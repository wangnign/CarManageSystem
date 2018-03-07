package edu.hzcc.webdemo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.hzcc.webdemo.sys.ProjectShare;
import edu.hzcc.webdemo.tables.TPerson;




public class TPersonDao {
	public static List<TPerson> allPersonList(){
		try {
			List<TPerson> list = new ArrayList<>();
			Connection connection = ProjectShare.getDbPool().getConnection();
			
			String sql = "select * from tperson";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			
			//System.out.println("sql = "+sql +"\n"+ rs.getFetchSize());
			
			while(rs.next()){
				TPerson course = convertPerson(rs);
				list.add(course);
			
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TPersonDao.allRouteList error: "+e.getMessage());
			return null;
		}
	}
	
	private static TPerson convertPerson(ResultSet rs) throws Exception{
		TPerson course = new TPerson();
		course.setId(rs.getLong("id"));
		course.setIdcard(rs.getString("idcard"));
		course.setName(rs.getString("name"));
		course.setPassword(rs.getString("password"));
		course.setSex(rs.getString("sex"));
		course.setXianluhao(rs.getString("xianluhao"));
		course.setChufashijian(rs.getString("chufashijian"));
		course.setChehao(rs.getString("chehao"));
		
		return course;
	}
	
	public static TPerson findById(long id){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			TPerson course = null;
			String sql = "select * from tperson where id="+id;
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			if(rs.next()){
				course = convertPerson(rs);
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return course;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TPersonDao.findById error: "+e.getMessage());
			return null;
		}
	}
	
	public static TPerson findByidcard(String idcard,long exceptId){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			TPerson cou = null;
			String sql = "select * from tperson where idcard='"+idcard+"'";
			if(exceptId>0){
				sql += " and id<>"+exceptId;
			}
			sql += " limit 1";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			if(rs.next()){
				cou = convertPerson(rs);
			}
			rs.close();
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return cou;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TPersonDao.findByidcard error: "+e.getMessage());
			return null;
		}
	}
	public static boolean save(TPerson cou){
		try {
			String sql = null;
			if(cou.getId()>0){
				//执行修改
			
				sql = "update tperson set idcard='"+cou.getIdcard();
				sql += "',name='"+cou.getName()+"'";
				sql += ",password='"+cou.getPassword()+"'";
				sql += ",sex='"+cou.getSex();
				sql += "',xianluhao='"+cou.getXianluhao();
				sql += "',chufashijian='"+cou.getChufashijian();
				sql += "',chehao='"+cou.getChehao();
				sql += "' where id='"+cou.getId()+"'";
				System.out.println(sql);
		
			}else {
				//执行新增
				sql = "insert into tperson(id,idcard,name,password,sex,xianluhao,chufashijian,chehao)";
				sql += " values('"+cou.getId()+"','"+cou.getIdcard()+"','"+cou.getName()+"','"+cou.getPassword()+"','"+cou.getSex()+"','"+cou.getXianluhao()+"','"+cou.getChufashijian()+"','"+cou.getChehao()+"')";
				 System.out.println(sql);
		
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
			ProjectShare.log("TPersonDao.save error: "+e.getMessage());
			return false;
		}
		
	}
	public static boolean save1(TPerson cou){
		try {
			String sql = null;
			
	
				//执行新增
				sql = "insert into tperson(id,idcard,name,password,sex,xianluhao,chufashijian,chehao)";
				sql += " values('"+cou.getId()+"','"+cou.getIdcard()+"','"+cou.getName()+"','"+cou.getPassword()+"','"+cou.getSex()+"','"+cou.getXianluhao()+"','"+cou.getChufashijian()+"','"+cou.getChehao()+"')";
				 System.out.println(sql);
				Connection connection = ProjectShare.getDbPool().getConnection();
				ProjectShare.getDbPool().transaction(connection, true);
				ProjectShare.getDbPool().update(connection, sql);
				
				 sql = "update troute  set shengyupiaoshu = shengyupiaoshu-"+cou.getPassword()+" where xianluhao= '"+cou.getXianluhao()+"'";
				 System.out.println(sql);
			

			ProjectShare.getDbPool().update(connection, sql);
			ProjectShare.getDbPool().commit(connection);
			ProjectShare.getDbPool().transaction(connection, false);
			
			ProjectShare.getDbPool().closeConnection(connection);
			
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TPersonDao.save error: "+e.getMessage());
			return false;
		}
		
	}
	public static boolean deleteById(long id){
		try {
			Connection connection = ProjectShare.getDbPool().getConnection();
			String sql = "delete from tperson where id="+id;
			int i=ProjectShare.getDbPool().update(connection, sql);
			
			ProjectShare.getDbPool().closeConnection(connection);
			if(i==1)
				return true;
			return false;
			          
		} catch (Exception e) {
			// TODO: handle exception
			ProjectShare.log("TPersonDao.deleteById error: "+e.getMessage());
			return false;
		}

  }
	
}
