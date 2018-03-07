package edu.hzcc.webdemo.util;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 对象和base64字符的相互转换
 * 
 * @author icekinglele
 *
 */
public abstract class ObjectToBase64 implements Serializable,ObjTFStr{
	/**
	 * 
	 */
	private static final long serialVersionUID = 948797844910760512L;
	
	
	@Override
	public String to() {
		return GlobalUtils.objectToBase64(this);
	}
	
	@Override
	public boolean from(String str) {
		try {
			Serializable obj = GlobalUtils.base64ToObject(str);
			BeanUtils.copyProperties(this, obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
