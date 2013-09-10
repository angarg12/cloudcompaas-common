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
