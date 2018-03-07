package edu.hzcc.webdemo.controller;

import java.util.List;

import edu.hzcc.webdemo.dao.TBusDao;
import edu.hzcc.webdemo.pojo.CheXing;
import edu.hzcc.webdemo.tables.TBus;
import edu.hzcc.webdemo.util.ControllerBase;
import net.sf.json.JSONObject;

public class BusController extends ControllerBase{
	public void allCheXingList(){
		if(getSession().getAttribute("LoginUser") == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "用户未登入");
			writeJson(jsonObject.toString());
			return;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		List<CheXing> list = TBusDao.allCheXingList();
		for (CheXing cheXing : list) {
			System.out.println(cheXing.toString());
		}
		jsonObject.put("list", list);
		
		writeJson(jsonObject.toString());
	}
	
	//这里需要到dao包里面去查询 
	public void findBusByChexing(){
		if(getSession().getAttribute("LoginUser") == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "用户未登入");
			writeJson(jsonObject.toString());
			return;
		}
		
		String chexing = getParameter("chexing");
		String goumainian = getParameter("goumainian");//加的参数
		if(chexing == null || chexing.equals("")){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "缺少必须参数：chexing");
			writeJson(jsonObject.toString());
			return;
		}
		//这里需要判断，跟chexing一样
		if(goumainian == null || "".equals(goumainian)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "缺少必须参数：goumainian");
			writeJson(jsonObject.toString());
			return;
		}
		//这里就是dao包里面的方法  加了一个参数goumainian
		List<TBus> list = TBusDao.findBusByChexing(chexing,goumainian);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		jsonObject.put("list", list);
		writeJson(jsonObject.toString());
		
	}
	
	public void addModify(){
		TBus stu = new TBus();
		stu.setId(getParameterLong("id"));
		stu.setGoumainian(getParameterInt("goumainian"));
		stu.setChehao(getParameter("chehao"));
		stu.setChexing(getParameter("chexing"));
		stu.setZuidazaikeliang(getParameter("zuidazaikeliang"));
		
		
		TBus oldOne = TBusDao.findByChehao(stu.getChehao(),stu.getId());
		if(oldOne != null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "车号已经存在："+stu.getChehao()+", "+stu.getChexing());
			writeJson(jsonObject.toString());
			return;
		}
		
		if(!TBusDao.save(stu)){
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
		long id = getParameterLong("id"); //这个是客户端传过来的id
		//排除问题 首先要打印log日志 看看代码流程
		System.out.println(" preModify init  id ===" +id );
		if(id<=0){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "缺少必要参数：id");
			writeJson(jsonObject.toString());
			return;
		}
		
		TBus bus = TBusDao.findById(id); //根据id查询一个对象 注意变量名 ，这里是bus 你不要写成stu
		if(bus == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "数据库查询失败"); 
			writeJson(jsonObject.toString());
			return;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", true);
		jsonObject.put("bus", bus);//这里就 是要 你查询的 对象bus .然后回到bus.js
		writeJson(jsonObject.toString());
		return;
	}
	
	public void delete(){
		long id = getParameterLong("id");
		//一样的 最好打印 获取到传过来的id参数
		boolean r=false;
		if(id<=0){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "缺少必要参数：id");
			writeJson(jsonObject.toString());
			return;
		}
		
		TBus tBus = TBusDao.findById(id); //这里要注意 TBUS
		if(tBus == null){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("succ", false);
			jsonObject.put("stmt", "数据库查询失败");
			writeJson(jsonObject.toString());
		}
		
		if(TBusDao.deleteById(id))
			r=true;
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("succ", r);
		jsonObject.put("bus", tBus); //还有这里
		writeJson(jsonObject.toString());
		return;
	}
}
