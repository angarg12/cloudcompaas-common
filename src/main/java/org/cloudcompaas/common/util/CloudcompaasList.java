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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlEngine;

/**
 * @author angarg12
 *
 */
public class CloudcompaasList {

	public double avg(Object items){
		if(items == null){
			return 0;
		}
		if(items.getClass().isArray()){
			double avg = 0;
	
			for(int i = 0; i < ((Object[])items).length; i++){
				avg += Double.valueOf(((Object[])items)[i].toString());
			}
			avg = avg/((Object[])items).length;
			return avg;
		}else{
			return Double.parseDouble(items.toString());
		}
	}
	
	public double min(Object items){
		if(items == null){
			return 0;
		}
		if(items.getClass().isArray()){
			if(((Object[])items).length == 1){
				return Double.valueOf(((Object[])items)[0].toString());
			}
			double min = Double.valueOf(((Object[])items)[0].toString());
			for(int i = 1; i < ((Object[])items).length; i++){
				if(Double.valueOf(((Object[])items)[i].toString()) < min){
					min = Double.valueOf(((Object[])items)[i].toString());
				}
			}
			return min;
		}else{
			return Double.parseDouble(items.toString());
		}
	}
	
	public double max(Object items){
		if(items == null){
			return 0;
		}
		if(items.getClass().isArray()){
			if(((Object[])items).length == 1){
				return Double.valueOf(((Object[])items)[0].toString());
			}
			double max = Double.valueOf(((Object[])items)[0].toString());
			for(int i = 1; i < ((Object[])items).length; i++){
				if(Double.valueOf(((Object[])items)[i].toString()) > max){
					max = Double.valueOf(((Object[])items)[i].toString());
				}
			}
			return max;
		}else{
			return Double.parseDouble(items.toString());
		}
	}
	
	public double sum(Object items){
		if(items == null){
			return 0;
		}
		if(items.getClass().isArray()){
			double sum = 0;
			for(int i = 0; i < ((Object[])items).length; i++){
				sum += Double.valueOf(((Object[])items)[i].toString());
			}
			return sum;
		}else{
			return Double.parseDouble(items.toString());
		}
	}	
	
	/**
	 * Applies a operation, described by expr, to a set of array variables. E.g. ARRAY*2 multiplies all
	 * the elements in the array variable ARRAY by two.
	 * NOTE: As the implementation is now, it does not make any kind of check about arrays lengths. If arrays
	 * length happen to be different, it will throw an exception (since the operation is invalid).
	 * 
	 * Syntax for jexl: vectorop("expression",context)
	 * 
	 * expression is the expression to evaluate, as a string.
	 * context is a reserved word that references the expression context.
	 * 
	 * @param expr
	 * @param jc
	 * @return
	 * @throws Exception
	 */
	public Object[] map(String expr, CCPaaSJexlContext jc) throws Exception {
	try{
		CCPaaSJexlContext clonedcontext = new CCPaaSJexlContext();
		Map<String, Object> vars = new HashMap<String, Object>(); 

		vars.putAll(jc);
		Set<String> keys = vars.keySet();
		Iterator<String> it = keys.iterator();
		Vector<String> keyvars = new Vector<String>();
		while(it.hasNext()){
			String key = it.next();
			// put in keyvars all the array variables in the context
			if(vars.get(key).getClass().isArray() && expr.contains(key)){
				keyvars.add(key);
			}
		}

		JexlEngine jexlengine = new JexlEngine();
		// if there are none, just evaluate the expression
		if(keyvars.size() < 1){
	        Expression expression = jexlengine.createExpression( expr );
			return new Object[]{expression.evaluate(jc).toString()};
		}
		
		// extract the length of the first array variable. We assume that every array
		// variable has the same length; otherwise the application of vectorop is not possible
		Object[] result = new Object[((Object[])vars.get(keyvars.get(0))).length];
		// iterate for each value in the array variables
		for(int i = 0; i < result.length; i++){
			// for each array variable
			for(int j = 0; j < keyvars.size();j++){
				// substitute in the context the array variable for its value in the possition i
				vars.put(keyvars.get(j), ((Object[])jc.get(keyvars.get(j)))[i]);
			}
			// evaluate the expression for that substitution, and store the value

	        Expression expression = jexlengine.createExpression( expr );
	        clonedcontext.clear();
	        clonedcontext.putAll(vars);
			result[i] = expression.evaluate(clonedcontext).toString();
		}
		for(int i = 0; i < result.length; i++){
			System.out.println(result[i]);
		}
		return result;
	}catch(Exception e){e.printStackTrace();}
	return null;
	}	
}
