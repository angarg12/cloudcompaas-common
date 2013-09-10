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

import org.apache.xmlbeans.XmlObject;
import org.w3c.dom.Node;

/**
 * @author angarg12
 *
 */
public class XMLWrapper {
	XmlObject object;
	
	public XMLWrapper(String xml) throws Exception {
		object = XmlObject.Factory.parse(xml);
	}
	
	public XMLWrapper(XmlObject xml) throws Exception {
		object = xml;
	}
	
	// get all the tags that matches the xpath
	public String[] get(String xpath) {
    	XmlObject[] elementsXml = object.selectPath(xpath);
    	String[] elements = new String[elementsXml.length];
    	for(int i = 0; i < elementsXml.length; i++){
    		if(elementsXml[i].getDomNode().getFirstChild().getNodeType() == Node.TEXT_NODE){
    			elements[i] = elementsXml[i].getDomNode().getFirstChild().getNodeValue();
    		}else{
    			elements[i] = elementsXml[i].xmlText();
    		}
    	}
    	return elements;
	}	
	
	// get the first tag that matches the xpath. Is like assuming that it will always just return
	// one element
	public String getFirst(String xpath) {
    	XmlObject[] elementsXml = object.selectPath(xpath);
    	if(elementsXml.length == 0){
    		return null;
    	}
    	if(elementsXml[0].getDomNode().getFirstChild().getNodeType() == Node.TEXT_NODE){
    		return elementsXml[0].getDomNode().getFirstChild().getNodeValue();
		}else{
			return elementsXml[0].xmlText();
		}
	}		
	
	// get all the tags that matches the xpath
	public XmlObject[] getNodes(String xpath) {
		return object.execQuery(xpath);
	}
	
	// get all the tags that matches the xpath
	public XmlObject getFirstNode(String xpath) {
    	XmlObject[] elementsXml = object.execQuery(xpath);
    	if(elementsXml.length > 0){
    		return elementsXml[0];
    	}
    	return null;
	}		
}
