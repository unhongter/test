package com.db.common.vo;
import java.io.Serializable;
/**
 * 
 * FAQ?为什么设计这个对象
 * 1)使用此对象封装和checkbox相关的信息
 * FAQ?那使用map封装不可以吗？可以，但是
 * 1)值类型不控
 * 2)可读性不好(打开map源码不知道其内部存储了什么)
 * 
 */
public class CheckBox implements Serializable{
	private static final long serialVersionUID = 5127226184862470973L;
	private Integer id;
	private String name;
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
	
}
