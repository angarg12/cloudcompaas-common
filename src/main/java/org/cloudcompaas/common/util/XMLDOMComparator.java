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
