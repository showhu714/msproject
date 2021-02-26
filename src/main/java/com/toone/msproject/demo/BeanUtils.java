package com.toone.msproject.demo;
import java.lang.reflect.InvocationTargetException;
public class BeanUtils  extends org.apache.commons.beanutils.BeanUtils {
	
	/**
	 * 复制实体属性
	 * @param dest
	 * @param orig
	 */
	public static void copyProperties(final Object dest, final Object orig) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 设置属性
	 * @param bean
	 * @param name
	 * @param value
	 */
	public static void setProperty(final Object bean, final String name, final Object value) {
		try {
			org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
