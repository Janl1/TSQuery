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
package com.github.theholywaffle.teamspeak3.api.wrapper;

import java.util.HashMap;

import com.github.theholywaffle.teamspeak3.api.PermissionGroupDatabaseType;

public class ChannelGroup extends Wrapper {

	public ChannelGroup(HashMap<String, String> map) {
		super(map);
	}

	public int getGroupId() {
		return getInt("cgid");
	}

	public String getName() {
		return get("name");
	}

	public PermissionGroupDatabaseType getType() {
		for (PermissionGroupDatabaseType t : PermissionGroupDatabaseType
				.values()) {
			if (t.getIndex() == getInt("type")) {
				return t;
			}
		}
		return null;
	}

	public long getIconId() {
		return getLong("iconid");
	}

	public boolean isSavedInDatabase() {
		return getBoolean("savedb");
	}

	public int getSortId() {
		return getInt("sortid");
	}

	public int getNameMode() {
		return getInt("namemode");
	}
	
	public int getModifyPower(){
		return getInt("n_modifyp");
	}
	
	public int getMemberAddPower(){
		return getInt("n_member_addp");
	}
	
	public int getMemberRemovePower(){
		return getInt("n_member_removep");
	}

}
