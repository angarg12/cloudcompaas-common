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
