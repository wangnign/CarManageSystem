package edu.hzcc.webdemo.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * 对象和json字符之间的转换
 * 需要用到 net.sf.json 包
 * @author icekinglele
 *
 */
public abstract class ObjectToJson implements ObjTFStr{
	/**
	 * json字符的格式化
	 */
	public static boolean JSON_FORMATTED = false;
	
	/**
	 * json对象转换的时候，如果内部有list等集合对象，
	 * 需要指定集合对象的类型
	 * 解析的时候使用
	 */
	private Map<String, Class<?>> jsonClassMap;
	
	/**
	 * 情况列表
	 * 解析之前需要
	 */
	public void clearJsonClassMap(){
		if(jsonClassMap == null){
			return;
		}
		jsonClassMap.clear();
	}
	
	/**
	 * 增加一个解析类的内容
	 * @param collectionFieldName
	 * @param collectionItemClass
	 */
	public void addJsonCollectionClass(String collectionFieldName,Class<?> collectionItemClass){
		if(jsonClassMap == null){
			jsonClassMap = new HashMap<String, Class<?>>();
		}
		jsonClassMap.put(collectionFieldName, collectionItemClass);
	}
	
	@Override
	public String to() {
		JSONObject jsonObject = JSONObject.fromObject(this);
		String jsonString = jsonObject.toString();
		if(jsonString == null || jsonString.equals(""))
			return null;
		if(JSON_FORMATTED)
			jsonString = GlobalUtils.formatJson(jsonString);
		return jsonString;
	}
	
	/**
	 * 是否需要解析这个json中的属性
	 * 一般要求子类进行扩展的
	 * @param propName
	 * @return
	 */
	protected boolean parseTheProperty(String propName){
		return true;
	}
	
	@Override
	public boolean from(String str) {
		try {
			//json转换时候的配置对象
			JsonConfig config = new JsonConfig();
			config.setJsonPropertyFilter(new PropertyFilter() {
				
				@Override
				public boolean apply(Object source, String name, Object value) {
					//false表示需要解析de
					return !parseTheProperty(name);
				}
			});
			
			JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(str,config);
			Object obj = null;
			if(jsonClassMap != null && !jsonClassMap.isEmpty()){
				obj = JSONObject.toBean(jsonObject, this.getClass(),jsonClassMap);
				BeanUtils.copyProperties(this, obj);
			}else{
				obj = JSONObject.toBean(jsonObject, this.getClass());
				BeanUtils.copyProperties(this, obj);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
