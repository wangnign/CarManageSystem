package edu.hzcc.webdemo.controller;

import java.util.List;

import edu.hzcc.webdemo.dao.TRouteDao;
import edu.hzcc.webdemo.pojo.ChufariqiPiaoshu;
import edu.hzcc.webdemo.tables.TPerson;
import edu.hzcc.webdemo.tables.TRoute;
import net.sf.json.JSONObject;
import edu.hzcc.webdemo.util.ControllerBase;

public class RouteController extends ControllerBase{
	public void allChufariqiPiaoshuList(){
		if(getSession().getAttribute("LoginUser") == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "用户未登入");
			writeJson(jsonObject.toString());
			return;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		List<ChufariqiPiaoshu> list = TRouteDao.allChufariqiPiaoshuList();
		jsonObject.put("list", list);
		
		writeJson(jsonObject.toString());
	}
	
	public void findRouteByChufariqi(){
		if(getSession().getAttribute("LoginUser") == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "用户未登入");
			writeJson(jsonObject.toString());
			return;
		}
		String chufariqi = getParameter("chufariqi");
		
		System.out.println(chufariqi);
		
		if(chufariqi == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "缺少必须参数：chufariqi");
			writeJson(jsonObject.toString());
			return;
		}
		
		List<TRoute> list =TRouteDao.findRouteByChufariqi(chufariqi);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		jsonObject.put("list", list);
		writeJson(jsonObject.toString());
	}
		
		public void addModify(){
			TRoute cou = new TRoute();
			cou.setId(getParameterLong("id"));
			cou.setXianluhao(getParameter("xianluhao"));
			cou.setChufadi(getParameter("chufadi"));
			cou.setMudidi(getParameter("mudidi"));
			cou.setChufariqi(getParameter("chufariqi"));
			cou.setChufashijian(getParameter("chufashijian"));
			cou.setSuoxushijian(getParameter("suoxushijian"));
			cou.setJiage(getParameter("jiage"));
			cou.setShengyupiaoshu(getParameter("shengyupiaoshu"));
			cou.setZongpiaoshu(getParameter("zongpiaoshu"));
			
			
			//System.out.println(cou.toString());
			
			
			TRoute oldOne = TRouteDao.findByxianluhao(cou.getXianluhao(),cou.getId());
			if(oldOne != null){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("succ", false);
				jsonObject.put("stmt", "线路号已经存在："+cou.getXianluhao()+", "+cou.getChufadi());
				writeJson(jsonObject.toString());
				return;
			}
			
			if(!TRouteDao.save(cou)){
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
			System.out.println(" preModify init  id ===" +id );
			if(id<=0){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("succ", false);
				jsonObject.put("stmt", "缺少必要参数：id");
				writeJson(jsonObject.toString());
				return;
			}
			TRoute cou = TRouteDao.findById(id);
			
			if(cou == null){
				System.out.println("数据库查询失败");
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
			
			TRoute cou = TRouteDao.findById(id);
			if(cou == null){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("succ", false);
				jsonObject.put("stmt", "数据库查询失败");
				writeJson(jsonObject.toString());
				return;
			}
			
			System.out.println(cou.toString());
			if(TRouteDao.deleteById(id))
				r=true;
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", r);
			jsonObject.put("cou", cou);
			writeJson(jsonObject.toString());
			return;
	
}
//		//购买
//		public void buy(){
//			long id = getParameterLong("id");
//			boolean r=false;
//			if(id<=0){
//				JSONObject jsonObject = new JSONObject();
//				jsonObject.put("succ", false);
//				jsonObject.put("stmt", "缺少必要参数：id");
//				writeJson(jsonObject.toString());
//				return;
//			}
//			
//			TRoute cou = TRouteDao.findById(id);
//			if(cou == null){
//				JSONObject jsonObject = new JSONObject();
//				jsonObject.put("succ", false);
//				jsonObject.put("stmt", "数据库查询失败");
//				writeJson(jsonObject.toString());
//				return;
//			}
//			
//			System.out.println(cou.toString());
//			if(TRouteDao.buyById(id))
//				r=true;
//			
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("succ", r);
//			jsonObject.put("cou", cou);
//			writeJson(jsonObject.toString());
//			return;
//	
//}
		//买方案2
	/*	public void buyModify(){
			TRoute cou = new TRoute();
	
			
			int flag = TRouteDao.buyByid(cou);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("flag", flag);
			writeJson(jsonObject.toString());
			
			return;
			
		}*/
		}

