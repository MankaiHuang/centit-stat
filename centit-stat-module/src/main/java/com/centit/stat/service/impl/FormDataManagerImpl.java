package com.centit.stat.service.impl;

import com.centit.framework.ip.service.IntegrationEnvironment;
import com.centit.stat.dao.DBCPDao;
import com.centit.stat.dao.QueryModelDao;
import com.centit.stat.po.QueryCell;
import com.centit.stat.po.QueryColumn;
import com.centit.stat.po.QueryCondition;
import com.centit.stat.po.QueryModel;
import com.centit.stat.po.html.table.CTableBodyTBody;
import com.centit.stat.po.html.table.CTableBodyTHead;
import com.centit.stat.po.html.table.CTableCell;
import com.centit.stat.po.html.table.CTablePanel;
import com.centit.stat.service.FormDataManager;
import com.centit.stat.service.FormDataModel;
import com.centit.support.algorithm.GeneralAlgorithm;
import com.centit.support.algorithm.CollectionsOpt;
import com.centit.support.algorithm.CollectionsOpt.ParentChild;
import com.centit.support.algorithm.StringBaseOpt;
import com.centit.support.database.utils.PageDesc;
import com.centit.support.database.utils.QueryAndNamedParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FormDataManagerImpl implements FormDataManager {
    protected QueryModelDao baseDao = null;

    @Resource
    public void setBaseDao(QueryModelDao baseDao) {
        this.baseDao = baseDao;
    }

    @Resource
    protected IntegrationEnvironment integrationEnvironment;
    /*
     * (non-Javadoc)
     *
     * @see com.centit.stat.twodimenform.FormDataManager#getDataModel(java.lang.
     * String)
     */
    @Transactional(readOnly = true)
    public FormDataModel getDataModel(String modelName) {
        FormDataModel formModel = new FormDataModel();
        formModel.setModelName(modelName);
        QueryModel qm = baseDao.getObjectById(modelName);
        if (qm != null) {
            formModel.loadFromQueryModel(qm);
            formModel.setDbinfo(
                    integrationEnvironment.getDatabaseInfo(qm.getDatabaseCode()));
        }
        return formModel;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.centit.stat.twodimenform.FormDataManager#queryFormData(com.centit.
     * stat.twodimenform.FormDataModel, boolean)
     */

    @Override
    @Transactional(readOnly = true)
    strictfp public Integer queryFormData(FormDataModel formData, PageDesc page) {
        List<Object[]> currDatas = new ArrayList<Object[]>();
        QueryAndNamedParams qap = formData.makeStatQuery();

        if (null != page)
            currDatas = DBCPDao.findObjectsNamedSql(formData.getDbinfo(), qap, page);
        else {
            currDatas = DBCPDao.findObjectsNamedSql(formData.getDbinfo(), qap);
        }
        formData.setFormData(currDatas);
        List<Object[]> datas = currDatas;
        List<QueryColumn> columns = formData.getColumns();

        // 树形结构
        if ("1".equals(formData.getIsTree())) {
            ParentChild<Object[]> c = new CollectionsOpt.ParentChild<Object[]>() {
                @Override
                public boolean parentAndChild(Object[] p, Object[] c) {
                    return p[0].equals(c[1]);
                }

            };

            CollectionsOpt.sortAsTree(datas, c);
        }
        // 计算合计和平均

        Object[] sumData = new Object[formData.getDataColumnCount()];
        Object[] avgData = new Object[formData.getDataColumnCount()];
        // sumData[0]=isPaging?"本页合计":"合计";
        sumData[0] = (page != null && page.getPageSize()>0) ? "本页合计" : "合计";
        avgData[0] = (page != null && page.getPageSize()>0) ? "本页平均" : "平均";
        boolean needSum = false;
        boolean needAvg = false;
        for (int i = 1; i < formData.getDataColumnCount(); i++) {
            sumData[i] = null;
            avgData[i] = null;
        }
        for (int i = 1; i < formData.getDataColumnCount(); i++) {
            int size = 0;
            String optType = columns.get(i).getOptType();
            if ("1".equals(optType) || "3".equals(optType)) {
                needSum = true;
            }
            if ("2".equals(optType) || "3".equals(optType)) {
                needAvg = true;
            }

            for (int di = 0; di < datas.size(); di++) {
                if (datas.get(di)[i] != null && datas.get(di)[i] instanceof java.lang.Number) {
                    sumData[i] = (sumData[i] == null) ? datas.get(di)[i]
                            : ((Number) sumData[i]).doubleValue() + ((Number) datas.get(di)[i]).doubleValue();
                    size++;
                }
            }
            if (size > 0)
                avgData[i] = ((Number) sumData[i]).doubleValue() / size;

        }
        for (int i = 1; i < formData.getDataColumnCount(); i++) {
            String optType = columns.get(i).getOptType();
            if (sumData[i] == null || "2".equals(optType) || "0".equals(optType)) {
                sumData[i] = "--";
            }
            if (avgData[i] == null || "1".equals(optType) || "0".equals(optType)) {
                avgData[i] = "--";
            }
        }
        if (needSum) {
            datas.add(sumData);
        }
        if (needAvg) {
            datas.add(avgData);
        }

        CTableBodyTHead thead = new CTableBodyTHead();
        CTableBodyTBody tbody = new CTableBodyTBody();

        // 创建表头
        for (QueryColumn col : columns) {
            thead.addCell(CTableCell.createTableHeadCell(col.getColName(), col));
        }

        // 链接参数
        List<Map<String, Object>> params = parseDataMap(datas, columns, formData.getConditions());

        int[][] needRowSpan = needRowSpan(columns, datas);

        // 创建表格内容
        int rowIndex = 0;
        int rowLength = datas.size();
        for (Object[] row : datas) {
            Map<String, Object> param = params.get(rowIndex);
            int colIndex = 0;
            for (QueryColumn col : columns) {
                CTableCell cell = CTableCell.createTableCell(row[colIndex++], col, param);
                // R是行头类型，合并单元格
                if ("R".equals(col.getShowType())) {
                    int xindex = columns.indexOf(col);
                    int yindex = rowIndex;
                    int rowspan = needRowSpan[xindex][yindex];
                    if (rowspan > 0) {
                        cell.setRowspan(rowspan);
                    } else {
                        cell.setRowspan(-1);
                    }
                }
                tbody.addCell(cell);

            }
            rowIndex++;
            if (rowIndex < rowLength)
                tbody.addLine();
            else {
                break;
            }
        }
        formData.setTablePanel(new CTablePanel(thead, tbody));
        return rowLength;
    }

    private static int[][] needRowSpan(List<QueryColumn> columns, List<Object[]> datas) {
        // 需要合并单元格的坐标，这里只考虑表数据纵向合并
        int[][] needRowSpan = new int[columns.size()][datas.size()];
        if (datas.size() > 0) {
            for (int j = 0; j < columns.size(); j++) {

                int beginindex = 0;
                int rowspan = 1;

                for (int i = 0; i < datas.size(); i++) {
                    if (i > 0) {
                        if (null != (datas.get(i)[j]) && !datas.get(i)[j].equals(datas.get(i - 1)[j])
                                || null == (datas.get(i)[j]) && null != (datas.get(i - 1)[j])) {
                            needRowSpan[j][beginindex] = rowspan;
                            beginindex = i;
                            rowspan = 1;
                            continue;
                        } else {
                            if ("R".equals(columns.get(j).getShowType())) {
                                needRowSpan[j][beginindex] = 1;
                            }
                            rowspan++;
                        }
                    } else {
                        needRowSpan[j][beginindex] = rowspan;
                    }
                }
                needRowSpan[j][beginindex] = rowspan;

            }
        }
        return needRowSpan;
    }

    private static int compareObjects(Object[] obj1, Object[] obj2, int rowGroup, List<QueryColumn> cols) {
        for (int i = 0; i < rowGroup; i++) {
            String ct = cols.get(i).getColType();
            if (obj1[i] != null || obj2[i] != null) {
                if (obj1[i] == null)
                    return -1;
                if (obj2[i] == null)
                    return 1;
                // 数值
                if ("N".equals(ct)) {
                    int nc = Double.valueOf(obj1[i].toString()).compareTo(Double.valueOf(obj2[i].toString()));
                    if (nc < 0)
                        return -1;
                    if (nc > 0)
                        return 1;
                } else { // if("D".equals(ct)){ //日期
                    int nc = obj1[i].toString().compareTo(obj2[i].toString());
                    if (nc < 0)
                        return -1;
                    if (nc > 0)
                        return 1;
                }
            }
        }

        return 0;
    }

    private static void mergeObjects(Object[] destObj, Object[] obj1, Object[] obj2, int rowGroup, int colSum) {
        if (obj1 == null) {
            if (obj2 == null)
                return;
            for (int i = 0; i < rowGroup; i++)
                destObj[i] = obj2[i];
            for (int i = rowGroup; i < colSum; i++)
                destObj[i] = null;

            for (int i = colSum; i < colSum * 2 - rowGroup; i++)
                destObj[i] = obj2[i - colSum + rowGroup];

            return;
        }

        for (int i = 0; i < colSum; i++)
            destObj[i] = obj1[i];

        if (obj2 != null) {
            for (int i = colSum; i < colSum * 2 - rowGroup; i++)
                destObj[i] = obj2[i - colSum + rowGroup];
        } else
            for (int i = colSum; i < colSum * 2 - rowGroup; i++)
                destObj[i] = null;
    }

    /**
     * 组装对比数据, 归并排序，sql语句中应该已经是排序好的，所以只要归并就可以了。
     */
    @Override
    public Integer queryCompareData(FormDataModel formData) {
        return queryCompareData(formData, true);
    }

    @Override
    @Transactional
    strictfp public Integer queryCompareData(FormDataModel formData, boolean needSum) {
        // 查询数据
        String currTitle = formData.makeCondCompareValue(formData.getModelType(), 0);
        QueryAndNamedParams qap = formData.makeStatQuery();
        List<Object[]> currDatas = DBCPDao.findObjectsNamedSql(formData.getDbinfo(), qap);

        String prevTitle = formData.makeCondCompareValue(formData.getModelType(), -1);
        qap = formData.makeStatQuery();
        List<Object[]> prevDatas = DBCPDao.findObjectsNamedSql(formData.getDbinfo(), qap);
        List<Object[]> compareDatas = new ArrayList<Object[]>();

        // 列信息
        List<QueryColumn> cols = formData.getColumns();

        CTableBodyTHead thead = parseCompareThead(cols, currTitle, prevTitle);

        int colSum = formData.getColumns().size();
        int rowGroup = formData.getRowGroupSum();

        /**
         * 合并两个数据集，对比数据放在前面，当前数据放在后面
         */
        int currDc = currDatas.size();
        int prevDc = prevDatas.size();
        int i = 0;
        int j = 0;
        while (j < currDc && i < prevDc) {
            Object[] rowData = new Object[colSum * 2 - rowGroup];
            int bc = compareObjects(prevDatas.get(i), currDatas.get(j), rowGroup, cols);
            if (bc < 0) {
                mergeObjects(rowData, prevDatas.get(i), null, rowGroup, colSum);
                i++;
            } else if (bc > 0) {
                mergeObjects(rowData, null, currDatas.get(j), rowGroup, colSum);
                j++;
            } else {
                mergeObjects(rowData, prevDatas.get(i), currDatas.get(j), rowGroup, colSum);
                i++;
                j++;
            }
            compareDatas.add(rowData);
        }

        while (j < currDc) {
            Object[] rowData = new Object[colSum * 2 - rowGroup];
            mergeObjects(rowData, null, currDatas.get(j), rowGroup, colSum);
            j++;
            compareDatas.add(rowData);
        }

        while (i < prevDc) {
            Object[] rowData = new Object[colSum * 2 - rowGroup];
            mergeObjects(rowData, prevDatas.get(i), null, rowGroup, colSum);
            i++;
            compareDatas.add(rowData);
        }

        // 树形结构
        if ("1".equals(formData.getIsTree())) {
            ParentChild<Object[]> c = new CollectionsOpt.ParentChild<Object[]>() {

                @Override
                public boolean parentAndChild(Object[] p, Object[] c) {
                    return p[0].equals(c[1]);
                }

            };

            CollectionsOpt.sortAsTree(compareDatas, c);
        }
        // 计算合计
        if (needSum) {
            int dataColCount = colSum * 2 - rowGroup;
            Object[] sumData = new Object[dataColCount];
            sumData[0] = "合计";

            for (i = rowGroup; i < dataColCount; i++)
                sumData[i] = null;
            for (int di = 0; di < compareDatas.size(); di++) {
                for (i = rowGroup; i < dataColCount; i++) {
                    if (compareDatas.get(di)[i] != null && compareDatas.get(di)[i] instanceof java.lang.Number)
                        sumData[i] = (sumData[i] == null) ? compareDatas.get(di)[i]
                                : ((Number) sumData[i]).doubleValue()
                                        + ((Number) compareDatas.get(di)[i]).doubleValue();
                }
            }
            for (i = 1; i < dataColCount; i++)
                if (sumData[i] == null)
                    sumData[i] = "--";
            compareDatas.add(sumData);
        }
        // 数据转换成hash表，供后面进行链接参数替换
        List<Map<String, Object>> dataMap = parseDataMap(compareDatas, cols, formData.getConditions());
        CTableBodyTBody tbody = parseCompareTbody(compareDatas, cols, dataMap);

        formData.setFormData(compareDatas);
        formData.setTablePanel(new CTablePanel(thead, tbody));
        return compareDatas.size();
    }

    /**
     * 解析表格内容
     *
     * @param compareDatas
     * @param cols
     * @param dataMap
     * @return
     */
    private static CTableBodyTBody parseCompareTbody(List<Object[]> compareDatas, List<QueryColumn> cols,
            List<Map<String, Object>> dataMap) {
        CTableBodyTBody tbody = new CTableBodyTBody();

        // 表格头固定列
        List<QueryColumn> columnHead = parseQueryColumn(cols, "R");

        // 对比数据列
        List<QueryColumn> columnData = parseQueryColumn(cols, "D");

        int indexRow = 0;
        // 总行数
        int lengthRow = compareDatas.size();

        // 数据列数
        int lengthData = columnData.size();

        for (Object[] datas : compareDatas) {

            int indexColHead = 0;

            // 固定列数据
            for (QueryColumn col : columnHead) {
                CTableCell cell = CTableCell.createTableCell(datas[indexColHead++], col, dataMap.get(indexRow));

                tbody.addCell(cell);
            }

            int indexColData = indexColHead;

            // 数据行在上，时间行在下分组
            if ("R".equals("R")) {
                for (QueryColumn col : columnData) {
                    CTableCell cellPrev = CTableCell.createTableCell(datas[indexColData], col, dataMap.get(indexRow));
                    CTableCell cellCurr = CTableCell.createTableCell(datas[indexColData + lengthData], col,
                            dataMap.get(indexRow));

                    tbody.addCell(cellPrev);
                    tbody.addCell(cellCurr);

                    indexColData++;
                }
            }

            // 时间行在上，数据行在下分组
            else {
                // 去年对比数据
                for (QueryColumn col : columnData) {
                    CTableCell cell = CTableCell.createTableCell(datas[indexColData++], col, dataMap.get(indexRow));
                    tbody.addCell(cell);
                }

                // 今年对比数据
                for (QueryColumn col : columnData) {
                    CTableCell cell = CTableCell.createTableCell(datas[indexColData++], col, dataMap.get(indexRow));
                    tbody.addCell(cell);
                }
            }

            // 还有数据，添加一行
            if (++indexRow < lengthRow) {
                tbody.addLine();
            }
        }

        return tbody;
    }

    /**
     * 解析表头数据
     *
     * @param dataColumns
     * @return
     */
    private static List<List<QueryCell>> parseColumns(List<Object[]> dataColumns) {

        List<List<QueryCell>> columns = new ArrayList<List<QueryCell>>();

        List<List<String>> newDataColumns = swapColumnRow(dataColumns);

        if (0 == newDataColumns.size()) {
            return columns;
        }

        // 现在只考虑最多2层的表头
        if (newDataColumns.size() == 2) {
            List<String> datas = newDataColumns.get(0);
            List<QueryCell> cells = new ArrayList<QueryCell>();
            List<String> titles = new ArrayList<String>();

            int length = 0;

            for (int index = 0; index < datas.size(); index++) {
                String title = datas.get(index);

                // 第一个单元格直接加入
                if (titles.isEmpty()) {
                    titles.add(title);
                    length++;
                }
                // 最后一个单元格特殊处理
                else if (index == datas.size() - 1) {
                    QueryCell cell = new QueryCell();
                    cell.setName(title);

                    if (titles.contains(title)) {
                        cell.setLength(++length);
                    } else {
                        cell.setLength(1);
                    }

                    cells.add(cell);
                }
                // 重复的单元格只增加长度（如果是）
                else if (titles.contains(title)) {
                    length++;
                }
                // 出现新title，说明原单元格已经遍历完毕，加入
                else {
                    QueryCell cell = new QueryCell();
                    cell.setName(titles.get(titles.size() - 1));
                    cell.setLength(length);
                    cells.add(cell);

                    titles.add(title);

                    // 长度重新清零
                    length = 1;
                }
            }

            columns.add(cells);
        }

        List<String> datas = newDataColumns.get(newDataColumns.size() - 1);

        List<QueryCell> cells = new ArrayList<QueryCell>();
        for (String title : datas) {

            QueryCell cell = new QueryCell();
            cell.setName(title);
            cell.setLength(1);
            cells.add(cell);
        }

        columns.add(cells);

        return columns;
    }

    /**
     * 数组行列互换
     *
     * @param array
     * @return
     */
    private static List<List<String>> swapColumnRow(List<Object[]> array) {
        List<List<String>> newArray = new ArrayList<List<String>>();

        int rowLength = array.size();

        // 当查询无结果时直接返回
        if (0 == rowLength) {
            return newArray;
        }

        int colLength = array.get(0).length;

        for (int i = 0; i < colLength; i++) {

            List<String> data = new ArrayList<String>();

            for (int j = 0; j < rowLength; j++) {

                data.add((String) array.get(j)[i]);

            }

            newArray.add(data);
        }

        return newArray;
    }

    /**
     * 解析表头
     *
     * @param columns
     * @param dataColumns
     * @return
     */
    private static CTableBodyTHead parseCrossThead(List<QueryColumn> columns, List<Object[]> dataColumns) {
        CTableBodyTHead thead = new CTableBodyTHead();

        List<List<QueryCell>> headColumns = parseColumns(dataColumns);

        // 表格头固定列
        List<QueryColumn> columnHead = parseQueryColumn(columns, "R");

        // 对比数据列
        List<QueryColumn> columnData = parseQueryColumn(columns, "D");

        // 数据列数
        int dataLength = columnData.size();

        int rowspan = (columnData.size() > 1) ? (headColumns.size() + 1) : headColumns.size();

        // 设定固定列
        int headBlank=0;
        for (QueryColumn col : columnHead) {
            CTableCell cell = CTableCell.createTableHeadCell(col.getColName(), col);

            cell.setRowspan(rowspan);

            thead.addCell(cell);
            headBlank ++;
        }

        int index = 1;
        // 设定其他列
        for (List<QueryCell> line : headColumns) {

            for (QueryCell td : line) {
                String title = td.getName();
                int colspan = td.getLength();

                CTableCell cell = CTableCell.createTableHeadCell(title);
                cell.setColspan(colspan * dataLength);
                thead.addCell(cell);
            }

            if (index++ < headColumns.size()) {
                thead.addLine(headBlank);
            }
        }

        // 添加最后一行标题
        if (dataLength > 1) {
            thead.addLine(headBlank);

            for (int i = 0; i < dataColumns.size(); i++) {
                for (QueryColumn col : columnData) {
                    CTableCell cell = CTableCell.createTableHeadCell(col.getColName());
                    thead.addCell(cell);
                }
            }
        }

        return thead;
    }

    /**
     * 解析表头
     *
     * @param cols
     * @param currTitle
     * @param prevTitle
     * @return
     */
    private static CTableBodyTHead parseCompareThead(List<QueryColumn> cols, String currTitle, String prevTitle) {
        CTableBodyTHead thead = new CTableBodyTHead();

        // 表格头固定列
        List<QueryColumn> columnHead = parseQueryColumn(cols, "R");

        // 对比数据列
        List<QueryColumn> columnData = parseQueryColumn(cols, "D");

        // 是否有多个对比数据
        boolean multiData = false;
        if (columnData.isEmpty() || 1 < columnData.size()) {
            multiData = true;
        }

        // 固定列头
        for (QueryColumn col : columnHead) {
            CTableCell cell = CTableCell.createTableHeadCell(col.getColName(), col);

            // 多个对比数据，固定列占2行
            if (multiData) {
                cell.setRowspan(2);
            }

            thead.addCell(cell);
        }

        // 数据在上、日期在下分组
        if ("R".equals("R")) {
            if (multiData) {
                for (QueryColumn col : columnData) {
                    String title = col.getColName();

                    CTableCell cell = CTableCell.createTableHeadCell(title);
                    cell.setColspan(2);
                    thead.addCell(cell);

                }

                thead.addLine();
            }

            for (QueryColumn col : columnData) {
                String title = col.getColName();
                String prevTitleChild = prevTitle;
                String currTitleChild = currTitle;

                // 单个对比数据修改标题,
                if (!multiData) {
                    prevTitleChild += title;
                    currTitleChild += title;
                }

                CTableCell prevCell = CTableCell.createTableHeadCell(prevTitleChild);
                CTableCell currCell = CTableCell.createTableHeadCell(currTitleChild);
                thead.addCell(prevCell);
                thead.addCell(currCell);

            }
        }

        // 日期在上、数据在下分组
        else {

            // 单个对比数据修改标题,
            if (!multiData) {
                String title = columnData.get(0).getColName();
                prevTitle += title;
                currTitle += title;
            }

            CTableCell prevCell = CTableCell.createTableHeadCell(prevTitle);
            CTableCell currCell = CTableCell.createTableHeadCell(currTitle);

            // 多对比数据, 则第一行单元格占多列宽度
            if (multiData) {
                prevCell.setColspan(columnData.size());
                currCell.setColspan(columnData.size());
            }

            thead.addCell(prevCell);
            thead.addCell(currCell);

            // 添加第二行数据
            if (multiData) {
                thead.addLine();
                for (int i = 0; i < 2; i++) {
                    for (QueryColumn col : columnData) {
                        CTableCell cell = CTableCell.createTableHeadCell(col.getColName());
                        thead.addCell(cell);
                    }
                }
            }
        }

        return thead;
    }

    /**
     * 将数据转换成hash表，供后面链接参数替换
     *
     * @param datas
     * @param cols
     * @return
     */
    private static List<Map<String, Object>> parseDataMap(List<Object[]> datas, List<QueryColumn> cols,
            List<QueryCondition> conditions) {
        List<Map<String, Object>> dataMap = new ArrayList<Map<String, Object>>();

        if (null == datas) {
            return dataMap;
        }

        for (Object[] line : datas) {
            Map<String, Object> lineMap = new HashMap<String, Object>();
            int index = 0;

            // 只处理行头的链接
            for (QueryColumn col : cols) {
                if ("R".equals(col.getShowType())) {
                    lineMap.put(":" + col.getColName(), line[index++]);
                }
            }

            for (QueryCondition condition : conditions) {
                String name = condition.getCondName();
                String value = StringBaseOpt.objectToString(condition.getCondValue());

                if (StringUtils.isBlank(value))
                    continue;

                lineMap.put(":" + name, value);
            }

            dataMap.add(lineMap);
        }

        return dataMap;
    }

    /**
     * 按照显示类型过滤列头
     *
     * @param cols
     * @param showType
     * @return
     */
    private static List<QueryColumn> parseQueryColumn(List<QueryColumn> cols, String showType) {
        if (null == showType)
            return cols;

        List<QueryColumn> list = new ArrayList<QueryColumn>();

        for (QueryColumn col : cols) {
            if (showType.equals(col.getShowType())) {
                list.add(col);
            }
        }

        return list;
    }

    private static boolean equalsObjects(Object[] obj1, Object[] obj2, int nSize) {
        for (int i = 0; i < nSize; i++) {
            if (obj1[i] != null || obj2[i] != null) {
                if (obj1[i] == null)
                    return false;
                if (obj2[i] == null)
                    return false;
                /*
                 * if(obj1[i] == obj2[i]) return true;
                 */
                if (obj1[i].toString().compareTo(obj2[i].toString()) != 0)
                    return false;
            }
        }
        return true;
    }

    private static int foundCrossColumn(List<Object[]> dataColumns, Object[] col, int colCount, int colGroup) {
        for (int i = 0; i < colCount; i++) {
            if (equalsObjects(dataColumns.get(i), col, colGroup)) {
                return i;
            }
        }
        return -1;
    }

    private static void initCrossRowData(Object[] rowData, Object[] curData, int rowGroup, int dataColCount) {
        for (int i = 0; i < rowGroup; i++) {
            rowData[i] = curData[i];
        }
        for (int i = rowGroup; i < dataColCount; i++) {
            rowData[i] = null;
        }
    }

    @Override
    public Integer queryCrossData(FormDataModel formData) {
        return queryCrossData(formData, false);
    }

    @Override
    @Transactional
    strictfp public Integer queryCrossData(FormDataModel formData, boolean needSum) {

        int dataCols = 0;
        int sumDataCols = 0;
        int rowGroup = formData.getRowGroupSum();
        int colGroup = formData.getColGroupSum();
        int dataAnalyseSum = formData.getDataAnalyseSum();
        List<Object[]> dataColumns = new ArrayList<Object[]>();
        List<Object[]> sumDataColumns = new ArrayList<Object[]>();

        String columnSql = formData.getColumnSql();
        if (columnSql != null && !"".equals(columnSql)) {
            List<Object[]> colList = DBCPDao.findObjectsNamedSql(formData.getDbinfo(), formData.makeColumnQuery());
            if (colList != null && colList.size() > 0) {
                for (int i = 0; i < colList.size(); i++) {
                    Object[] col;
                    if (colGroup == 1) {
                        col = new Object[1];
                        col[0] = colList.get(i);
                    } else
                        col = colList.get(i);

                    int nf = foundCrossColumn(dataColumns, col, dataCols, colGroup);
                    if (nf < 0) {
                        dataColumns.add(col);
                        dataCols++;
                    }
                    Object[] col2 = new Object[colGroup];
                    col2[0] = "合计";
                    for (int j = 1; j < colGroup; j++)
                        col2[j] = col[j];
                    nf = foundCrossColumn(sumDataColumns, col2, sumDataCols, colGroup);
                    if (nf < 0) {
                        sumDataColumns.add(col2);
                        sumDataCols++;
                    }
                }
                dataColumns.addAll(sumDataColumns);
                dataCols = dataColumns.size();
            }
        }

        List<Object[]> currDatas = DBCPDao.findObjectsNamedSql(formData.getDbinfo(), formData.makeStatQuery());

        // List<Object[]> currDatas =this.findDataBySql(formData.getQuerySql());
        List<Object[]> crossDatas = new ArrayList<Object[]>();
        int currDc = currDatas.size();

        if (dataCols == 0) {
            for (int i = 0; i < currDc; i++) {
                Object[] col = new Object[colGroup];
                Object[] curData = currDatas.get(i);
                for (int j = 0; j < colGroup; j++)
                    col[j] = curData[j + rowGroup];
                int nf = foundCrossColumn(dataColumns, col, dataCols, colGroup);
                if (nf < 0) {
                    dataColumns.add(col);
                    dataCols++;
                }
                Object[] col2 = new Object[colGroup];
                col2[0] = "合计";
                for (int j = 1; j < colGroup; j++)
                    col2[j] = col[j];
                nf = foundCrossColumn(sumDataColumns, col2, sumDataCols, colGroup);
                if (nf < 0) {
                    sumDataColumns.add(col2);
                    sumDataCols++;
                }
            }
            dataColumns.addAll(sumDataColumns);
            dataCols = dataColumns.size();
        }

        formData.setCrossTableColumns(dataColumns);
        int dataColCount = rowGroup + dataCols * dataAnalyseSum;

        Object[] rowData = new Object[dataColCount];
        if (currDc > 0) {
            Object[] curData = currDatas.get(0);
            initCrossRowData(rowData, curData, rowGroup, dataColCount);
        }

        for (int i = 0; i < currDc; i++) {

            Object[] curData = currDatas.get(i);
            Object[] col = new Object[colGroup];
            for (int j = 0; j < colGroup; j++)
                col[j] = curData[j + rowGroup];

            int nf = foundCrossColumn(dataColumns, col, dataCols, colGroup);

            if (!equalsObjects(rowData, curData, rowGroup)) {
                crossDatas.add(rowData);
                rowData = new Object[dataColCount];
                initCrossRowData(rowData, curData, rowGroup, dataColCount);
            }
            // 这个肯定应该为true
            if (nf >= 0) {
                for (int j = 0; j < dataAnalyseSum; j++) {
                    rowData[rowGroup + nf * dataAnalyseSum + j] = curData[rowGroup + colGroup + j];
                }
            }
            col[0] = "合计";
            nf = foundCrossColumn(dataColumns, col, dataCols, colGroup);
            if (nf > 0) {
                for (int j = 0; j < dataAnalyseSum; j++) {
                    rowData[rowGroup + nf * dataAnalyseSum + j] = GeneralAlgorithm.addTwoObject(
                            rowData[rowGroup + nf * dataAnalyseSum + j], curData[rowGroup + colGroup + j]);
                }
            }

        }

        List<QueryColumn> columns = formData.getColumns();

        if (currDc > 0) {
            crossDatas.add(rowData);
        }
        // 树形结构
        if ("1".equals(formData.getIsTree())) {
            CollectionsOpt.sortAsTree(crossDatas, (p, c1) -> p[0].equals(c1[1]));
        }
        // 计算合计
        if (needSum) {
            Object[] sumData = new Object[dataColCount];
            sumData[0] = "合计";

            for (int i = rowGroup; i < dataColCount; i++)
                sumData[i] = null;
            for (int di = 0; di < crossDatas.size(); di++) {
                for (int i = rowGroup; i < dataColCount; i++) {
                    if (crossDatas.get(di)[i] != null && crossDatas.get(di)[i] instanceof java.lang.Number) {
                        sumData[i] = (sumData[i] == null) ? crossDatas.get(di)[i]
                            : GeneralAlgorithm.addTwoObject(sumData[i], crossDatas.get(di)[i]);
                    }
                  }
            }
            for (int i = 1; i < dataColCount; i++)
                if (sumData[i] == null)
                    sumData[i] = "--";

            crossDatas.add(sumData);
        }
        CTableBodyTHead thead = parseCrossThead(columns, dataColumns);
        List<Map<String, Object>> dataMap = parseDataMap(crossDatas, columns, formData.getConditions());
        CTableBodyTBody tbody = parseCrossTbodyConbined(crossDatas, columns, dataMap, dataColumns);

        formData.setFormData(crossDatas);
        formData.setTablePanel(new CTablePanel(thead, tbody));

        return crossDatas.size();
    }

    /**
     * 解析表格内容数据,合并单元格
     *
     * @param crossDatas
     *            交叉表数据内容
     * @param columns
     *            列
     * @param dataMap
     *            参数信息
     * @param dataColumns
     *            列参数
     * @return
     */
    private static CTableBodyTBody parseCrossTbodyConbined(List<Object[]> crossDatas, List<QueryColumn> columns,
            List<Map<String, Object>> dataMap, List<Object[]> dataColumns) {
        CTableBodyTBody tbody = new CTableBodyTBody();
        // 表格头固定列
        List<QueryColumn> columnHead = parseQueryColumn(columns, "R");

        // 对比数据列
        List<QueryColumn> columnCross = parseQueryColumn(columns, "C");

        // 对比数据列
        List<QueryColumn> columnData = parseQueryColumn(columns, "D");

        // 需要合并单元格的坐标>=1:合并，0:忽略
        int[][] needRowSpan = needRowSpan(columnHead, crossDatas);
        for (int rowIndex = 0; rowIndex < crossDatas.size(); rowIndex++) {

            // 每一行数据
            Object[] datas = crossDatas.get(rowIndex);

            // 每一行参数
            Map<String, Object> params = dataMap.get(rowIndex);

            int colIndex = 0;
            // 固定列
            for (QueryColumn col : columnHead) {
                CTableCell cell = CTableCell.createTableCell(datas[colIndex++], col, params);
                int xindex = columnHead.indexOf(col);
                int yindex = rowIndex;
                int rowspan = needRowSpan[xindex][yindex];
                if (rowspan > 0) {
                    cell.setRowspan(rowspan);
                } else {
                    cell.setRowspan(-1);
                }
                tbody.addCell(cell);
            }

            // 交叉列
            for (Object[] obj : dataColumns) {
                // 设置列参数
                for (int paramIndex = 0; paramIndex < obj.length; paramIndex++) {
                    params.put(":" + columnCross.get(paramIndex).getColName(), obj[paramIndex].toString());
                }

                for (QueryColumn col : columnData) {
                    CTableCell cell = CTableCell.createTableCell(datas[colIndex++], col, params);
                    tbody.addCell(cell);
                }
            }

            if (rowIndex < crossDatas.size() - 1) {
                tbody.addLine();
            }
        }

        return tbody;
    }

}
