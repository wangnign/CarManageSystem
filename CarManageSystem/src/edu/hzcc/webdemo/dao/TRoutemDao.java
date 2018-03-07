package edu.hzcc.webdemo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.hzcc.webdemo.sys.ProjectShare;
import edu.hzcc.webdemo.tables.TRoutem;

public class TRoutemDao {
	public static List<TRoutem> allRoutemList(){
		try {
			List<TRoutem> list = new ArrayList<>();
			Connection connection = ProjectShare.getDbPool().getConnection();
			
			String sql = "select * from troutem";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			
			//System.out.println("sql = "+sql +"\n"+ rs.getFetchSize());
			
			while(rs.next()){
				TRoutem course = convertRoutem(rs);
				list.add(course);
			
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TRoutemDao.allRoutemList error: "+e.getMessage());
			return null;
		}
	}
	
	private static TRoutem convertRoutem(ResultSet rs) throws Exception{
		TRoutem course = new TRoutem();
		course.setId(rs.getLong("id"));
		course.setXianluhao(rs.getString("xianluhao"));
		course.setChufadi(rs.getString("chufadi"));
		course.setMudidi(rs.getString("mudidi"));
		course.setJiage(rs.getString("jiage"));
		course.setZongpiaoshu(rs.getString("zongpiaoshu"));
		course.setSuoxushijian(rs.getString("suoxushijian"));
		
		return course;
	}
	
	public static TRoutem findById(long id){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			TRoutem course = null;
			String sql = "select * from troutem where id="+id;
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			if(rs.next()){
				course = convertRoutem(rs);
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return course;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TRoutemDao.findById error: "+e.getMessage());
			return null;
		}
	}
	
	public static TRoutem findByxianluhao(String xianluhao,long exceptId){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			TRoutem cou = null;
			String sql = "select * from troutem where xianluhao='"+xianluhao+"'";
			if(exceptId>0){
				sql += " and id<>"+exceptId;
			}
			sql += " limit 1";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			if(rs.next()){
				cou = convertRoutem(rs);
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return cou;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TRoutemDao.findByxianluhao error: "+e.getMessage());
			return null;
		}
	}
	public static boolean save(TRoutem cou){
		try {
			String sql = null;
			if(cou.getId()>0){
				//执行修改
			
				sql = "update troutem set xianluhao='"+cou.getXianluhao()+"'";
				sql += ",chufadi='"+cou.getChufadi()+"'";
				sql += ",mudidi='"+cou.getMudidi()+"'";
				sql += ",jiage='"+cou.getJiage()+"'";
				sql += ",zongpiaoshu='"+cou.getZongpiaoshu()+"'";
				sql += ",suoxushijian='"+cou.getSuoxushijian()+"'";
				sql += "where id="+cou.getId();
				System.out.println(sql);
				//System.out.println("chufashijian" + cou.getChufashijian());
			}else {
				//执行新增
				sql = "insert into troutem(Id,xianluhao,chufadi,mudidi,jiage,zongpiaoshu,suoxushijian)";
				sql += " values('"+cou.getId()+"','"+cou.getXianluhao()+"','"+cou.getChufadi()+"','"+cou.getMudidi()+"','"+cou.getJiage()+"','"+cou.getZongpiaoshu()+"','"+cou.getSuoxushijian()+"')";
			}
			
			Connection connection = ProjectShare.getDbPool().getConnection();
			ProjectShare.getDbPool().transaction(connection, true);
			ProjectShare.getDbPool().update(connection, sql);
			ProjectShare.getDbPool().commit(connection);
			ProjectShare.getDbPool().transaction(connection, false);
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TRoutemDao.save error: "+e.getMessage());
			return false;
		}
		
	}
	public static boolean deleteById(long id){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			String sql = "delete from troutem where id="+id;
			int i=ProjectShare.getDbPool().update(connection, sql);
			
			ProjectShare.getDbPool().closeConnection(connection);
			if(i==1)
				return true;
			return false;
			          
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TRoutemDao.deleteById error: "+e.getMessage());
			return false;
		}

  }

}
