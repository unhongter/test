package com.db.sys.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptVo;

public interface SysUserDao {
	 /**
	  * 基于用户id查询用户以及对应的部门信息
	  * @param id
	  * @return
	  */
	 SysUserDeptVo findObjectById(Integer id);
	 
	 /**
	  * 更新用户自身信息
	  * @param entity
	  * @return
	  */
	 int updateObject(SysUser entity);
	 
	 /**
	  * 保存用户自身信息
	  * @param entity
	  * @return
	  */
	 int insertObject(SysUser entity);
	
	 /**
	  * 禁用或启用用户信息
	  * @param id 用户id
	  * @param valid 状态
	  * @param modifiedUser 修改用户
	  * @return 修改的行数
	  */
	 int validById(@Param("id")Integer id,
			      @Param("valid")Integer valid,
			      @Param("modifiedUser")String modifiedUser);
	
     /**
      * 按条件查询总记录数
      * @param username
      * @return
      */
	 int getRowCount(
			 @Param("username")String username);
	 /**
	  * 按条件查询当前页记录
	  * @param username
	  * @param startIndex
	  * @param pageSize
	  * @return
	  */
	 List<SysUserDeptVo> findPageObjects(
			 @Param("username")String username,
			 @Param("startIndex")Integer startIndex,
			 @Param("pageSize")Integer pageSize);

	SysUser findUserByUserName(String username);

	int getRowCount1(String username);

	/**
	 * 修改密码
	 * @param password
	 * @param salt
	 * @param id
	 * @return
	 */
	int updatePassword(@Param("password") String password,@Param("salt") String salt,@Param("id") Integer id);
}
