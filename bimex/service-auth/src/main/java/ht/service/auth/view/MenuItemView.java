package ht.service.auth.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public class MenuItemView implements Serializable {

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
     * 所属的部门ID
     */
    private String departmentId;
    /**
     * 子菜单
     */
    private List<MenuItemView> subMenuItems;

    public List<MenuItemView> sub() {
        if (subMenuItems == null) {
            subMenuItems = new ArrayList<>();
        }
        return subMenuItems;
    }

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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public List<MenuItemView> getSubMenuItems() {
        return subMenuItems;
    }

    public void setSubMenuItems(List<MenuItemView> subMenuItems) {
        this.subMenuItems = subMenuItems;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
