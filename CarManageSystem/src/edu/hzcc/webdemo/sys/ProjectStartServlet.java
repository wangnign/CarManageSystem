package edu.hzcc.webdemo.sys;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import edu.hzcc.webdemo.dao.TManagerDao;

/**
 * 系统启动时候自动调用的第一个servlet
 * @author vboxwin7
 *
 */
public class ProjectStartServlet extends HttpServlet{
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		ProjectShare.log("===============================================");
		ProjectShare.log("==============Project web demo start==================");
		ProjectShare.log("===============================================");
		
		//初始化连接池
		ProjectShare.initDatabasePool();
		
		//初始化超级管理员
		//TManagerDao.initManager();
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
		ProjectShare.destroyDatabasePool();
		
		ProjectShare.log("===============================================");
		ProjectShare.log("==============Project web demo stop==================");
		ProjectShare.log("===============================================");
		
		super.destroy();
		
	}
}
