/*******************************************************************************
 * Copyright (c) 2013, Andrés García García All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * (1) Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 
 * (2) Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * (3) Neither the name of the Universitat Politècnica de València nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
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
