package ht.service.common.dao;

import ht.service.common.po.DictPlace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public interface DictPlaceDao {

	List<DictPlace> findAllProvince();

	/**
	 * 根据全名获取省份ID
	 * @param fullName
	 * @return
     */
	String findProvinceId(@Param("fullName") String fullName);
}
