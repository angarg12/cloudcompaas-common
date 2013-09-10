package org.cloudcompaas.common.util;

import org.w3c.dom.Node;

/**
 * @author angarg12
 *
 */
public class XMLDOMComparator {

	public boolean compare(Node first, Node second){
		if(first.getNodeName().equals("#text") && second.getNodeName().equals("#text") == false){
			return false;
		}

		if(first.getNodeName().equals("#text") == false && second.getNodeName().equals("#text")){
			return false;
		}

		if(first.getNodeName().equals("#text") && second.getNodeName().equals("#text")){
			return first.getNodeValue().equals(second.getNodeValue());
		}

		if(first.getAttributes().getLength() != second.getAttributes().getLength()){
			return false;
		}

		for(int i = 0; i < first.getAttributes().getLength(); i++){
			boolean isCorrect = false;
    		// we need an inner loop to make the function correct independently of the
    		// order.
    		for(int j = 0; j < second.getAttributes().getLength(); j++){
    			if(first.getAttributes().item(i).getNodeName().equals(second.getAttributes().item(j).getNodeName()) &&
    				first.getAttributes().item(i).getNodeValue().equals(second.getAttributes().item(j).getNodeValue())){
    				isCorrect = true;
    				break;
    			}
    		}
    		if(isCorrect == false){
    			return false;
    		}
		}

		if(first.getChildNodes().getLength() != second.getChildNodes().getLength()){
			return false;
		}

		for(int i = 0; i < first.getChildNodes().getLength(); i++){
			boolean isCorrect = false;
    		// we need an inner loop to make the function correct independtly of the
    		// order.
    		for(int j = 0; j < second.getChildNodes().getLength(); j++){
    			if(compare(first.getChildNodes().item(i), second.getChildNodes().item(j))){
    				isCorrect = true;
    				break;
    			}
    		}
    		if(isCorrect == false){
    			return false;
    		}
		}

		return true;
	}
}
