package org.cloudcompaas.common.security.server;

import org.cloudcompaas.common.communication.RESTComm;
import org.cloudcompaas.common.util.Base64Coder;
import org.cloudcompaas.common.util.XMLWrapper;

/**
 * @author angarg12
 *
 */
public class SecurityHandler {
	String username;
	
	public boolean authenticate(String auth){
		try {
			String[] tokens = auth.split("Basic ");
			String basicToken = tokens[1];
			String[] userpass = Base64Coder.decodeString(basicToken).split(":");
			username = userpass[0];

			if(UserCache.get(username) == null){
				String password = userpass[1];
 
            	RESTComm comm = new RESTComm("Catalog");
        		comm.setUrl("/user/search?name="+username);
    			XMLWrapper wrap = comm.get();
            	String passwd = wrap.getFirst("//passwd");
            	
	    		if(password.equals(passwd) == true){
	    			UserCache.put(username, basicToken);
	    			return true;
	    		}
	    		return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public String getUsername(String auth){
		try {
			String[] tokens = auth.split("Basic ");
			String basicToken = tokens[1];
			String[] userpass = Base64Coder.decodeString(basicToken).split(":");
			return userpass[0];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
