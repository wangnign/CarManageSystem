package edu.hzcc.webdemo.controller;
import java.util.List;
import edu.hzcc.webdemo.dao.TPersonDao;
import edu.hzcc.webdemo.tables.TPerson;
import edu.hzcc.webdemo.util.ControllerBase;
import net.sf.json.JSONObject;

public class PersonController extends ControllerBase {
	public void allPersonList(){
		if(getSession().getAttribute("LoginUser") == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "用户未登入");
			writeJson(jsonObject.toString());
			return;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		List<TPerson> list = TPersonDao.allPersonList();
		jsonObject.put("list", list);
		writeJson(jsonObject.toString());
	}
	
	public void addModify(){
		TPerson cou = new TPerson();
		cou.setId(getParameterLong("id"));
		cou.setIdcard(getParameter("idcard"));
		cou.setName(getParameter("name"));
		cou.setPassword(getParameter("password"));
		cou.setSex(getParameter("sex"));
		cou.setXianluhao(getParameter("xianluhao"));
		cou.setChufashijian(getParameter("chufashijian"));
		cou.setChehao(getParameter("chehao"));
		
		
		//System.out.println(cou.toString());
		
		
		TPerson oldOne = TPersonDao.findByidcard(cou.getIdcard(),cou.getId());
		if(oldOne != null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "身份证号已经存在："+cou.getIdcard()+", "+cou.getName());
			writeJson(jsonObject.toString());
			return;
		}
		
		if(!TPersonDao.save(cou)){
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
	public void addModify1(){
		TPerson cou = new TPerson();
		cou.setId(getParameterLong("id"));
		cou.setIdcard(getParameter("idcard"));
		cou.setName(getParameter("name"));
		cou.setPassword(getParameter("password"));
		cou.setSex(getParameter("sex"));
		cou.setXianluhao(getParameter("xianluhao"));
		cou.setChufashijian(getParameter("chufashijian"));
		cou.setChehao(getParameter("chehao"));
		
		
		//System.out.println(cou.toString());
		
		
		TPerson oldOne = TPersonDao.findByidcard(cou.getIdcard(),cou.getId());
		if(oldOne != null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "身份证号已经存在："+cou.getIdcard()+", "+cou.getName());
			writeJson(jsonObject.toString());
			return;
		}
		
		if(!TPersonDao.save1(cou)){
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
		
		TPerson cou = TPersonDao.findById(id);
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
		
		TPerson cou = TPersonDao.findById(id);
		if(cou == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "数据库查询失败");
			writeJson(jsonObject.toString());
			return;
		}
		
		if(TPersonDao.deleteById(id))
			r=true;
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", r);
		jsonObject.put("cou", cou);
		writeJson(jsonObject.toString());
		return;
	}


}
