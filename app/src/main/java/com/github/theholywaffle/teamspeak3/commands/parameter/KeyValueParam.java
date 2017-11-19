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
package com.github.theholywaffle.teamspeak3.commands.parameter;

import com.github.theholywaffle.teamspeak3.StringUtil;

public class KeyValueParam extends Parameter{
	
	private String key;
	private String value;

	public KeyValueParam(String key, String value){
		this.key=key;
		this.value=value;
	}
	
	public KeyValueParam(String key, int value) {
		this(key,value+"");
	}

	public KeyValueParam(String key, long value) {
		this(key,value+"");
	}

	public String build() {
		return StringUtil.encode(key)+"="+StringUtil.encode(value);
	}

}
