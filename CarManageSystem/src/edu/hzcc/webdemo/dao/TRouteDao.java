package edu.hzcc.webdemo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.hzcc.webdemo.pojo.ChufariqiPiaoshu;

import edu.hzcc.webdemo.sys.ProjectShare;
import edu.hzcc.webdemo.tables.TRoute;


public class TRouteDao {
	public static List<ChufariqiPiaoshu> allChufariqiPiaoshuList(){
		try {
			List<ChufariqiPiaoshu> list = new ArrayList<>();
			Connection connection = ProjectShare.getDbPool().getConnection();
			String sql = "select distinct chufariqi from troute order by chufariqi desc";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			while(rs.next()){
				ChufariqiPiaoshu tyrs = new ChufariqiPiaoshu();
				tyrs.setChufariqi(rs.getString("chufariqi"));
				list.add(tyrs);
			}
			rs.close();
			
			for(ChufariqiPiaoshu tyrs: list){
				sql = "select count(*) from troute where chufariqi=\""+tyrs.getChufariqi()+"\"";
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
			ProjectShare.log("TRouteDao.allChufariqiPiaoshuList error: "+e.getMessage());
			return null;
		}
	}
	
	private static TRoute convertRoute(ResultSet rs) throws Exception{
		TRoute course = new TRoute();
		course.setId(rs.getLong("id"));
		course.setXianluhao(rs.getString("xianluhao"));
		course.setChufadi(rs.getString("chufadi"));
		course.setMudidi(rs.getString("mudidi"));
		course.setChufariqi(rs.getString("chufariqi"));
		course.setChufashijian(rs.getString("chufashijian"));
		course.setSuoxushijian(rs.getString("suoxushijian"));
		course.setJiage(rs.getString("jiage"));
		course.setShengyupiaoshu(rs.getString("shengyupiaoshu"));
		course.setZongpiaoshu(rs.getString("zongpiaoshu"));
		
		return course;
	}
	
	public static List<TRoute> findRouteByChufariqi(String chufariqi){
		try {
			List<TRoute> list = new ArrayList<>();
			Connection connection = ProjectShare.getDbPool().getConnection();
			
			String sql = "select * from troute where chufariqi = \""+chufariqi+"\" order by chufariqi";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			while(rs.next()){
				TRoute manager = convertRoute(rs);
				list.add(manager);
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TRouteDao.findRouteByChufariqi error: "+e.getMessage());
			return null;
		}
	}
	public static TRoute findById(long id){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			TRoute course = null;
			String sql = "select * from troute where id="+id;
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			if(rs.next()){
				course = convertRoute(rs);
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return course;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TRouteDao.findById error: "+e.getMessage());
			return null;
		}
	}
	
	public static TRoute findByxianluhao(String xianluhao,long exceptId){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			TRoute cou = null;
			String sql = "select * from troute where xianluhao='"+xianluhao+"'";
			if(exceptId>0){
				sql += " and id<>"+exceptId;
			}
			sql += " limit 1";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			if(rs.next()){
				cou = convertRoute(rs);
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return cou;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TRouteDao.findByxianluhao error: "+e.getMessage());
			return null;
		}
	}
	public static boolean save(TRoute cou){
		try {
			String sql = null;
			if(cou.getId()>0){
				//执行修改

				sql = "update troute set xianluhao='"+cou.getXianluhao();
				sql += "',chufadi='"+cou.getChufadi()+"'";
				sql += ",mudidi='"+cou.getMudidi()+"'";
				sql += ",chufariqi='"+cou.getChufariqi()+"'";
				sql += ",chufashijian='"+cou.getChufashijian();
				sql += "',suoxushijian='"+cou.getSuoxushijian();
				sql += "',jiage='"+cou.getJiage();
				sql += "',shengyupiaoshu='"+cou.getShengyupiaoshu();
				sql += "',zongpiaoshu='"+cou.getZongpiaoshu();
				sql += "' where id='"+cou.getId()+"'";
				
			}else {
				//执行新增
				sql = "insert into troute(xianluhao,chufadi,mudidi,chufariqi,chufashijian,suoxushijian,jiage,shengyupiaoshu,zongpiaoshu)";
				sql += " values('"+cou.getXianluhao()+"','"+cou.getChufadi()+"','"+cou.getMudidi()+"','"+cou.getChufariqi()+"','"+cou.getChufashijian()+"','"+cou.getSuoxushijian()+"','"+cou.getJiage()+"','"+cou.getShengyupiaoshu()+"','"+cou.getZongpiaoshu()+"')";
			}
			System.out.println(sql);
			Connection connection = ProjectShare.getDbPool().getConnection();
			ProjectShare.getDbPool().transaction(connection, true);
			ProjectShare.getDbPool().update(connection, sql);
			ProjectShare.getDbPool().commit(connection);
			ProjectShare.getDbPool().transaction(connection, false);
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TRouteDao.save error: "+e.getMessage());
			return false;
		}
		
	}
	public static boolean deleteById(long id){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			String sql = "delete from troute where id="+id;
			int i=ProjectShare.getDbPool().update(connection, sql);
			
			ProjectShare.getDbPool().closeConnection(connection);
			if(i==1)
				return true;
			return false;
			          
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TRouteDao.deleteById error: "+e.getMessage());
			return false;
		}

}

//	//购买
//	public static boolean buyById(long id){
//		try {
//			Connection connection = ProjectShare.getDbPool().getConnection();
//			String sql = "update troute  set shengyupiaoshu = shengyupiaoshu-1 where id = "+ id;
//			int i=ProjectShare.getDbPool().update(connection, sql);
//			
//			ProjectShare.getDbPool().closeConnection(connection);
//			if(i==1)
//				return true;
//			return false;
//			          
//		} catch (Exception e) {
//			// TODO: handle exception
//			ProjectShare.log("TRouteDao.buyById error: "+e.getMessage());
//			return false;
//		}
//	}
	//方案2
	public static boolean buyByid(long id) {
		try {
//			
			
			Connection connection = ProjectShare.getDbPool().getConnection();
			
			
			String sql = "update troute  set shengyupiaoshu = shengyupiaoshu-1 where id = "+ id;
		
			
			
			
			ProjectShare.getDbPool().transaction(connection, true);
			ProjectShare.getDbPool().update(connection, sql);
			ProjectShare.getDbPool().commit(connection);
			ProjectShare.getDbPool().transaction(connection, false);
   ProjectShare.getDbPool().closeConnection(connection);
			
			return true;
			
		
			
		} catch (Exception e) {
			ProjectShare.log("TRouteDao.buyByid error: "+e.getMessage());
			return false;
		}
	}
	
}
