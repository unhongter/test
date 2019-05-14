package com.db.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.CheckBox;
import com.db.sys.entity.SysRole;
import com.db.sys.vo.SysRoleMenuVo;

public interface SysRoleDao {
	/**
	 * 按名字统计角色相关记录
	 * @param name
	 * @return
	 */
	int getRowCountByName(String name);
	
    /***
     * 查询所有角色信息
     * @return
     */
	List<CheckBox> findObjects();
	/**
	 * 基于角色id查询角色以及对应的菜单信息
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);
	/**
	 * 将角色信息更新到数据库
	 * @param entity
	 * @return
	 */
	int updateObject(SysRole entity);
	
	/**
	 * 将角色信息写入数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysRole entity);
	
	 /**
	  * 基于角色id删除角色自身信息
	  * @param id
	  * @return
	  */
	 int deleteObject(Integer id);
	
	 int getRowCount(@Param("name")String name);
	 List<SysRole> findPageObjects(
			 @Param("name")String name,
			 @Param("startIndex")Integer startIndex,
			 @Param("pageSize") Integer pageSize);
}
