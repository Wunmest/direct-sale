package com.wunmest.database.entity;

public class Role {

	private int rid;
	private String roleName;
	private String desc;
	
	public Role() {
		// TODO Auto-generated constructor stub
	}
	
	public Role(int rid, String roleName, String desc) {
		super();
		this.rid = rid;
		this.roleName = roleName;
		this.desc = desc;
	}
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
