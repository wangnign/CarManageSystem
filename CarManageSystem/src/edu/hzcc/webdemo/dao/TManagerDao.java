package edu.hzcc.webdemo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.hzcc.webdemo.pojo.ChufariqiPiaoshu;
import edu.hzcc.webdemo.sys.ProjectShare;

import edu.hzcc.webdemo.tables.TManager;

public class TManagerDao {
	
	private static TManager convertManager(ResultSet rs) throws Exception{
		TManager manager = new TManager();
		manager.setId(rs.getLong("id"));
		manager.setType(rs.getInt("type"));
		manager.setName(rs.getString("name"));
		manager.setUsername(rs.getString("username"));
		manager.setPassword(rs.getString("password"));
		return manager;
	}
	
	public static List<TManager> findManagerByType(int type){
		try {
			List<TManager> list = new ArrayList<>();
			Connection connection = ProjectShare.getDbPool().getConnection();
			
			String sql = "select * from tdenglu where type="+type+" order by type";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			while(rs.next()){
				TManager manager = convertManager(rs);
				list.add(manager);
				
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TManagerDao.findManagerByType error: "+e.getMessage());
			return null;
		}
	}
	
	public static String insertSQLTManager(TManager po){
		String sql = "insert into tdenglu(type,name,username,password)";
		sql += " values("+po.getType()+",'"+po.getName()+"','"+po.getUsername()+"','"+po.getPassword()+"')";
		ProjectShare.log("sql->"+sql);
		return sql;
	}
	
	/**
	 * 在tmanager表中建立初始化对象
	 */
	public static void initManager(){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			String sql = "select count(*) from tdenglu";
			boolean exist = false;
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			if(rs.next()){
				long count = rs.getLong(1);
				if(count>0)
					exist = true;
			}
			rs.close();
			
			if(!exist){
				//插入一条超级管理员的信息
				TManager po = new TManager();
				po.setName("SuperManager");
				po.setUsername("admin");
				po.setPassword("admin");
				po.setType(99);
				
				ProjectShare.getDbPool().update(connection, insertSQLTManager(po));
			}
			
			ProjectShare.getDbPool().closeConnection(connection);
		} catch (Exception e) {
			ProjectShare.log("TManagerDao.initManager error: "+e.getMessage());
		}
	}
	public static TManager findByUsernameAndPassword(String username,String password){
		TManager po = null;
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			String sql = "select * from tmanager where username='"+username+"' and password='"+password+"'";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			if(rs.next()){
				po = new TManager();
				po.setId(rs.getLong("id"));
				po.setName(rs.getString("name"));
				po.setUsername(rs.getString("username"));
				po.setPassword(rs.getString("password"));
				po.setType(rs.getInt("type"));
			}
			rs.close();
			ProjectShare.getDbPool().closeConnection(connection);
			
			return po;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TManagerDao.findByUsernameAndPassword error: "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 根据用户名和密码找到对象
	 * @param username
	 * @param password
	 * @return
	 */

	}
