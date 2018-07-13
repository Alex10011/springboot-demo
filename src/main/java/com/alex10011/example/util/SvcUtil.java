package com.alex10011.example.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

//业务辅助工具
public class SvcUtil {
	private static Logger log = Logger.getLogger(SvcUtil.class);

	/**
	 * 左或右填充成指定长度的字符串
	 * 
	 * @param value
	 * @param bits
	 *            位数
	 * @param seed
	 *            填充种子
	 * @param direct
	 *            true左填充 false右填充
	 * @return
	 */
	public static String fillStringByseed(String value, int bits, char seed, boolean direct) {
		if (value == null || value.length() >= bits) {
			return value;
		}

		char[] rs = new char[bits];
		int temp = bits - value.length();

		if (direct) {// true左填充
			System.arraycopy(value.toCharArray(), 0, rs, temp, value.length());
			for (int i = 0; i < temp; i++) {
				rs[i] = seed;
			}
		} else {// false右填充
			System.arraycopy(value.toCharArray(), 0, rs, 0, value.length());
			for (int i = value.length(); i < bits; i++) {
				rs[i] = seed;
			}
		}

		return new String(rs);
	}

	/**
	 * 执行某类的静态方法
	 * 
	 * @param className
	 *            类名
	 * @param methodName
	 *            方法名
	 * @param args
	 *            参数数组
	 * @return 执行方法返回的结果
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object invokeStaticMethod(Class ownerClass, String methodName, Object[] args) throws Exception {
		Class[] argsClass = new Class[0];
		if (args != null && args.length > 0) {
			argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}
		}
		return ownerClass.getMethod(methodName, argsClass).invoke(null, args);
	}

	/**
	 * 修改类的静态属性值
	 * 
	 * @param className
	 *            类名
	 * @param methodName
	 *            方法名
	 * @param args
	 *            参数数组
	 * @return 执行方法返回的结果
	 * @throws Exception
	 */
	public static void setStaticValue(Class ownerClass, String name, Object value) throws Exception {
		Field[] fields = ownerClass.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers())) {
				if (field.getName().equals(name)) {
					field.set(null, value);
					break;
				}
			}
		}
	}

	/**
	 * 获取客户端IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					log.error(e.getMessage(), e);
				}
				ip = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	public static Map<String, Object> objectToMap(Object obj) {
		Map<String, Object> map = new HashMap<>();
		try {
			Class<?> clazz = obj.getClass();
			System.out.println(clazz);
			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				String fieldName = field.getName();
				Object value = field.get(obj);
				if (value != null) {
					map.put(fieldName, value);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return map;
	}

	// 判断访问者是不是微信浏览器
	public static boolean checkWechat(HttpServletRequest request) {
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
			return true;
		}

		return false;
	}

}
