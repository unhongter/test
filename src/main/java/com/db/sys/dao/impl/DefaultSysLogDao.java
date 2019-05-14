package com.db.sys.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;

@Repository
public class DefaultSysLogDao implements SysLogDao {
    @Autowired
	private SqlSessionFactory sqlSessionFactory;
	@Override
	public int getRowCount(String username) {
		// TODO Auto-generated method stub
		return 0;
	}
    @Override
	public List<SysLog> findPageObjects(String username, Integer startIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
    @Override
	public int deleteObjects(Integer... ids) {
		//1.获取sqlsession对象
		SqlSession session=
		sqlSessionFactory.openSession();
		//2.执行删除操作
		SysLogDao dao=
		session.getMapper(SysLogDao.class);
		int rows=dao.deleteObjects(ids);
		session.commit();
		//3.释放资源
		session.close();
		return rows;
	}
}
