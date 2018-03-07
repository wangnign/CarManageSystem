package edu.hzcc.webdemo.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author vboxwin7
 * 
 * 在web.xml中的配置
 * 
 * <!-- controller servlet define -->
  <servlet>
  	<servlet-name>ControllerServlet</servlet-name>
  	<servlet-class>edu.hzcc.webdemo.util.edu.hzcc.webdemo.util</servlet-class>
  	<init-param>
  		<param-name>controllerPackage</param-name>
  		<param-value>edu.hzcc.webdemo.controller</param-value>
  	</init-param>
  </servlet>
  <servlet-mapping>
  	<servlet-name>ControllerServlet</servlet-name>
  	<url-pattern>/ctrl</url-pattern>
  </servlet-mapping>
 * 
 * 
 *
 */
public class ControllerServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5955918457942677951L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//获取 web.xml中servlet配置下的参数值
		
		//指定类的名字
		String pnameClass = config.getInitParameter("pnameClass");
		if(!GlobalUtils.isEmpty(pnameClass))
			ControllerBase.Controller_Param_Name = pnameClass;
		
		String pnameMethod = config.getInitParameter("pnameMethod");
		if(!GlobalUtils.isEmpty(pnameMethod))
			ControllerBase.Controller_Action_Method = pnameMethod;
		
		String controllerPackage = config.getInitParameter("controllerPackage");
		if(!GlobalUtils.isEmpty(controllerPackage))
			ControllerBase.Controller_Param_Package = controllerPackage;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		action(req, resp);
	}
	
	/**
	 * Controller做成单利模式
	 */
	private Map<String, ControllerBase> ctrlMap;
	
	private void action(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			String value = req.getParameter(ControllerBase.Controller_Param_Name);
			if(value == null || value.trim().equals("")){
				return;
			}
//			req.setCharacterEncoding("UTF-8");
			//根据不同的值，这里的值就是controller的java类的名字进行跳转控制
			if(ctrlMap == null)
				ctrlMap = new HashMap<String, ControllerBase>();
			try {
				ControllerBase base = ctrlMap.get(value);
				if(base == null){
					if(ControllerBase.Controller_Param_Package!=null && !ControllerBase.Controller_Param_Package.equals(""))
						base = (ControllerBase)Class.forName(ControllerBase.Controller_Param_Package+"."+value).newInstance();
					else
						base = (ControllerBase)Class.forName(value).newInstance();
					ctrlMap.put(value, base);
				}
				base.setRequest(req);
				base.setResponse(resp);
				//可以调用不同的方法
				String actmethod = req.getParameter(ControllerBase.Controller_Action_Method);
				if(actmethod!=null && !actmethod.equals("")){
					try {
						Method method = base.getClass().getDeclaredMethod(actmethod);
						if(method == null){
							base.action();
						}else {
							method.invoke(base);
						}
					} catch (Exception e2) {
						base.action();
					}
				}else {
					base.action();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
