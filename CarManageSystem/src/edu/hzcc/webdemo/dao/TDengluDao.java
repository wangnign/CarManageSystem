package edu.hzcc.webdemo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.hzcc.webdemo.pojo.ChufariqiPiaoshu;

import edu.hzcc.webdemo.sys.ProjectShare;
import edu.hzcc.webdemo.tables.TDenglu;

public class TDengluDao {
	public static TDenglu findByUsernameAndPassword(String username,String password){
		TDenglu po = null;
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			String sql = "select * from tdenglu where username='"+username+"' and password='"+password+"'";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			if(rs.next()){
				po = new TDenglu();
				po.setId(rs.getLong("id"));
			
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
	public static boolean add(TDenglu po){
		try {
			
			
			Connection connection = ProjectShare.getDbPool().getConnection();
			ProjectShare.getDbPool().transaction(connection, true);
			ProjectShare.getDbPool().update(connection, insertSQLTDenglu(po));
			ProjectShare.getDbPool().commit(connection);
			ProjectShare.getDbPool().transaction(connection, false);
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TDengluDao.add error: "+e.getMessage());
			return false;
		}
		
	}
	public static String insertSQLTDenglu(TDenglu po){
		String sql = "insert into tdenglu(type,name,username,password)";
		sql += " values("+po.getType()+",'"+po.getName()+"','"+po.getUsername()+"','"+po.getPassword()+"')";
		ProjectShare.log("sql->"+sql);
		return sql;
	}
	public static List<TDenglu> findDengluByType(int type){
		try {
			List<TDenglu> list = new ArrayList<>();
			Connection connection = ProjectShare.getDbPool().getConnection();
			
			String sql = "select * from tdenglu where type="+type+" order by type";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			while(rs.next()){
				TDenglu manager = convertDenglu(rs);
				list.add(manager);
				
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TDengluDao.findDengluByType error: "+e.getMessage());
			return null;
		}
	}
	public static List<ChufariqiPiaoshu> allChufariqiPiaoshuList(){
		try {
			List<ChufariqiPiaoshu> list = new ArrayList<>();
			Connection connection = ProjectShare.getDbPool().getConnection();
			String sql = "select distinct type from tdenglu order by type desc";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			while(rs.next()){
				ChufariqiPiaoshu tyrs = new ChufariqiPiaoshu();
				tyrs.setChufariqi(rs.getString("chufariqi"));
				list.add(tyrs);
			}
			rs.close();
			
			for(ChufariqiPiaoshu tyrs: list){
				sql = "select count(*) from tdenglu where type="+tyrs.getType();
				rs = ProjectShare.getDbPool().query(connection, sql);
				if(rs.next()){
					tyrs.setPiaoshu(rs.getString(1));
				}
				rs.close();
			}
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TDengluDao.allChufariqiPiaoshuList error: "+e.getMessage());
			return null;
		}
	}
	private static TDenglu convertDenglu(ResultSet rs) throws Exception{
		TDenglu manager = new TDenglu();
		manager.setId(rs.getLong("id"));
		manager.setType(rs.getInt("type"));
		manager.setName(rs.getString("name"));
		manager.setUsername(rs.getString("username"));
		manager.setPassword(rs.getString("password"));
		return manager;
	}
	
	
}
