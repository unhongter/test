package com.db.sys.vo;

import com.db.sys.entity.BaseEntity;
import com.db.sys.entity.SysDept;
/**
 * 定义值对象封装用户以及关联的部门信息
 * @author Administrator
 */
public class SysUserDeptVo extends BaseEntity {
	private static final long serialVersionUID = 951138124475981917L;
	private Integer id;
	private String username;
	private String password;//md5
	/**盐值:加密时使用,会对应一个随机字符串*/
	private String salt;
	private String email;
	private String mobile;
	private Integer valid=1;
	private SysDept sysDept;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public SysDept getSysDept() {
		return sysDept;
	}
	public void setSysDept(SysDept sysDept) {
		this.sysDept = sysDept;
	} 
	
	

}
