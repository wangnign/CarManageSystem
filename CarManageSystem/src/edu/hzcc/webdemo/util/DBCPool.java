package edu.hzcc.webdemo.util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;


/**
 * dbcp的连接池对象
 * @author icekinglele
 *
 */
public class DBCPool {
	
	/**
	 * 数据库的连接配置文件
	 * 默认是 user.dir/conf_dbcp.properties
	 */
	private File configFile;
	
	/**
	 * dbcp的配置文件信息
	 */
	private Properties dbProperties;
	
	
	public DBCPool(File configFile){
		this.configFile = configFile;
	}

	public DBCPool(Properties dbProperties) {
		this.dbProperties = dbProperties;
	}

	/**
	 * 构造时候就初始化连接池对象
	 */
	public void init(){
		if(dataSource != null)
			return;
		try {
			if(dbProperties != null){
				//直接使用配置对象构造ds
				dataSource = (BasicDataSource)BasicDataSourceFactory.createDataSource(dbProperties);
			}else {
				//没有设置过dbProperties的，那么使用配置文件读取
				//到user.dir下面去找到配置文件，
				if(!configFile.exists())
					return;
				FileInputStream fis = new FileInputStream(configFile);
				Properties dbProp = new Properties();
				dbProp.load(fis);
				fis.close();
				dataSource = (BasicDataSource)BasicDataSourceFactory.createDataSource(dbProp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataSource = null;
		}
	}
	
	/**
	 * 释放连接池
	 */
	public void destroy(){
		if(dataSource == null)
			return;
		try {
			dataSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 数据源对象
	 */
	private BasicDataSource dataSource;
	
	/**
	 * 获得一个连接
	 * @return
	 * @throws SQLException 
	 */
	public Connection getConnection() throws SQLException{
		init();
		return dataSource.getConnection();
	}
	
	/**
	 * 关闭 一个连接
	 * @param connection
	 * @throws SQLException 
	 */
	public void closeConnection(Connection connection) throws SQLException{
		connection.close();
	}
	
	/**
	 * 执行数据库查询
	 * @param sql
	 * @return
	 * @throws SQLException 
	 */
	public ResultSet query(Connection connection,String sql) throws SQLException{
		Statement statement = connection.createStatement();
		return statement.executeQuery(sql);
	}
	
	/**
	 * 执行更新操作
	 * @param sql
	 * @return
	 * @throws SQLException 
	 */
	public int update(Connection connection,String sql) throws SQLException{
		Statement statement = connection.createStatement();
		return statement.executeUpdate(sql);
	}
	
	/**
	 * 开启或关闭事务事务
	 * @param connection
	 * @throws SQLException 
	 */
	public void transaction(Connection connection,boolean inuse) throws SQLException{
		connection.setAutoCommit(!inuse);
	}
	
	/**
	 * 提交事务
	 * @param connection
	 * @throws SQLException 
	 */
	public void commit(Connection connection) throws SQLException{
		if(connection.getAutoCommit())
			return;
		connection.commit();
	}
	
	/**
	 * 回滚事务
	 * @param connection
	 * @throws SQLException 
	 */
	public void rollback(Connection connection) throws SQLException{
		if(connection.getAutoCommit())
			return;
		connection.rollback();
	}
	
	public String getDBDriver(){
		if(dataSource == null)
			return "";
		return dataSource.getDriverClassName();
	}
	
	public String getDBUrl(){
		if(dataSource == null)
			return "";
		return dataSource.getUrl();
	}
	
}
