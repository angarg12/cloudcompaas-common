package org.cloudcompaas.common.communication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.apache.wink.common.http.HttpStatus;
import org.cloudcompaas.common.security.server.UserCache;
import org.cloudcompaas.common.util.XMLWrapper;

/**
 * @author angarg12
 *
 */
public class RESTComm {
	static Map<String,Map<String,String>> components;
	String username;
	String usernametoken;
	String componentType;
	String componentId;
	String contentType;
	String accept;
	String url;
	RestClient client;

	public static void bootstrapping(){
		components = new HashMap<String,Map<String,String>>();
		addEpr("InfrastructureConnector", "0", "http://158.42.104.100:8080/cloudcompaas-infrastructureconnector-0.0.1/rest/agreement/");
		addEpr("Orchestrator", "1", "http://158.42.104.100:8080/cloudcompaas-orchestrator-0.0.1/rest/agreement/");
		addEpr("SLAManager", "2", "http://158.42.104.100:8080/cloudcompaas-slamanager-0.0.1/rest/agreement/");
		addEpr("Catalog", "3", "http://158.42.104.100:8080/cloudcompaas-catalog-0.0.1/rest/");
		addEpr("PlatformConnector", "4", "http://158.42.104.100:8080/cloudcompaas-platformconnector-0.0.1/rest/agreement/");
	}
	
	// Elects one base EPR for the designed component type according to some specific
	// (potentially load balancing) algorithm.
	private synchronized static Map<String,String> getEpr(String componentType_){
		return components.get(componentType_);
	}
	
	private synchronized static String getEpr(String componentType_, String componentId_){
		Map<String,String> eprs = components.get(componentType_);
		if(eprs == null) return null;
		return eprs.get(componentId_);
		
	}
	
	private synchronized static void addEpr(String componentType_, String componentId_, String epr_){
		Map<String,String> eprs = components.get(componentType_);
		if(eprs == null){
			eprs = new HashMap<String, String>();
			components.put(componentType_, eprs);
		}
		eprs.put(componentId_, epr_);
	}
	
	public RESTComm(String componentType_){
		componentType = componentType_;
		usernametoken = UserCache.getPlatformToken();
		url = "";
		initialize();
	}
	
	public RESTComm(String componentType_, String username_){
		componentType = componentType_;
		username = username_;
		usernametoken = UserCache.get(username_);
		url = "";
		initialize();
	}
	
	private void initialize(){
		ClientConfig config = new ClientConfig();
    	config.connectTimeout(600000);
    	config.readTimeout(600000);
    	client = new RestClient(config);
	}
	
	private synchronized String retrieveEPR(){
		if(componentId != null){
			return getEpr(componentType, componentId);
		}else{
			Map<String,String> eprs = getEpr(componentType);
			int elected = new Random().nextInt(eprs.size());
			Iterator<String> it = eprs.values().iterator();
			while(elected > 0){
				it.next();
				elected--;
			}
			return it.next();
		}
	}
	
	public void setUsernameToken(String usernametoken_){
		usernametoken = usernametoken_;
	}
	
	public void setComponentId(String componentId_){
		componentId = componentId_;
	}
	
	public void setContentType(String contentType_){
		contentType = contentType_;
	}
	
	public void setAccept(String accept_){
		accept= accept_;
	}
	
	public void setUrl(String url_){
		url = url_;
	}
		
	private Resource createResource(){
		Resource resource = client.resource(retrieveEPR()+url).header("Authorization", "Basic "+usernametoken);
		
    	if(contentType != null){
    		resource.contentType(contentType);
    	}
    	if(accept != null){
    		resource.accept(accept);
    	}
    	
    	return resource;
	}
	
	public void delete() throws Exception {
		System.out.println("delete "+url+" "+usernametoken);
		//Thread.dumpStack();
    	Resource resource = createResource();
    	
    	ClientResponse clientResponse = resource.delete();

    	if(clientResponse.getStatusCode() != HttpStatus.OK.getCode()){
    		throw new Exception(clientResponse.getMessage());
    	}
	}
	
	public void put(Object payload) throws Exception {
		System.out.println("put "+url);
		//Thread.dumpStack();
    	Resource resource = createResource();
    	
    	ClientResponse clientResponse = resource.put(payload);

    	if(clientResponse.getStatusCode() != HttpStatus.OK.getCode()){
    		throw new Exception(clientResponse.getMessage());
    	}
	}
	
	public XMLWrapper post(Object payload) throws Exception {
		System.out.println("post "+url);
		//Thread.dumpStack();
    	Resource resource = createResource();
    	
    	ClientResponse clientResponse = resource.post(payload);

    	if(clientResponse.getStatusCode() != HttpStatus.OK.getCode()){
    		throw new Exception(clientResponse.getMessage());
    	}
    	
    	return new XMLWrapper(clientResponse.getEntity(String.class));
	}

	public XMLWrapper get() throws Exception {
		System.out.println("get "+url);
		//Thread.dumpStack();
    	Resource resource = createResource();
    	
    	ClientResponse clientResponse = resource.get();

    	if(clientResponse.getStatusCode() != HttpStatus.OK.getCode()){
    		throw new Exception(clientResponse.getMessage());
    	}
    	
    	return new XMLWrapper(clientResponse.getEntity(String.class));
	}
}
