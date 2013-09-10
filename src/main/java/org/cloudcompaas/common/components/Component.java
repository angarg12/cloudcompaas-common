package org.cloudcompaas.common.components;

import org.cloudcompaas.common.communication.RESTComm;
import org.cloudcompaas.common.security.server.SecurityHandler;
import org.cloudcompaas.common.security.server.UserCache;

/**
 * @author angarg12
 *
 */
public class Component {
	protected SecurityHandler securityHandler;
	
	public Component(){
		securityHandler = new SecurityHandler(); 
		RESTComm.bootstrapping();
		UserCache.bootstrapping();
	}
	
	public void customStartup(){}
	
	public SecurityHandler getSecurityHandler(){
		return securityHandler;
	}
}
