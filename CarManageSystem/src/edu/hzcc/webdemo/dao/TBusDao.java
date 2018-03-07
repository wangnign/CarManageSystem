package edu.hzcc.webdemo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.hzcc.webdemo.pojo.CheXing;
import edu.hzcc.webdemo.pojo.CheliangShumu;
import edu.hzcc.webdemo.sys.ProjectShare;
import edu.hzcc.webdemo.tables.TBus;


public class TBusDao {
	public static List<CheXing> allCheXingList(){
		try {
			List<CheXing> list = new ArrayList<>();
			Connection connection = ProjectShare.getDbPool().getConnection();
			String sql = "select distinct goumainian from tbus order by goumainian desc";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			while(rs.next()){
				CheXing cx = new CheXing();
				cx.setGoumainian(rs.getInt("goumainian"));
				list.add(cx);
			}
			rs.close();
			
			for(CheXing cx: list){
				sql = "select distinct chexing from tbus where goumainian="+cx.getGoumainian()+" order by chexing";
				rs = ProjectShare.getDbPool().query(connection, sql);
				while(rs.next()){
					CheliangShumu clsm = new CheliangShumu();
					clsm.setChexing (rs.getString(1));
					String sql2 = "select count(*) from tbus where chexing='"+clsm.getChexing()+"' and goumainian = '"+cx.getGoumainian()+"'";
					ResultSet rs2 = ProjectShare.getDbPool().query(connection, sql2);
					if(rs2.next()){
						clsm.setShumu(rs2.getInt(1));
					}
					rs2.close();
					
					cx.add(clsm);
				}
				rs.close();
			}
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TBusDao.allCheXingList error: "+e.getMessage());
			return null;
		}
	}
	
	private static TBus convertBus(ResultSet rs) throws Exception{
		TBus student = new TBus();
		student.setId(rs.getLong("id"));
		student.setGoumainian(rs.getInt("Goumainian"));
		student.setChehao(rs.getString("Chehao"));
		student.setChexing(rs.getString("Chexing"));
		student.setZuidazaikeliang(rs.getString("Zuidazaikeliang"));
		return student;
	}

	//这里 原始是findBusByChexing(String chexing)
	//加一个购买年限，可以确定比如大众 是2015年买的。他就不会出现在2017年下面去了
	//我就在sql语句里面where条件加了一个购买年限。
	public static List<TBus> findBusByChexing(String chexing, String goumainian){
		try {
			List<TBus> list = new ArrayList<>();
			Connection connection = ProjectShare.getDbPool().getConnection();
			
			String sql = "select * from tbus where chexing='"+chexing+"' and goumainian= '"+goumainian+"' order by chehao";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			while(rs.next()){
				TBus student = convertBus(rs);
				list.add(student);
				
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TBusDao.findBusByChexing error: "+e.getMessage());
			return null;
		}
	}
	
	public static TBus findByChehao(String chehao,long exceptId){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			TBus bus = null;
			String sql = "select * from tbus where chehao='"+chehao+"'";
			if(exceptId>0){
				sql += " and id<>"+exceptId;
			}
			sql += " limit 1";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			if(rs.next()){
				bus = convertBus(rs);
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return bus;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TBusDao.findByChehao error: "+e.getMessage());
			return null;
		}
	}
	
	public static TBus findById(long id){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			TBus bus = null;
			String sql = "select * from tbus where id="+id;
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			if(rs.next()){
				bus = convertBus(rs);
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return bus;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TBusDao.findByChehao error: "+e.getMessage());
			return null;
		}
	}
	
	public static boolean save(TBus bus){
		try {
			String sql = null;
			if(bus.getId()>0){
				//执行修改
				sql = "update tbus set goumainian="+bus.getGoumainian();
				sql += ",chehao='"+bus.getChehao()+"'";
				sql += ",chexing='"+bus.getChexing()+"'";
				sql += ",zuidazaikeliang='"+bus.getZuidazaikeliang()+"'";
				sql += " where id="+bus.getId();
			}else {
				//执行新增
				sql = "insert into tbus(goumainian,chehao,chexing,zuidazaikeliang)";
				sql += " values("+bus.getGoumainian()+",'"+bus.getChehao()+"','"+bus.getChexing()+"','"+bus.getZuidazaikeliang()+"')";
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
			ProjectShare.log("TBusDao.save error: "+e.getMessage());
			return false;
		}
		
	}
	public static boolean deleteById(long id){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			String sql = "delete from tbus where id="+id;
			int i=ProjectShare.getDbPool().update(connection, sql);
			
			ProjectShare.getDbPool().closeConnection(connection);
			if(i==1)
				return true;
			return false;
			          
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TBusDao.deleteById error: "+e.getMessage());
			return false;
		}
	}
}
