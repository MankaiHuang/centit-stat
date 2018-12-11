package com.centit.stat.query.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.centit.framework.common.JsonResultUtils;
import com.centit.framework.core.controller.BaseController;
import com.centit.support.database.utils.PageDesc;
import com.centit.stat.query.dao.DBCPDao;
import com.centit.stat.query.po.QueryCondition;
import com.centit.stat.query.po.html.table.CTableCell;
import com.centit.stat.query.po.html.table.CTableLine;
import com.centit.stat.query.service.FormDataManager;
import com.centit.stat.query.service.FormDataModel;
import com.centit.support.algorithm.DatetimeOpt;
import com.centit.support.algorithm.StringBaseOpt;
import com.centit.support.algorithm.StringRegularOpt;
import com.centit.support.json.JsonPropertyUtils;
import com.centit.support.network.HtmlFormUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(value = "生成统计报表", tags = "报表元数据")
@Controller
@RequestMapping("/stat/twodimenform")
public class FormDataController extends BaseController {

    @Resource
    protected FormDataManager dataManager;

    @ApiOperation(value = "统计报表模块元数据")
    @ApiImplicitParam(name = "modelName", value = "模块名称")
    @RequestMapping(value = "/meta/{modelName}", method = RequestMethod.GET)
    public void getMetaDate(@PathVariable String modelName,
                            HttpServletRequest request, HttpServletResponse response) {
        FormDataModel formObj = new FormDataModel();
        formObj.copyModelMetaData(dataManager.getDataModel(modelName));
        try {
            collectParams(request, formObj, true);
            JsonResultUtils.writeSingleDataJson(formObj, response,
                JsonPropertyUtils.getIncludePropPreFilter(
                    FormDataModel.class, "modelName",
                    "paramCount", "conditions",
                    "formNameFormat", "formName"));
        } catch (Exception e) {
            JsonResultUtils.writeErrorMessageJson(e.getMessage(), response);
            e.printStackTrace();
        }
    }

    /**
     * 统计入口，返回包装好的FormDataModel对象
     *
     * @param modelName 传入统计模块代码
     * @param page      分页信息，交叉表不支持分页，会自动忽略
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     */
    @ApiOperation(value = "统计数据")
    @RequestMapping(value = "/{modelName}", method = RequestMethod.GET)
    public void doStat(@PathVariable String modelName, PageDesc page,
                       HttpServletRequest request, HttpServletResponse response) {
        FormDataModel formObj = new FormDataModel();
        FormDataModel fm = dataManager.getDataModel(modelName);
        formObj.copyModelMetaData(fm);
        queryDatabase(page, formObj, request);
        JsonResultUtils.writeSingleDataJson(formObj, response);
    }


    /**
     * formdatamodel对象导入excel并推送至页面下载
     *
     * @param request   HttpServletRequest
     * @param modelName 传入统计模块代码
     * @param paging    是否分页
     * @param page      分页信息
     * @param response  HttpServletResponse
     */
    @ApiOperation(value = "excel下载")
    @RequestMapping(value = "/excels", method = RequestMethod.POST)
    public void exportToExcel(HttpServletRequest request, String modelName, boolean paging,
                              @Valid PageDesc page, HttpServletResponse response) {
        FormDataModel fdm = new FormDataModel();
        fdm.copyModelMetaData(dataManager.getDataModel(modelName));

        queryDatabase(paging ? page : null, fdm, request);

        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddhhmm");
        Date date = new Date();
        String time = sdf.format(date);
        String fileName = StringUtils.isBlank(fdm.getFormNameFormat()) ? "未命名"
            : fdm.getFormNameFormat() + time;
        HSSFWorkbook excel = exportToExcel(fdm);
        try {
            response.setHeader("Content-Disposition", "attachment;filename="
                + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                excel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 把formDataModel写入一个excel中
    private HSSFWorkbook exportToExcel(FormDataModel fdm) {
        List<CTableLine> headLines = fdm.getTablePanel().getThead().getLines();
        List<CTableLine> bodyLines = fdm.getTablePanel().getTbody().getLines();
        TwoDimen resHead = convertLinesToTwoDimen(headLines);
        TwoDimen resBody = convertLinesToTwoDimen(bodyLines);
        return HSSFWorkbookOpt.exportToWorkbook(resHead.getTwodimen(),
            resHead.getNeedCombine(), resBody.getTwodimen(),
            resBody.getNeedCombine());
    }

    /**
     * 下拉框对应表
     *
     * @param request            HttpServletRequest
     * @param formObj            FormDataModel对象
     * @param collectComboValues boolean
     * @throws Exception Exception
     */
    private void collectParams(HttpServletRequest request, FormDataModel formObj, boolean collectComboValues)
        throws Exception {
        Map<String, String[]> paramMap = request.getParameterMap();
        for (QueryCondition cond : formObj.getConditions()) {
            String sName = cond.getCondName();
            String style = cond.getParamReferenceType();
            // 通过SQL查询数字
            if (collectComboValues) {
                char rt = '0';
                if (StringUtils.isNotBlank(style))
                    rt = style.charAt(0);
                switch (rt) {
                    case '1': {// 数据字典设置下拉框
                        String sql = "select datacode,datavalue from f_datadictionary where catalogcode=?";
                        if (StringUtils.isNotBlank(sql)) {
                            List<Object[]> datas = DBCPDao.findObjectsBySql(
                                formObj.getDbinfo(), sql,
                                cond.getParamReferenceData());
                            for (Object[] data : datas) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("key", data[0]);
                                map.put("value", data[1]);
                                cond.getComboValues().add(map);
                            }
                        }
                    }
                    break;
                    case '2': {// json表达式方式设置下拉框
                        JSONObject json = JSON
                            .parseObject(cond.getParamReferenceData());
                        if (json != null) {
                            for (Map.Entry<String, Object> ent : json.entrySet()) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("key", ent.getKey());
                                map.put("value", ent.getValue());
                                cond.getComboValues().add(map);

                            }
                        }
                    }
                    break;
                    case '3': {// 存入数据库的下拉框
                        String sql = cond.getParamReferenceData();
                        if (StringUtils.isNotBlank(sql)) {
                            List<Object[]> datas = DBCPDao.findObjectsBySql(
                                formObj.getDbinfo(), sql);
                            for (Object[] data : datas) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("key", data[0]);
                                map.put("value", data[1]);
                                cond.getComboValues().add(map);
                            }
                        }
                    }
                    break;
                    case 'Y': {// 年份下拉框
                        int currYear = DatetimeOpt.getYear(new Date());
                        for (int i = 5; i > -45; i--) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("key", String.valueOf(currYear + i) + "年");
                            map.put("value", currYear + i);
                            cond.getComboValues().add(map);
                        }
                    }
                    break;
                    case 'M':// 月份下拉框
                        for (int i = 1; i < 13; i++) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("key", String.valueOf(+i) + "月");
                            map.put("value", i);
                            cond.getComboValues().add(map);
                        }
                        break;
                    default:
                        break;
                }
            }
            setDefaultValue(cond, paramMap);
            Object oValue = paramMap.get(sName);
            if (oValue != null) {
                String sValue = StringBaseOpt.objectToString(oValue);
                cond.setCondValue(sValue);
            }
        }
    }

    /**
     * 覆盖默认值
     *
     * @param cond     查询条件
     * @param paramMap 参数
     */
    private void setDefaultValue(QueryCondition cond,
                                 Map<String, String[]> paramMap) {
        // String referenceStyle = cond.getParamReferenceType();
        String conditionType = cond.getParamType();

        String referenceStyle = cond.getParamReferenceType();
        if ("M".equals(referenceStyle)) {
            Date date = new Date();
            cond.setCondValue(DatetimeOpt.getMonth(date));
        }

        if ("Y".equals(referenceStyle)) {
            Date date = new Date();
            cond.setCondValue(DatetimeOpt.getYear(date));
        }

        String value = cond.getParamDefaultValue();
        if (StringUtils.isBlank(value))
            return;
        if (value.startsWith("{") && value.endsWith("}")) {
            value = value.substring(1, value.length() - 1);
            String[] v = paramMap.get(value);
            if (v != null && v.length > 0)
                cond.setCondValue(v[0]);
        } else {// 有默认值按默认值计算
            // Formula formula = new Formula();
            if ("D".equals(conditionType) || "T".equals(conditionType)) {
                cond.setCondValue(DatetimeOpt.smartPraseDate(value));
            } else if ("N".equals(conditionType)) {
                if (StringRegularOpt.isNumber(value))
                    cond.setCondValue(Double.valueOf(value));
            } else {
                cond.setCondValue(value);
            }
        }
    }

    /**
     * 从数据库中取元数据
     *
     * @param page    分页信息
     * @param formObj FormDataModel对象
     * @param request HttpServletRequest
     */
    private void queryDatabase(PageDesc page, FormDataModel formObj,
                               HttpServletRequest request) {


        Map<String, String[]> paramMap = request.getParameterMap();
        Object oValue = paramMap.get("resultName");
        if (oValue != null) {
            String sValue = HtmlFormUtils.getParameterString(oValue);
            if (sValue != null && !"".equals(sValue))
                formObj.setResultName(sValue);
        }
        // 获取参数
        try {
            collectParams(request, formObj, true);
        } catch (Exception e) {
            // log.error("获取参数失败："+e.getMessage());
            e.printStackTrace();
        }
        Integer totalRows = 0;
        String modelType = formObj.getModelType();
        // 普通二维报表
        if ("2".equals(modelType) || StringUtils.isEmpty(modelType)) {
            totalRows = dataManager.queryFormData(formObj, page);
            formObj.setTotalRowsAll(page == null ? totalRows : page.getTotalRows());
        }

        // 同比报表 环比报表
        else if ("3".equals(modelType) || "4".equals(modelType)) {
            totalRows = dataManager.queryCompareData(formObj);
            formObj.setTotalRowsAll(totalRows);
        }

        // 交叉表
        else if ("5".equals(modelType)) {
            totalRows = dataManager.queryCrossData(formObj);
            formObj.setTotalRowsAll(totalRows);
        } else {
            totalRows = dataManager.queryFormData(formObj, page);
            formObj.setTotalRowsAll(page == null ? totalRows : page.getTotalRows());
        }
        formObj.setTotalRows(totalRows);
//        formObj.setTotalRowsAll(page.getTotalRows());
        formObj.makeParamByFormat();
    }

    //二维表单元格对象
    static class TwoDimen {
        //每个单元格值
        private Object[][] twodimen = null;
        //需要合并的区域列表
        private List<int[]> needCombine = new ArrayList<>();

        public TwoDimen(Object[][] twodimen2, List<int[]> combineList) {
            this.twodimen = twodimen2;
            this.needCombine = combineList;
        }

        public Object[][] getTwodimen() {
            return twodimen;
        }

        public void setTwodimen(Object[][] twodimen) {
            this.twodimen = twodimen;
        }

        public List<int[]> getNeedCombine() {
            return needCombine;
        }

        public void setNeedCombine(List<int[]> needCombine) {
            this.needCombine = needCombine;
        }
    }


    public TwoDimen convertLinesToTwoDimen(List<CTableLine> lines) {
        int xlen = 0;
        for (CTableCell cell : lines.get(0).getCells()) {
            if (cell.getColspan() > 0) {
                xlen += cell.getColspan();
            } else {
                xlen += 1;
            }
        }
        int ylen = lines.size();
        // xlen:横向长度
        // ylen:纵向长度
        Object[][] twodimen = new Object[xlen][ylen];
        List<int[]> combineList = new ArrayList<>();
        // 每行line循环
        for (int y = 0; y < lines.size(); y++) {
            CTableLine line = lines.get(y);
            int xIndex = line.getFirstCellCol();
            // 每个单元格循环
            for (CTableCell cell : line.getCells()) {
                Object value = cell.getValue();
                // 单元格是纵向合并类型
                if (cell.getRowspan() > 1) {
                    int[] comb = new int[]{y, (y + cell.getRowspan() - 1), xIndex,
                        xIndex};
                    combineList.add(comb);
                    for (int k = 0; k < cell.getRowspan(); k++) {
                        // 非数字型统统取displayvalue
                        if (isNumType(value)) {
                            twodimen[xIndex][y + k] = cell.getValue();
                        } else {
                            twodimen[xIndex][y + k] = cell.getDisplayValue();
                        }
                    }
                    xIndex++;
                }
                // 横向合并
                else if (cell.getColspan() > 1) {
                    int[] comb = new int[]{y, y, xIndex,
                        xIndex + cell.getColspan() - 1};
                    combineList.add(comb);
                    for (int k = 0; k < cell.getColspan(); k++) {
                        // 非数字型统统取displayvalue
                        if (isNumType(value)) {
                            twodimen[xIndex + k][y] = cell.getValue();
                        } else {
                            twodimen[xIndex + k][y] = cell.getDisplayValue();
                        }
                    }
                    xIndex += cell.getColspan();
                } else {
                    // 非数字型统统取displayvalue
                    if (isNumType(value)) {
                        twodimen[xIndex][y] = cell.getValue();
                    } else {
                        twodimen[xIndex][y] = cell.getDisplayValue();
                    }
                    xIndex++;
                }
            }
        }
        return new TwoDimen(twodimen, combineList);
    }

    public boolean isNumType(Object value) {
        if (value instanceof BigDecimal || value instanceof Integer
            || value instanceof Double || value instanceof Float)
            return true;
        else
            return false;
    }


    //excel操作类
    static class HSSFWorkbookOpt {
        /**
         * @param twodimenHead 二维数组
         * @param headCombine  int数组集合
         * @param twodimenBody 二维数组
         * @param bodyCombine  int数组集合
         * @return HSSFWorkbook
         */
        static HSSFWorkbook exportToWorkbook(Object[][] twodimenHead,
                                             List<int[]> headCombine, Object[][] twodimenBody,
                                             List<int[]> bodyCombine) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();// excel表
            sheet.setDefaultColumnWidth(20);
            HSSFCellStyle cellStyleHead = workbook.createCellStyle();// 表头单元格样式
            cellStyleHead.setAlignment(HorizontalAlignment.CENTER);
            cellStyleHead.setWrapText(true);// 单元格包围文本
            cellStyleHead.setFillPattern(FillPatternType.FINE_DOTS);
            cellStyleHead.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW
                .getIndex());// excel表头颜色，前景色和背景色要同事设置
            cellStyleHead.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.YELLOW
                .getIndex());
            cellStyleHead.setBorderBottom(BorderStyle.THIN);// excel表头边框
            cellStyleHead.setBorderTop(BorderStyle.THIN);
            cellStyleHead.setBorderLeft(BorderStyle.THIN);
            cellStyleHead.setBorderRight(BorderStyle.THIN);
            HSSFCellStyle cellStyleBody = workbook.createCellStyle();// 表数据单元格样式
            cellStyleBody.setAlignment(HorizontalAlignment.LEFT);
            cellStyleBody.setWrapText(true);
            cellStyleBody.setAlignment(HorizontalAlignment.CENTER);
            cellStyleBody.setVerticalAlignment(VerticalAlignment.CENTER);

            int endIndex = addTwodimen(sheet, twodimenHead, 0, cellStyleHead,
                headCombine);
            addTwodimen(sheet, twodimenBody, endIndex, cellStyleBody,
                bodyCombine);
            return workbook;
        }


        /**
         * @param sheet      表格
         * @param twodimen   二位数组
         * @param startIndex int
         * @param cellStyle  表格样式
         * @param combine    合计数据
         * @return int 结束行
         */
        static int addTwodimen(HSSFSheet sheet, Object[][] twodimen,
                               int startIndex, HSSFCellStyle cellStyle, List<int[]> combine) {
            Row row = null;
            Cell cell = null;
            Object value = null;
            int endIndex = 0;// 结束行
            for (int i = 0; i < twodimen[0].length; i++) {
                row = sheet.createRow(i + startIndex);

                for (int j = 0; j < twodimen.length; j++) {
                    cell = row.createCell(j);
                    cell.setCellStyle(cellStyle);
                    value = twodimen[j][i];
                    if (null == value) {
                        continue;
                    }
                    // 这里把数字型的都按Number保存，避免单元格出现左上角箭头
                    if (value instanceof Integer) {
                        cell.setCellType(CellType.NUMERIC);// 0:Number 1:String ...
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Double
                        || value instanceof Float) {
                        cell.setCellType(CellType.NUMERIC);// 0:Number 1:String ...
                        cell.setCellValue((Double) value);
                    } else if (value instanceof BigDecimal) {
                        cell.setCellType(CellType.NUMERIC);// 0:Number 1:String ...
                        cell.setCellValue(((BigDecimal) value).doubleValue());
                    } else {
                        cell.setCellValue((String) value);
                    }
                    // sheet.autoSizeColumn(j);
                }
                endIndex++;
            }
            if (null != combine && combine.size() > 0) {
                for (int i = 0; i < combine.size(); i++) {
                    int[] com = combine.get(i);
                    sheet.addMergedRegion(new CellRangeAddress(startIndex
                        + com[0], startIndex + com[1], com[2], com[3]));// hang
                    // lie
                }
            }
            return endIndex;
        }

    }
}
