package edu.hzcc.webdemo.sys;

import java.io.InputStream;
import java.util.Properties;

import edu.hzcc.webdemo.util.DBCPool;

/**
 * 针对本项目共享的一些静态内容
 * @author vboxwin7
 *
 */
public class ProjectShare {
	
	//=========初始化数据库连接池对象=================
	
	/**
	 * 数据库连接池对象
	 */
	private static DBCPool dbPool;

	public static DBCPool getDbPool() {
		return dbPool;
	}

	public static void setDbPool(DBCPool dbPool) {
		ProjectShare.dbPool = dbPool;
	}
	
	/**
	 * 初始化连接池，使用 /conf_dbcp.properties
	 */
	public static void initDatabasePool(){
		if(dbPool != null)
			return;
		try {
			Properties dbprop = new Properties();
			InputStream is = ProjectShare.class.getResourceAsStream("/conf_dbcp.properties");
			dbprop.load(is);
			is.close();
			dbPool = new DBCPool(dbprop);
			dbPool.init();
			log("database pool inited.");
			log("driver: "+dbPool.getDBDriver());
			log("url: "+dbPool.getDBUrl());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			dbPool = null;
		}
	}
	
	public static void destroyDatabasePool(){
		if(dbPool == null)
			return;
		dbPool.destroy();
	}
	
	//==============================================
	
	public static void log(String str){
		System.out.println(str);
	}
}
