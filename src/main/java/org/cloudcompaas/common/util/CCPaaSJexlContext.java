package org.cloudcompaas.common.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl2.JexlContext;

/**
 * Based on JEXLWSAG4JContext.
 * 
 * @author angarg12
 * 
 */
public class CCPaaSJexlContext extends HashMap<String, Object> implements
		JexlContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7261867830303172948L;

	public void setVars(Map<String, Object> map) {
		clear();
		putAll(map);
	}

	public Object get(String key) {
		Object result = super.get(key);

		if (result == null) {
			throw new IllegalStateException(MessageFormat.format(
					"Unable to resolve value for variable {0}",
					new Object[] { key }));
		}

		return result;
	}

	public boolean has(String key) {
		return containsKey(key);
	}

	public void set(String name, Object value) {
		super.put(name, value);
	}

}