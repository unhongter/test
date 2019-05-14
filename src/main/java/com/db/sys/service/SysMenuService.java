package com.db.sys.service;
import java.util.List;
import java.util.Map;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;
public interface SysMenuService {
	/**
	 * 将菜单信息更新到数据库
	 * @param menu
	 * @return
	 */
	 int updateObject(SysMenu menu);
	  /**
	   * 将菜单信息保存到数据库
	   * @param menu
	   * @return
	   */
	  int saveObject(SysMenu menu);
	  
	  List<Node> findZtreeMenuNodes();
	  /**
	   * 基于菜单id删除菜单以及对应的关系数据
	   * @param id
	   * @return
	   */
	  int deleteObject(Integer id);
	  List<Map<String,Object>> findObjects();
}
