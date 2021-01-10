/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package com.newlife.charge.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 关于异常的工具类.
 * @author calvin
 * @version 2013-01-15
 */
public class Exceptions {

	private static final Logger logger = LoggerFactory.getLogger(Exceptions.class);

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable e) {
		String exceptionStack = "";
		if (e == null){
			return exceptionStack;
		}

		if (e instanceof Exception) {
			Exception eObj = (Exception) e;
			StringWriter sw = null;
			PrintWriter pw = null;
			try {
				sw = new StringWriter();
				pw = new PrintWriter(sw);
				eObj.printStackTrace(pw);
				exceptionStack = sw.toString();
				return  exceptionStack;
			} catch (Exception e1) {
				logger.error("IOException",e1);
			} finally {
				try {
					if(pw!=null){
						pw.close();
					}
					if(sw!=null){
						sw.close();
					}
				} catch (IOException e2) {
					logger.error("IOException",e2);
				}
			}
		} else {
			exceptionStack = e.toString();
		}
		return  exceptionStack;
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}

	/**
	 * 在request中获取异常类
	 * @param request
	 * @return 
	 */
	public static Throwable getThrowable(HttpServletRequest request){
		Throwable ex = null;
		if (request.getAttribute("exception") != null) {
			ex = (Throwable) request.getAttribute("exception");
		} else if (request.getAttribute("javax.servlet.error.exception") != null) {
			ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
		}
		return ex;
	}
	
}
