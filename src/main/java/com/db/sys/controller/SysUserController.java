package com.db.sys.controller;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;

@RequestMapping("/user/")
@Controller
public class SysUserController {
	   @Autowired
	   private SysUserService sysUserService;
	   @RequestMapping("doUserListUI")
	   public String doUserListUI() {
		   return "sys/user_list";
	   }
	   @RequestMapping("doUserEditUI")
	   public String doUserEditListUI() {
		   return "sys/user_edit";
	   }
	   @RequestMapping("doFindObjectById")
	   @ResponseBody
	   public JsonResult doFindObjectById(
			   Integer id) {
		   Map<String,Object> map=
		   sysUserService.findObjectById(id);
		   return new JsonResult(map);
	   }
	   @RequestMapping("doUpdateObject")
	   @ResponseBody
	   public JsonResult doUpdateObject(
			   SysUser entity,
			   Integer[] roleIds) {
		   sysUserService.updateObject(entity, roleIds);
		   return new JsonResult("update ok");
	   }
	   @RequestMapping("doSaveObject")
	   @ResponseBody
	   public JsonResult doSaveObject(
			  SysUser entity,
			  Integer[] roleIds) {

		      sysUserService.saveObject(entity, roleIds);
		      return new JsonResult("save ok");
	   }
	@RequiresPermissions("sys:user:valid")
	   @RequestMapping("doValidById")
	   @ResponseBody
	   public JsonResult doValidById(Integer id,Integer valid) {
		  SysUser  user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		   sysUserService.validById(id, valid, user.getUsername());
		   return new JsonResult("update ok");
	   }
	   
	   @RequestMapping("doFindPageObjects")
	   @ResponseBody
	   public JsonResult doFindPageObjects(Integer pageCurrent,String username) {
		  return new JsonResult(
		  sysUserService.findPageObjects(pageCurrent, username));
	   }
	   @RequestMapping("doLogin")
	   @ResponseBody
	public JsonResult doLogin(boolean isRememberMe,String username,String password){
		   //1.获取Subject对象
		   Subject subject=SecurityUtils.getSubject();
		   //2.通过Subject提交用户信息,交给shiro框架进行认证操作
		   //2.1对用户进行封装
		   UsernamePasswordToken token= new UsernamePasswordToken(username, password);//凭证信息
		   //2.2对用户信息进行身份认证
		   if(isRememberMe) {
			   token.setRememberMe(true);
		   }
		   subject.login(token);
		   //分析:
		   //1)token会传给shiro的SecurityManager
		   //2)SecurityManager将token传递给认证管理器
		   //3)认证管理器会将token传递给realm
		   return new JsonResult("登陆成功");
	   }
	   @RequestMapping("checkName.do")
		@ResponseBody
	public JsonResult checkName(String username){
	   		sysUserService.getRowCount1(username);
	   		return new JsonResult();
	   }


	@RequestMapping("doPwdEditUI")
	public String doPwdEditUI(){
		return "sys/pwd_edit";
	}


	@RequestMapping("doUpdatePassword")
	@ResponseBody
	public JsonResult doUpdatePassword(String pwd, String newPwd, String cfgPwd) {
		if (sysUserService.updatePassword(pwd, newPwd, cfgPwd)>0){
			return JsonResult.ok("操作成功");
		}
		return JsonResult.error("操作失败");
	}


}
