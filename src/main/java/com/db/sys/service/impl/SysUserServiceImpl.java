package com.db.sys.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.exception.ServiceException;
import com.db.common.util.PageUtil;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;
import com.db.sys.vo.SysUserDeptVo;
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
	private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    //SLF4J
    private static Logger log=LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Override
    public Map<String, Object> findObjectById(Integer id) {
    	//1.参数校验
    	if(id==null||id<1)
    	throw new IllegalArgumentException("id值不正确");
    	//2.基于id查询用户以及对应的部门信息
    	SysUserDeptVo user=
    	sysUserDao.findObjectById(id);
    	if(user==null)
    	throw new ServiceException("用户信息不存在");
    	//3.基于id查询用户对应的角色id
    	List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(id);
    	//4.对信息进行封装
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("user", user);
    	map.put("roleIds", roleIds);
    	return map;
    }
    
    
    
    
    
    @Override
    public int updateObject(SysUser entity,
    		Integer[] roleIds) {
    	//1.参数校验
    	if(entity==null)
    		throw new IllegalArgumentException("保存对象不能为空");
    	if(StringUtils.isEmpty(entity.getUsername()))
    		throw new IllegalArgumentException("用户名不能为空");
    	//....
    	if(roleIds==null||roleIds.length==0)
    		throw new ServiceException("必须为用户分配角色");
    	//2.保存用户自身信息
    	//2.1构建一个盐值对象
    	int rows=sysUserDao.updateObject(entity);
    	if(rows==0)
    	throw new ServiceException("记录可能已经不存在");
    	//3.保存用户和角色关系数据
    	//3.1先删除关系数据
    	sysUserRoleDao.deleteObjectsByUserId(entity.getId());
    	//3.2再添加新的关系数据
    	sysUserRoleDao.insertObjects(
    			entity.getId(),
    			roleIds);
    	//4.返回结果
    	return rows;
    }
    @Override
    public int saveObject(SysUser entity,
    		Integer[] roleIds) {
    	//1.参数校验
    	if(entity==null)
    	throw new IllegalArgumentException("保存对象不能为空");
    	if(StringUtils.isEmpty(entity.getUsername())) {
    	log.debug("username is null");
    	throw new IllegalArgumentException("用户名不能为空");
    	}
    	if(StringUtils.isEmpty(entity.getPassword()))
    	throw new IllegalArgumentException("密码不能为空");
    	//....
    	if(roleIds==null||roleIds.length==0)
        throw new ServiceException("必须为用户分配角色");
    	//2.保存用户自身信息
    	//2.1构建一个盐值对象
    	String salt=UUID.randomUUID().toString();
    	//2.2对密码进行加密
    	//String password1=
    	//DigestUtils.md5DigestAsHex((salt+entity.getPassword()).getBytes());
    	//System.out.println("password1="+password1);
    	SimpleHash sh=new SimpleHash(//Shiro框架提供
    			"MD5",//algorithmName 算法名称
    			entity.getPassword(), //source要加密的对象
    			salt,//盐值
    			1);//hashIterations 加密次数
    	String password2=sh.toHex();
    	System.out.println("password2="+password2);
    	entity.setPassword(password2);
    	entity.setSalt(salt);
    	int rows=sysUserDao.insertObject(entity);
    	//3.保存用户和角色关系数据
    	sysUserRoleDao.insertObjects(
    			entity.getId(),
    			roleIds);
    	//4.返回结果
    	return rows;
    }
    
//    @RequiresPermissions("sys:user:valid")
    @Override
    public int validById(Integer id, Integer valid, String modifiedUser) {
    	//1.参数校验
    	if(id==null||id<1)
        throw new IllegalArgumentException("id值无效");
    	if(valid==null||(valid!=0&&valid!=1))
        throw new IllegalArgumentException("valid值无效");
    	//2.修改状态
    	int rows= sysUserDao.validById(id, valid, modifiedUser);
    	if(rows==0){
			throw new ServiceException("记录可能已经不存在");
		}
    	//3.返回结果
    	return rows;
    }

	@Override
	public boolean getRowCount1(String username) {
    	if (!StringUtils.isEmpty(username)){
			int count = sysUserDao.getRowCount1(username);
			if (count>0){
				throw new ServiceException("用户已存在");
			}
		}
		return true;
	}

	/**
	 * 密码修改
	 * @param password
	 * @param newPassword
	 * @param cfgPassword
	 * @return
	 */
	@Override
	public int updatePassword(String password, String newPassword, String cfgPassword) {
		SysUser  user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		if (user==null){
			throw new ServiceException("该用户不存在，请联系管理员");
		}
    	if (StringUtils.isEmpty(password)){
    		throw new IllegalArgumentException("原密码不能为空");
		}
		if (StringUtils.isEmpty(newPassword)){
			throw new IllegalArgumentException("新密码不能为空");
		}
		if (StringUtils.isEmpty(cfgPassword)){
			throw new IllegalArgumentException("请再次输入新密码");
		}
		if (!(newPassword.equals(cfgPassword))){
    		throw new IllegalArgumentException("两次输入的密码不一致");
		}
		SimpleHash sh=new SimpleHash("MD5",password, user.getSalt(), 1);
		if (!user.getPassword().equals(sh.toHex())){
			throw new ServiceException("原密码不正确");
		}
		//新密码盐值
		String salt=UUID.randomUUID().toString();
		SimpleHash newsh=new SimpleHash("MD5",newPassword, salt, 1);
		int i = sysUserDao.updatePassword(newsh.toHex(), salt, user.getId());
		if (i==0){
			throw new ServiceException("操作失败");
		}
		return i;
	}


	@Override
	public PageObject<SysUserDeptVo> findPageObjects(Integer pageCurrent, String username) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("页码值无效");
		//2.查询总记录数，并进行校验
		int rowCount=sysUserDao.getRowCount(username);
		if(rowCount==0)
		throw new ServiceException("没有对应记录");
		//3.查询当前页要呈现的记录
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysUserDeptVo> records=
		sysUserDao.findPageObjects(username, startIndex, pageSize);
		//4.封装数据并返回
		return PageUtil.newInstance(pageCurrent, rowCount, pageSize, records);
	}

}
