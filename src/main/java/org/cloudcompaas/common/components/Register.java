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
package org.cloudcompaas.common.components;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import org.cloudcompaas.common.communication.RESTComm;

/**
 * @author angarg12
 *
 */
public class Register extends Thread {
	protected Thread deployingThread;
	String serviceName;
	String serviceVersion; 
	String epr;
	
	public Register(Thread deployingThread_, String serviceName_, String serviceVersion_, String epr_){
		deployingThread = deployingThread_;
		serviceName = serviceName_;
		serviceVersion = serviceVersion_; 
		epr = epr_;
	}
	
	public void run(){
		try{
			deployingThread.join();
			
			RESTComm comm = new RESTComm("Catalog");
			comm.setUrl("service/search?name="+serviceName);
			String serviceId = comm.get().getFirst("//id_service");
			comm.setUrl("serviceversion/search?id_service="+serviceId+"&version="+serviceVersion);
			String versionId = comm.get().getFirst("//id_serviceversion");
			
			Properties properties = new Properties();
			properties.put("epr", epr);
			properties.put("service", serviceId);
			properties.put("version", versionId);
			
			String payload;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			properties.storeToXML(baos, null);
			payload = baos.toString();

			comm.setUrl("service_instance/search?epr="+epr);
			comm.delete();
	    	comm.setUrl("service_instance");
	    	comm.setContentType("text/xml");
			comm.post(payload);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
