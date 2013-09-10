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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author angarg12
 *
 */
public class Evaluator {
	
	public boolean Evaluate(Node compositor, Object element, Comparator comparator, ComparisonOperation operation){
		if(compositor.getNodeName().equals("ccpaas:All")){
			return EvaluateAll(compositor, element, comparator, operation);
		}
		if(compositor.getNodeName().equals("ccpaas:OneOrMore")){
			return EvaluateOneOrMore(compositor, element, comparator, operation);
		}
		if(compositor.getNodeName().equals("ccpaas:ExactlyOne")){
			return EvaluateExactlyOne(compositor, element, comparator, operation);
		}
		if(compositor.getNodeName().equals("ccpaas:Not")){
			return EvaluateNot(compositor, element, comparator, operation);
		}
		if(compositor.getNodeName().equals("ccpaas:Element")){
			return EvaluateElement(compositor, element, comparator, operation);
		}
		return false;
	}
	
	public boolean EvaluateAll(Node compositor, Object element, Comparator comparator, ComparisonOperation operation){
		NodeList nl = compositor.getChildNodes();
		boolean result = true;
		for(int i = 0; i < nl.getLength(); i++){
			result = result && Evaluate(nl.item(i), element, comparator, operation);
		}
		return result;
	}
	
	public boolean EvaluateOneOrMore(Node compositor, Object element, Comparator comparator, ComparisonOperation operation){
		NodeList nl = compositor.getChildNodes();
		boolean result = true;
		for(int i = 0; i < nl.getLength(); i++){
			result = result || Evaluate(nl.item(i), element, comparator, operation);
		}
		return result;
	}
	
	public boolean EvaluateExactlyOne(Node compositor, Object element, Comparator comparator, ComparisonOperation operation){
		NodeList nl = compositor.getChildNodes();
		int truecount = 0;
		for(int i = 0; i < nl.getLength(); i++){
			if(Evaluate(nl.item(i), element, comparator, operation)){
				truecount++;
			}
		}
		if(truecount == 1) return true;
		return false;
	}	
	
	public boolean EvaluateNot(Node compositor, Object element, Comparator comparator, ComparisonOperation operation){
		return !Evaluate(compositor.getFirstChild(), element, comparator, operation);
	}
	
	public boolean EvaluateElement(Node compositor, Object element, Comparator comparator, ComparisonOperation operation){
		return comparator.compare(element, compositor.getFirstChild().getNodeValue(), operation);
	}
}
