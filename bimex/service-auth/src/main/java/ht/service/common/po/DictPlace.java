package ht.service.common.po;

import java.io.Serializable;

/**
 * Created by zhulh on 2018/2/1.
 */
public class DictPlace implements Serializable{

	private String id;
	private String name;
	private String fullName;
	private Integer placeLevel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getPlaceLevel() {
		return placeLevel;
	}

	public void setPlaceLevel(Integer placeLevel) {
		this.placeLevel = placeLevel;
	}
}
