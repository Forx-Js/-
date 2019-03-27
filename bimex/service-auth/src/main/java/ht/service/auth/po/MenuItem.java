package ht.service.auth.po;

import java.io.Serializable;

/**
 * Created by zeng.ling on 2017/11/17.
 */
public class MenuItem implements Serializable {

    private String menuItemId;
    private String parentMenuItemId;
    private String title;
    private String url;
    private String icon;
    private String focusIcon;
    private Integer orderIndex;
    private Integer level;
    private Boolean visible;

    /**
     * 模块名称
     */
    private String moduleId;

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getParentMenuItemId() {
        return parentMenuItemId;
    }

    public void setParentMenuItemId(String parentMenuItemId) {
        this.parentMenuItemId = parentMenuItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFocusIcon() {
        return focusIcon;
    }

    public void setFocusIcon(String focusIcon) {
        this.focusIcon = focusIcon;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
