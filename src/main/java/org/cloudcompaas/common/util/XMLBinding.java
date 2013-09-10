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

/**
 * @author angarg12
 *
 */
public class XMLBinding {
	static public Object bind(String metric, XmlObject target) throws Exception {
		try{
			// Depending on the schema type, the xml value is binded to a different Java type, as the bindings 
			// defined by JAXB.
			if(metric.equals("xsd:string") || metric.equals("xs:string")){
				java.lang.String targetValue = org.apache.xmlbeans.XmlString.Factory.parse(target.getDomNode()).getStringValue();
				return targetValue;
			}
			if(metric.equals("xsd:integer") || metric.equals("xs:integer")){
				java.math.BigInteger targetValue = org.apache.xmlbeans.XmlInteger.Factory.parse(target.getDomNode()).getBigIntegerValue();
				return targetValue;
			}
			if(metric.equals("xsd:int") || metric.equals("xs:int")){
				int targetValue = org.apache.xmlbeans.XmlInt.Factory.parse(target.getDomNode()).getIntValue();
				return targetValue;
			}	
			if(metric.equals("xsd:long") || metric.equals("xs:long")){
				long targetValue = org.apache.xmlbeans.XmlLong.Factory.parse(target.getDomNode()).getLongValue();
				return targetValue;
			}	
			if(metric.equals("xsd:short") || metric.equals("xs:short")){
				short targetValue = org.apache.xmlbeans.XmlShort.Factory.parse(target.getDomNode()).getShortValue();
				return targetValue;
			}	
			if(metric.equals("xsd:decimal") || metric.equals("xs:decimal")){
				java.math.BigDecimal targetValue = org.apache.xmlbeans.XmlDecimal.Factory.parse(target.getDomNode()).getBigDecimalValue();
				return targetValue;
			}	
			if(metric.equals("xsd:float") || metric.equals("xs:float")){
				float targetValue = org.apache.xmlbeans.XmlFloat.Factory.parse(target.getDomNode()).getFloatValue();
				return targetValue;
			}	
			if(metric.equals("xsd:double") || metric.equals("xs:double")){
				double targetValue = org.apache.xmlbeans.XmlDouble.Factory.parse(target.getDomNode()).getDoubleValue();
				return targetValue;
			}	
			if(metric.equals("xsd:boolean") || metric.equals("xs:boolean")){
				boolean targetValue = org.apache.xmlbeans.XmlBoolean.Factory.parse(target.getDomNode()).getBooleanValue();
				return targetValue;
			}	
			if(metric.equals("xsd:byte") || metric.equals("xs:byte")){
				byte targetValue = org.apache.xmlbeans.XmlByte.Factory.parse(target.getDomNode()).getByteValue();
				return targetValue;
			}	
			if(metric.equals("xsd:QName") || metric.equals("xs:QName")){
				javax.xml.namespace.QName targetValue = org.apache.xmlbeans.XmlQName.Factory.parse(target.getDomNode()).getQNameValue();
				return targetValue;
			}		
			if(metric.equals("xsd:dateTime") || metric.equals("xs:dateTime")){
				java.util.Calendar targetValue = org.apache.xmlbeans.XmlDateTime.Factory.parse(target.getDomNode()).getCalendarValue();
				return targetValue;
			}	
			if(metric.equals("xsd:base64Binary") || metric.equals("xs:base64Binary")){
				byte[] targetValue = org.apache.xmlbeans.XmlBase64Binary.Factory.parse(target.getDomNode()).getByteArrayValue();
				return targetValue;
			}	
			if(metric.equals("xsd:hexBinary") || metric.equals("xs:hexBinary")){
				byte[] targetValue = org.apache.xmlbeans.XmlHexBinary.Factory.parse(target.getDomNode()).getByteArrayValue();
				return targetValue;
			}	
			if(metric.equals("xsd:unsignedInt") || metric.equals("xs:unsignedInt")){
				long targetValue = org.apache.xmlbeans.XmlUnsignedInt.Factory.parse(target.getDomNode()).getLongValue();
				return targetValue;
			}	
			if(metric.equals("xsd:unsignedShort") || metric.equals("xs:unsignedShort")){
				int targetValue = org.apache.xmlbeans.XmlUnsignedShort.Factory.parse(target.getDomNode()).getIntValue();
				return targetValue;
			}
			if(metric.equals("xsd:unsignedByte") || metric.equals("xs:unsignedByte")){
				short targetValue = org.apache.xmlbeans.XmlUnsignedByte.Factory.parse(target.getDomNode()).getShortValue();
				return targetValue;
			}
			if(metric.equals("xsd:time") || metric.equals("xs:time")){
				java.util.Calendar targetValue = org.apache.xmlbeans.XmlTime.Factory.parse(target.getDomNode()).getCalendarValue();
				return targetValue;
			}
			if(metric.equals("xsd:date") || metric.equals("xs:date")){
				java.util.Calendar targetValue = org.apache.xmlbeans.XmlDate.Factory.parse(target.getDomNode()).getCalendarValue();
				return targetValue;
			}
			if(metric.equals("xsd:duration") || metric.equals("xs:duration")){
				org.apache.xmlbeans.GDuration targetValue = org.apache.xmlbeans.XmlDuration.Factory.parse(target.getDomNode()).getGDurationValue();
				return targetValue;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			// If no binding are found for the declared type or the previous binding fails, the value is binded
			// as a string.
			java.lang.String targetValue = org.apache.xmlbeans.XmlString.Factory.parse(target.getDomNode()).getStringValue();
			return targetValue;
		}catch(Exception e){
			// If EVERYTHING fails, we cannot bind the value.
			Exception ee = new Exception("Cannot bind the XML value to any java type.", e);
			throw ee;
		}	
	}
}
