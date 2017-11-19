/*******************************************************************************
 * Copyright (c) 2014 Bert De Geyter (https://github.com/TheHolyWaffle).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Bert De Geyter (https://github.com/TheHolyWaffle)
 ******************************************************************************/
package com.github.theholywaffle.teamspeak3.api.event;

import java.util.HashMap;

import com.github.theholywaffle.teamspeak3.api.wrapper.Wrapper;

public class ClientLeaveEvent extends Wrapper implements TS3Event,
		TS3EventEmitter {
	
	public ClientLeaveEvent() {
		super(null);
	}

	public ClientLeaveEvent(HashMap<String, String> map) {
		super(map);
	}
	
	public int getClientFromId(){
		return getInt("cfid");
	}
	
	public int getClientTargetId(){
		return getInt("ctid");
	}
	
	public int getReasonId(){
		return getInt("reasonid");
	}
	
	public int getInvokerId(){
		return getInt("invokerid");
	}
	
	public String getInvokerName(){
		return get("invokername");
	}
	
	public String getInvokerUniqueId(){
		return get("invokeruid");
	}
	
	public String getReasonMessage(){
		return get("reasonmsg");
	}
	
	public int getClientId(){
		return getInt("clid");
	}

	@Override
	public void fire(TS3Listener listener, HashMap<String, String> map) {
		listener.onClientLeave(new ClientLeaveEvent(map));
	}

}
