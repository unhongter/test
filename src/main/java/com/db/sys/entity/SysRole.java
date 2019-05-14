package com.db.sys.entity;

public class SysRole extends BaseEntity{
	private static final long serialVersionUID = 7814052030747453305L;
	private Integer id;
	/**角色名称*/
	private String name;
	/**角色备注*/
	private String note;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	 
}
