package ht.service.auth.dao;

import ht.service.auth.po.Department;

import java.util.List;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public interface DepartmentDao {
    List<Department> findDepartments(String userId);

    List<Department> findPositions(String userId);

	/**
     * 根据科室id查询该科室职务
     * @param depId
     * @return
     */
    List<Department> findPositionsByDepId(String depId);

    /**
     * 根据职位id查询科室职务
     * @param departmentID
     * @return
     */
    Department findPositionsByPostID(String departmentID);
}
