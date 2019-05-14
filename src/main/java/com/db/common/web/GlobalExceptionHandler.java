package com.db.common.web;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.db.common.vo.JsonResult;
/**
 * @ControllerAdvice 修饰的类为一个全局异常处理类
 * 说明：此类需要在配置文件(spring-web.xml)中进行
 * 扫描配置。
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	 /**
	 * @ExceptionHandler 注解修饰的方法为异常处理方法
	 * @param e
	 * @return
	 */
     @ExceptionHandler(RuntimeException.class)
	 @ResponseBody
     public JsonResult doHandleRuntimeException(
			 RuntimeException e) {
		 e.printStackTrace();
		 return new JsonResult(e);
	 }

	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public JsonResult doHandleShiroException(
			ShiroException e) {
		JsonResult r=new JsonResult();
		r.setState(0);
		if(e instanceof UnknownAccountException) {
			logger.error(e.getMessage());
			r.setMessage("账户不存在");
		}else if(e instanceof LockedAccountException) {
			logger.error(e.getMessage());
			r.setMessage("账户已被禁用");
		}else if(e instanceof IncorrectCredentialsException) {
			logger.error(e.getMessage());
			r.setMessage("密码不正确");
		}else if(e instanceof AuthorizationException) {
			logger.error(e.getMessage());
			r.setMessage("没有此操作权限");
		}else {
			logger.error(e.getMessage());
			r.setMessage("系统维护中");
		}
		e.printStackTrace();
		return r;
	}
}



