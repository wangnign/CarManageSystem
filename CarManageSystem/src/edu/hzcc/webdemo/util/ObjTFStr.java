package edu.hzcc.webdemo.util;

/**
 *  对象和字符串的相互转换的接口
 * @author icekinglele
 *
 */
public interface ObjTFStr {
	/**
	 * 从对象到字符，
	 * 返回null表示失败
	 * @return
	 */
	public String to();
	
	/**
	 * 从字符到对象
	 * @param str
	 * @return
	 */
	public boolean from(String str);
}
