package com.centit.stat.po.html.table;

import com.centit.stat.po.html.AbstractCHtmlComponent;


/**
 * TABLE组件，负责后台数据和前台TABLE转换
 * 
 * @author zk 2013-6-8
 *
 */
public class CTablePanel extends AbstractCHtmlComponent{
    
    private CTableBodyTHead thead;
    
    private CTableBodyTBody tbody;
    
    public CTablePanel() {
        thead = new CTableBodyTHead();
        tbody = new CTableBodyTBody();
    }
    
    public CTablePanel(CTableBodyTHead head, CTableBodyTBody body) {
        thead = head;
        tbody = body;
    }
    
    public CTableBodyTHead getThead() {
        return thead;
    }

    public void setThead(CTableBodyTHead thead) {
        this.thead = thead;
    }

    public CTableBodyTBody getTbody() {
        return tbody;
    }

    public void setTbody(CTableBodyTBody tbody) {
        this.tbody = tbody;
    }

    @Override
    public String getHtml() {
        // TODO Auto-generated method stub
        return null;
    }
}
