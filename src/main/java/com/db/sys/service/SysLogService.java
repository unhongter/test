package com.db.sys.service;

import com.db.common.vo.PageObject;
import com.db.sys.entity.SysLog;

/**
 * 日志模块业务接口：负责日志业务的规范定义
 */
public interface SysLogService extends PageService<SysLog>{
	 
	 /**
	  * 基于id执行日志删除业务
	  * @param ids 对应多个id的值
	  * @return 删除记录的行数
	  */
	 int deleteObjects(Integer... ids);
	
}





