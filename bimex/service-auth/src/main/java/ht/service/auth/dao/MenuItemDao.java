package ht.service.auth.dao;

import ht.service.auth.view.MenuItemView;

import java.util.List;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public interface MenuItemDao {

    /**
     * ORDER BY level, display_index
     * @param departmentId
     * @return
     */
    List<MenuItemView> findDepartmentVisibleMenuItems(String departmentId);
}
