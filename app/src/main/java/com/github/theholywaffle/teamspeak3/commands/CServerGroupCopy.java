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
package com.github.theholywaffle.teamspeak3.commands;

import com.github.theholywaffle.teamspeak3.api.PermissionGroupDatabaseType;
import com.github.theholywaffle.teamspeak3.commands.parameter.KeyValueParam;

public class CServerGroupCopy extends Command {

	public CServerGroupCopy(int sourceId, int targetId, String targetName,
			PermissionGroupDatabaseType t) {
		super("servergroupcopy");
		add(new KeyValueParam("ssgid", sourceId));
		add(new KeyValueParam("tsgid", targetId));
		add(new KeyValueParam("name", targetName));
		add(new KeyValueParam("type", t.getIndex()));
	}

}
