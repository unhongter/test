package com.db.sys.service.impl;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.db.common.exception.ServiceException;
import com.db.common.util.PageUtil;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;
import com.db.sys.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {
	@Autowired
	private SysLogDao sysLogDao;
//	@RequiresPermissions("sys:log:deleteObjects")
	@Override
	public int deleteObjects(Integer... ids) {
		//1.判定参数有效性
		if(ids==null||ids.length==0)
	    throw new IllegalArgumentException("请先选择");
		//2.基于参数id执行删除操作
		int rows=0;
		try {
		 rows=sysLogDao.deleteObjects(ids);
		}catch(Throwable e) {
		 e.printStackTrace();
		 //报警，给运维人员发短信。
		 throw new ServiceException("系统维护中");
		}
		if(rows==0)
		throw new ServiceException("记录可能已经不存在了");
		//3.返回删除结果
		return rows;
	}
	
	@Override
	public PageObject<SysLog> findPageObjects(Integer pageCurrent, String username) {
		//1.参数的合法性校验
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("页码值无效");	
		//2.基于用户名统计总记录数并进行验证
		System.out.println("username="+username);
		int rowCount=sysLogDao.getRowCount(username);
		if(rowCount==0)
		throw new ServiceException("没有找到对应记录");
		//3.基于查询条件查询当前页的日志信息
		//3.1 定义页面大小
		int pageSize=10;
		//3.2 计算起始位置
		int startIndex=(pageCurrent-1)*pageSize;
		//3.3 基于起始位置执行当前页数据的查询
		List<SysLog> records=
		sysLogDao.findPageObjects(username,
				startIndex, pageSize);
		//4.对查询结果进行封装并返回
		return PageUtil.newInstance(pageCurrent, rowCount, pageSize, records);
	}
}





