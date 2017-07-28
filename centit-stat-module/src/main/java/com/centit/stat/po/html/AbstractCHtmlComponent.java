package com.centit.stat.po.html;

/**
 * HTML组件
 * 
 * @author zk
 * @create 2013-6-8
 * @version 
 */
public abstract class AbstractCHtmlComponent {
    private String id;
    
    /**
     * 样式
     */
    private String cssStyle = "";
    
    /**
     * class
     */
    private String cssClass = "";
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public abstract String getHtml();
}
