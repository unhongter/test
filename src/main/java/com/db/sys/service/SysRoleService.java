package com.db.sys.service;
import java.util.List;

import com.db.common.vo.CheckBox;
import com.db.sys.entity.SysRole;
import com.db.sys.vo.SysRoleMenuVo;
public interface SysRoleService extends PageService<SysRole>{
	
	/**
	 * 判定橘色名称是否存在
	 * @param name
	 */
	void isRoleExist(String name);
	
	/**
	 * 查询所有角色信息
	 * @return
	 */
	List<CheckBox> findObjects();
	
	/**
	 * 基于角色id获取角色以及对应的菜单信息
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);
	
	/**
	 * 将角色自身信息以及对应的菜单更新到数据库
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int updateObject(SysRole entity,
			Integer[] menuIds);
	/**
	 * 将角色自身信息以及对应的菜单写入到数据库
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int saveObject(SysRole entity,
			Integer[] menuIds);
	
	/**
	 * 基于角色id删除角色相关信息
	 * @param id
	 * @return
	 */
	 int deleteObject(Integer id);
	
 

}
