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