package edu.hzcc.webdemo.util;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 每个controller的基础类
 * 
 * @author icekinglele
 *
 */
public abstract class ControllerBase {
	/**
	 * servlet中根据这个参数来跳转到相应的controller去
	 */
	public static String Controller_Param_Name = "cls";
	public static String Controller_Action_Method = "mtd";

	/**
	 * controller所在的根目录
	 */
	public static String Controller_Param_Package = "";

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	/**
	 * 每个controller都需要实现的执行方法
	 */
	public void action(){
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	
	public HttpSession getSession(){
		return getRequest().getSession();
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * 直接返回一个html的文本，或json对象
	 * 
	 * @param html
	 */
	protected void writeHtml(String html) {
		try {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().println(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void writeJson(String json) {
		try {
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 封装参数获得
	 * 
	 * @param paramName
	 * @return
	 */
	protected String getParameter(String paramName) {
		String value = request.getParameter(paramName);
		return value != null ? value.trim() : null;
	}

	/**
	 * 判断一个参数是否为空，=null或者=空
	 * 
	 * @param paramName
	 * @return
	 */
	protected boolean isParameterEmpty(String paramName) {
		String value = getParameter(paramName);
		return value == null || value.equals("");
	}

	/**
	 * 是否有这个参数
	 * 
	 * @param paramName
	 * @return
	 */
	protected boolean hasParameter(String paramName) {
		return getParameter(paramName) != null;
	}

	/**
	 * 返回整数型参数，默认0
	 * 
	 * @param paramName
	 * @return
	 */
	protected int getParameterInt(String paramName) {
		String value = getParameter(paramName);
		if (value == null || value.equals(""))
			return 0;
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}

	protected long getParameterLong(String paramName) {
		String value = getParameter(paramName);
		if (value == null || value.equals(""))
			return 0l;
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			return 0l;
		}
	}

	protected double getParameterDouble(String paramName) {
		String value = getParameter(paramName);
		if (value == null || value.equals(""))
			return 0d;
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			return 0d;
		}
	}

	protected boolean getParameterBoolean(String paramName) {
		String value = getParameter(paramName);
		if (value == null || value.equals(""))
			return false;
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 跳转到另外一张jsp页面去
	 * 
	 * @param pagePath
	 */
	protected void toPage(String pagePath) {
		try {
			request.getRequestDispatcher("/" + pagePath).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将request中的参数设置到bean中去
	 * 
	 * @param bean
	 * @param excludeArray,
	 *            例外不需要拷贝的属性
	 * @return
	 */
	protected boolean copyToBean(Object bean, String[] excludeArray) {
		try {
			Field[] fields = bean.getClass().getDeclaredFields();
			if (fields == null || fields.length == 0)
				return false;
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName();
				if (excludeArray != null && isStrInArray(name, excludeArray)) {
					continue;
				}
				// 判断request中是否有一样的
				String value = getParameter(name);
				if (value == null)
					continue;
				// 存在，则进行拷贝
				BeanUtils.copyProperty(bean, name, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断字符串是否在数组中
	 * 
	 * @param str
	 * @param array
	 * @return
	 */
	protected boolean isStrInArray(String str, String[] array) {
		if (array == null || array.length == 0)
			return false;
		for (String tmp : array) {
			if (str.equals(tmp))
				return true;
		}
		return false;
	}
}
