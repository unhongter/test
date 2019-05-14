package com.db.sys.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.exception.ServiceException;
import com.db.common.util.PageUtil;
import com.db.common.vo.CheckBox;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysRoleDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysRole;
import com.db.sys.service.SysRoleService;
import com.db.sys.vo.SysRoleMenuVo;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
	private SysRoleDao sysRoleDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    
    
    @Override
    public void isRoleExist(String name) {
    	int rowCount=sysRoleDao.getRowCountByName(name);
    	if(rowCount>0)
    	throw new ServiceException("此记录已存在");
    }
    
    @Override
    public List<CheckBox> findObjects() {
    	List<CheckBox> list=sysRoleDao.findObjects();
    	if(list==null||list.isEmpty())
        throw new ServiceException("系统没有角色信息");
    	return list;
    }
    
    @Override
    public SysRoleMenuVo findObjectById(Integer id) {
    	//1.参数校验
    	if(id==null||id<1)
        throw new IllegalArgumentException("id参数无效");
    	//2.基于id查询数据
    	//2.1查询角色自身信息
    	//2.2查询角色对应的菜单id
    	SysRoleMenuVo roleMenuVo=
    	sysRoleDao.findObjectById(id);
    	if(roleMenuVo==null)
    	throw new ServiceException("记录不存在");
    	return roleMenuVo;
    }
    @Override
    public int saveObject(SysRole entity, Integer[] menuIds) {
    	//1.参数校验
    	if(entity==null)
    	throw new IllegalArgumentException("保存对象不能为空");
    	if(StringUtils.isEmpty(entity.getName()))
    	throw new IllegalArgumentException("角色名不能为空");
    	if(menuIds==null||menuIds.length==0)
    	throw new IllegalArgumentException("必须为角色分配权限");
    	//2.保存角色自身信息
    	int rows=sysRoleDao.insertObject(entity);
    	//3.保存角色和菜单的关系数据
    	sysRoleMenuDao.insertObjects(entity.getId(),menuIds);
    	//4.返回结果
    	return rows;
    }
    @Override
    public int updateObject(SysRole entity, Integer[] menuIds) {
    	//1.参数校验
    	if(entity==null)
    		throw new IllegalArgumentException("保存对象不能为空");
    	if(StringUtils.isEmpty(entity.getName()))
    		throw new IllegalArgumentException("角色名不能为空");
    	if(menuIds==null||menuIds.length==0)
    		throw new IllegalArgumentException("必须为角色分配权限");
    	//2.更新角色自身信息
    	int rows=sysRoleDao.updateObject(entity);
    	//3.更新角色和菜单的关系数据
    	//3.1先删除关系数据
    	sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
    	//3.2再添加新的关系数据
    	sysRoleMenuDao.insertObjects(entity.getId(),menuIds);
    	//4.返回结果
    	return rows;
    }
    
    
    @Override
    public int deleteObject(Integer id) {
    	//1.参数校验
    	if(id==null||id<1)
    	throw new IllegalArgumentException("id值无效");
    	//2.执行删除
    	//2.1 删除角色和用户关系数据
    	sysUserRoleDao.deleteObjectsByRoleId(id);
    	//2.2 删除角色和菜单关系数据
    	//sysRoleMenuDao.deleteObjectsByColumnId("role_id", id);
    	sysRoleMenuDao.deleteObjectsByRoleId(id);
    	//2.3 删除角色自身信息。
    	int rows=sysRoleDao.deleteObject(id);
    	if(rows==0)
    	throw new ServiceException("记录可能已经不存在");
    	//3.返回结果
    	return rows;
    }
    
	@Override
	public PageObject<SysRole> findPageObjects(Integer pageCurrent, String name) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("当前页码不正确");
		//2.查询总记录数并进行验证
		int rowCount=sysRoleDao.getRowCount(name);
		if(rowCount==0)
	    throw new ServiceException("没有记录");
		//3.查询当前页记录
		int pageSize=10;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysRole> records=
	    sysRoleDao.findPageObjects(name, 
	    	startIndex, pageSize);
		//4.进行封装并返回
		PageObject<SysRole> po =
		PageUtil.newInstance(pageCurrent, rowCount, pageSize, records);
		return po;
	}

	
}



