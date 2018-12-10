package com.centit.stat.query.po.html.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.centit.stat.query.po.html.AbstractCHtmlComponent;

/**
 * 表格行组件，后台数据和表格TR之间的转换
 *
 * @author zk 2013-6-8
 */
public class CTableLine extends AbstractCHtmlComponent {
    public static final String TABLE_LINE_MODEL = "<tr${id}${class}${style}>${cells}</tr>";

    public CTableLine(){
        firstCellCol = 0;
    }

    public CTableLine(int lineHeadBlankRows){
        firstCellCol = lineHeadBlankRows;
    }
    /**
     * 单元格集合
     */
    private List<CTableCell> cells;



    private int firstCellCol;

    /**
     * H:行头     N:普通行    T:行尾
     */
    private String  lineType;

    /**
     * 添加单元格
     *
     * @param cell 列
     */
    public void addCell(CTableCell cell) {
        getCells().add(cell);
    }

    public List<CTableCell> getCells() {
        if (null == cells) {
            cells = new ArrayList<CTableCell>();
        }

        return cells;
    }

    @Override
    public String getHtml() {
        String strStyle = (StringUtils.isEmpty(getCssStyle()) ? "" : " style='" + getCssStyle() + "'");
        String strClass = (StringUtils.isEmpty(getCssClass()) ? "" : " class='" + getCssClass() + "'");
        String strId = (StringUtils.isEmpty(getId()) ? "" : " id='" + getId() + "'");

        StringBuilder sb = new StringBuilder();
        for (CTableCell cell : getCells()) {
            sb.append(cell.getHtml());
        }

        // 替换返回
        return TABLE_LINE_MODEL.replace("${id}", strId)
                .replace("${class}", strClass)
                .replace("${style}", strStyle)
                .replace("${cells}", sb.toString());
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public int getFirstCellCol() {
        return firstCellCol;
    }

    public void setFirstCellCol(int firstCellCol) {
        this.firstCellCol = firstCellCol;
    }
}
