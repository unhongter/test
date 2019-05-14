package com.db.sys.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;

/** 菜单数据层对象*/
public interface SysMenuDao {
	
	/**
	 * 将内存中的menu对象更新到数据库
	 * @param entity 菜单对象，其内容最初来自页面
	 * @return 修改的行数
	 */
	int updateObject(SysMenu entity);
	 /**
	  * 将内存中的menu对象持久化到数据库
	  * @param menu 菜单对象，其内容最初来自页面
	  * @return 插入的行数
	  */
	 int insertObject(SysMenu entity);
	 /**
	  * 查询菜单的节点信息
	  * @return
	  */
	 List<Node> findZtreeMenuNodes();
	 /**
	  * 统计菜单是否有子菜单
	  * @param id
	  * @return 子菜单的个数
	  */
	 int getChildCount(Integer id);
	 /**
	  * 基于id删除菜单对象
	  * @return 删除的行数
	  */
	 int deleteObject(Integer id);
	 
	 
	 List<Map<String,Object>> findObjects();

	 List<String> findPermissions(@Param("ids") Integer[] id);
}
