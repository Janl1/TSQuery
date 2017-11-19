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

import com.github.theholywaffle.teamspeak3.api.ServerInstanceProperty;

public class InstanceInfo extends Wrapper {

	public InstanceInfo(HashMap<String, String> map) {
		super(map);
	}

	public int getDatabaseVersion() {
		return getInt(ServerInstanceProperty.SERVERINSTANCE_DATABASE_VERSION);
	}

	public int getFileTransferPort() {
		return getInt(ServerInstanceProperty.SERVERINSTANCE_FILETRANSFER_PORT);
	}

	public long getMaxDownloadBandwidth() {
		return getLong(ServerInstanceProperty.SERVERINSTANCE_MAX_DOWNLOAD_TOTAL_BANDWIDTH);
	}

	public long getMaxUploadBandwidth() {
		return getLong(ServerInstanceProperty.SERVERINSTANCE_MAX_UPLOAD_TOTAL_BANDWIDTH);
	}

	public int getGuestServerQueryGroup() {
		return getInt(ServerInstanceProperty.SERVERINSTANCE_GUEST_SERVERQUERY_GROUP);
	}

	public int getMaxFloodCommands() {
		return getInt(ServerInstanceProperty.SERVERINSTANCE_SERVERQUERY_FLOOD_COMMANDS);
	}

	public int getMaxFloodTime() { // SECONDS
		return getInt(ServerInstanceProperty.SERVERINSTANCE_SERVERQUERY_FLOOD_TIME);
	}

	public int getFloodBanTime() {// SECONDS
		return getInt(ServerInstanceProperty.SERVERINSTANCE_SERVERQUERY_BAN_TIME);
	}

	public int getServerAdminGroup() {
		return getInt(ServerInstanceProperty.SERVERINSTANCE_TEMPLATE_SERVERADMIN_GROUP);
	}

	public int getDefaultServerGroup() {
		return getInt(ServerInstanceProperty.SERVERINSTANCE_TEMPLATE_SERVERDEFAULT_GROUP);
	}

	public int getChannelAdminGroup() {
		return getInt(ServerInstanceProperty.SERVERINSTANCE_TEMPLATE_CHANNELADMIN_GROUP);
	}

	public int getDefaultChannelGroup() {
		return getInt(ServerInstanceProperty.SERVERINSTANCE_TEMPLATE_CHANNELDEFAULT_GROUP);
	}

	public int getPermissionsVersion() {
		return getInt(ServerInstanceProperty.SERVERINSTANCE_PERMISSIONS_VERSION);
	}
}
