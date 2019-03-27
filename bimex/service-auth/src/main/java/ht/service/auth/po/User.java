package ht.service.auth.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public class User implements Serializable {
	private String userId;
	private String userName;
	private String name;
	private String phoneNum;
	private String status;
	private Boolean ifOpenWebLogin;
	private Boolean ifOpenAppLogin;
	private Date firstWebLoginTime;
	private Date firstAppLoginTime;
	private Boolean domainAccount;

	public Boolean getDomainAccount() {
		return domainAccount;
	}

	public void setDomainAccount(Boolean domainAccount) {
		this.domainAccount = domainAccount;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Boolean getIfOpenWebLogin() {
		return ifOpenWebLogin;
	}

	public void setIfOpenWebLogin(Boolean ifOpenWebLogin) {
		this.ifOpenWebLogin = ifOpenWebLogin;
	}

	public Boolean getIfOpenAppLogin() {
		return ifOpenAppLogin;
	}

	public void setIfOpenAppLogin(Boolean ifOpenAppLogin) {
		this.ifOpenAppLogin = ifOpenAppLogin;
	}

	public Date getFirstWebLoginTime() {
		return firstWebLoginTime;
	}

	public void setFirstWebLoginTime(Date firstWebLoginTime) {
		this.firstWebLoginTime = firstWebLoginTime;
	}

	public Date getFirstAppLoginTime() {
		return firstAppLoginTime;
	}

	public void setFirstAppLoginTime(Date firstAppLoginTime) {
		this.firstAppLoginTime = firstAppLoginTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
