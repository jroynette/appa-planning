package org.appa.planning.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Classe utilitaire permettant la gestion de différents Aspect de type AOP
 * 
 * @author jroynett
 *
 */
public class AOPManager {

	/**
	 * loggue les exceptions
	 */
	public Object logException(ProceedingJoinPoint call) throws Throwable {

		try {
			return call.proceed();
		}catch (Throwable e) {

			Class classType = call.getSignature().getDeclaringType();
			String method = call.getSignature().toShortString();
			Logger logger = Logger.getLogger(classType);

			//log execution context
			StringBuffer error = new StringBuffer();
			error.append("error during call " +  method + " with args : "+ argsToString(call));
			error.append("\n");

			//log stack trace
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			error.append(sw.toString());

			//log full error message
			logger.error(error.toString());

			throw e;
		}
	}

	/**
	 * trace le contexte d'execution des méthodes (utilisé en développement)
	 */
	public Object traceExecution(ProceedingJoinPoint call) throws Throwable {

		Class classType = call.getSignature().getDeclaringType();
		String method = call.getSignature().toShortString();
		Logger logger = Logger.getLogger(classType);

		if(logger.isDebugEnabled()){
			logger.debug("call " +  method + " with args : "+ argsToString(call));
		}
		Object result =  call.proceed();
		if(logger.isDebugEnabled()){
			logger.debug("end " + method + " return : "+ result);
		}
		return result;
	}

	/**
	 * profile le temps d'execution des méthodes (utilisé en développement)
	 */
	public Object profileExecution(ProceedingJoinPoint call) throws Throwable {

		Class classType = call.getSignature().getDeclaringType();
		String method = call.getSignature().toShortString();
		Logger logger = Logger.getLogger(classType);
		Date now = new Date();

		try {
			return call.proceed();
		} finally {
			if(logger.isDebugEnabled()){
				logger.debug("execution time for " + method + " : " + (new Date().getTime() - now.getTime()) + "ms");
			}
		}
	}

	private String argsToString(ProceedingJoinPoint call){
		String args = "";
		for (Object arg : call.getArgs()) {
			args += arg +  " ";
		}
		return args;
	}
}