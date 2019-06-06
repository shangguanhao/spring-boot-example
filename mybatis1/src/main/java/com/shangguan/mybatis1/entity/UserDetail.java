package com.shangguan.mybatis1.entity;

import java.util.List;

public class UserDetail {
	
    private Integer userid;

    private String username;

    private String password;
    
    private List<Integer> roleids;
	
	private List<String> rolenames;


	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Integer> getRoleids() {
		return roleids;
	}

	public void setRoleids(List<Integer> roleids) {
		this.roleids = roleids;
	}

	public List<String> getRolenames() {
		return rolenames;
	}

	public void setRolenames(List<String> rolenames) {
		this.rolenames = rolenames;
	}

	@Override
	public String toString() {
		return "UserDetail [userid=" + userid + ", username=" + username + ", password=" + password + ", roleids="
				+ roleids + ", rolenames=" + rolenames + "]";
	}
    
}
