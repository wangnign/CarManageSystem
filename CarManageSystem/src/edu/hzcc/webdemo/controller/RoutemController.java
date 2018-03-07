package edu.hzcc.webdemo.controller;

import java.util.List;

import edu.hzcc.webdemo.dao.TRoutemDao;
import edu.hzcc.webdemo.tables.TRoutem;
import edu.hzcc.webdemo.util.ControllerBase;
import net.sf.json.JSONObject;

public class RoutemController extends ControllerBase {
	public void allRoutemList(){
		if(getSession().getAttribute("LoginUser") == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "用户未登入");
			writeJson(jsonObject.toString());
			return;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		List<TRoutem> list = TRoutemDao.allRoutemList();
		jsonObject.put("list", list);
		writeJson(jsonObject.toString());
	}
	
	public void addModify(){
		TRoutem cou = new TRoutem();
		cou.setId(getParameterLong("id"));
		cou.setXianluhao(getParameter("xianluhao"));
		cou.setChufadi(getParameter("chufadi"));
		cou.setMudidi(getParameter("mudidi"));
		cou.setJiage(getParameter("jiage"));
		cou.setZongpiaoshu(getParameter("zongpiaoshu"));
		cou.setSuoxushijian(getParameter("suoxushijian"));
		
		
		
		
		//System.out.println(cou.toString());
		
		
		TRoutem oldOne = TRoutemDao.findByxianluhao(cou.getXianluhao(),cou.getId());
		if(oldOne != null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "线路号已经存在："+cou.getXianluhao()+", "+cou.getChufadi());
			writeJson(jsonObject.toString());
			return;
		}
		
		if(!TRoutemDao.save(cou)){
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
	
	public void preModify(){
		long id = getParameterLong("id");
//		System.out.println(id);
		if(id<=0){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "缺少必要参数：id");
			writeJson(jsonObject.toString());
			return;
		}
		
		TRoutem cou = TRoutemDao.findById(id);
		if(cou == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "数据库查询失败");
			writeJson(jsonObject.toString());
			return;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		jsonObject.put("cou", cou);
		writeJson(jsonObject.toString());
		return;
	}
	
	public void delete(){
		long id = getParameterLong("id");
		boolean r=false;
		if(id<=0){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "缺少必要参数：id");
			writeJson(jsonObject.toString());
			return;
		}
		
		TRoutem cou = TRoutemDao.findById(id);
		if(cou == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "数据库查询失败");
			writeJson(jsonObject.toString());
			return;
		}
		
		if(TRoutemDao.deleteById(id))
			r=true;
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", r);
		jsonObject.put("cou", cou);
		writeJson(jsonObject.toString());
		return;
	}

}
