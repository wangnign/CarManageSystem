package edu.hzcc.webdemo.controller;



import java.util.List;

import edu.hzcc.webdemo.dao.TDengluDao;


import edu.hzcc.webdemo.tables.TDenglu;

import edu.hzcc.webdemo.util.ControllerBase;
import edu.hzcc.webdemo.util.JsonResult;
import net.sf.json.JSONObject;

public class TDengluController extends ControllerBase{
	public void login(){
		String username = getParameter("username");
		String password = getParameter("password");
		
		TDenglu loginUser = TDengluDao.findByUsernameAndPassword(username, password);
		if(loginUser == null){
			JsonResult jsonResult = new JsonResult(false, "用户名不存在或者密码错误");
			writeJson(jsonResult.toJson());
			return;
		}
		
		//表示成功的，用户名正确
		getRequest().getSession().setAttribute("LoginUser", loginUser);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		jsonObject.put("loginUserName", loginUser.getUsername());
		jsonObject.put("type", loginUser.getType());
		writeJson(jsonObject.toString());
	}
	
	/**
	 * 退出登入
	 */
	public void logout(){
		getSession().removeAttribute("LoginUser");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		writeJson(jsonObject.toString());
	}
	
	public void findDengluByType(){
		if(getSession().getAttribute("LoginUser") == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "用户未登入");
			writeJson(jsonObject.toString());
			return;
		}
		int type = getParameterInt("type");
		if(type == 0){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "缺少必须参数：type");
			writeJson(jsonObject.toString());
			return;
		}
		
		List<TDenglu> list =TDengluDao.findDengluByType(type);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		jsonObject.put("list", list);
		writeJson(jsonObject.toString());
		
	}
	
	public void add(){
		TDenglu po = new TDenglu();
		po.setName(getParameter("name"));
		po.setUsername(getParameter("username"));
		po.setPassword(getParameter("password"));
		po.setType(1);

		if(!TDengluDao.add(po)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "数据库保存失败");
			writeJson(jsonObject.toString());
			return;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		
		writeJson(jsonObject.toString());
		
	}
}

