package com.centit.stat.po.html.table;

import java.util.ArrayList;
import java.util.List;

import com.centit.stat.po.html.AbstractCHtmlComponent;

/**
 * 表格内容组件
 *
 * @author zk 2013-6-8
 *
 */
public abstract class AbstractCTableBody extends AbstractCHtmlComponent{

    /**
     * tr集合
     */
    private List<CTableLine> lines;


    /**
     * 添加单元格
     *
     * @param cell 单元格
     */
    public void addCell(CTableCell cell) {
        if (getLines().size()<1) {
            lines.add(new CTableLine());
        }
        int length = getLines().size();
        CTableLine line=lines.get(length - 1);
        String displayValue=cell.getDisplayValue();
        if(displayValue.equals("本页合计")||displayValue.equals("合计")||displayValue.equals("本页平均")||displayValue.equals("平均"))
            {line.setLineType("T");}
        line.addCell(cell);
    }

    /**
     * 新增一行
     */
    public void addLine() {
        getLines().add(new CTableLine());
    }

    public void addLine(int lineHeadBlankRows) {
        getLines().add(new CTableLine(lineHeadBlankRows));
    }

    public List<CTableLine> getLines() {
        if(null==lines)
             lines = new ArrayList<>();
        return lines;
    }
}
