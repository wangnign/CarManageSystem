package edu.hzcc.webdemo.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.hzcc.webdemo.sys.ProjectShare;
import edu.hzcc.webdemo.util.ControllerBase;
import edu.hzcc.webdemo.util.JsonResult;

public class My1Controller extends ControllerBase{
	private long id;
	
	public void html(){
		String html = "<p>hello, from My1Controller. 你好啊.</p>";
		writeHtml(html);
	}
	
	public void json(){
		JsonResult result = new JsonResult(false, "hello, 测试json的使用");
		writeJson(result.toJson());
	}
	
	/**
	 * 测试mysql数据库的连接
	 * 返回json格式
	 */
	public void mysql(){
		try {
			//向连接池申请一个数据库连接
			Connection connection = ProjectShare.getDbPool().getConnection();
			String sql = "select * from syslog limit 10";
			ResultSet rs = ProjectShare.getDbPool().query(connection, sql);
			List<String> list = new ArrayList<>();
			while(rs.next()){
				long id = rs.getLong("id");
				long time = rs.getLong("time");
				String what = rs.getString("what");
				String who = rs.getString("who");
				String str = "id="+id+", time="+time+", what="+what+", who="+who;
				list.add(str);
			}
			rs.close();
			JsonResult result = new JsonResult(true,"数据检索成功");
			result.setRows(list);
			writeJson(result.toJson());
			
			//连接使用完毕之后，关闭
			ProjectShare.getDbPool().closeConnection(connection);
		} catch (Exception e) {
			// TODO: handle exception
			JsonResult result = new JsonResult(false, "错误："+e.getMessage());
			writeJson(result.toJson());
		}
	}
	
	public void jsp(){
		List<String> list = new ArrayList<>();
		for(int i=0;i<10;i++){
			list.add("字符串 "+System.currentTimeMillis());
		}
		getRequest().setAttribute("list", list);
		
		toPage("jsp1.jsp");
	}
}
