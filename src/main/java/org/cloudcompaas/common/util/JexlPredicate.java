package org.cloudcompaas.common.util;

import java.util.Collection;
import java.util.Vector;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlEngine;


/**
 * @author angarg12
 *
 */
public class JexlPredicate {
	private String variable;
	private Expression e;
	CCPaaSJexlContext jc;
	
	public JexlPredicate(String variable, String expression, CCPaaSJexlContext context) {
		this.variable = variable;
		try {
			JexlEngine jexlengine = new JexlEngine();
			jc = context;
			this.e = jexlengine.createExpression(expression);
		} catch (Exception e) {
			throw new RuntimeException("Error creating JEXL expression", e);
		}
	}
	
	private boolean evaluate(Object o) {
		jc.put(variable, o);
		Boolean b;
		try {
			b = (Boolean) e.evaluate(jc);
		} catch (Exception e) {
			throw new RuntimeException("Error evaluating JEXL expression", e);
		}
		return b.booleanValue();
	}
	
	public Collection<Object> filterIn(Object[] o) {
		Collection<Object> ins = new Vector<Object>();
		for(int i = 0; i < o.length; i++){
			if(evaluate(o[i])){
				ins.add(o[i]);
			}
		}
		return ins;
	}
	
	public Collection<Object> filterOut(Object[] o) {
		Collection<Object> outs = new Vector<Object>();
		for(int i = 0; i < o.length; i++){
			if(evaluate(o[i]) == false){
				outs.add(o[i]);
			}
		}
		return outs;
	}
}