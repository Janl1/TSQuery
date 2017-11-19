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
package com.github.theholywaffle.teamspeak3.api;

public enum ServerInstanceProperty implements Property {

	CONNECTION_BANDWIDTH_RECEIVED_LAST_MINUTE_TOTAL(false),
	CONNECTION_BANDWIDTH_RECEIVED_LAST_SECOND_TOTAL(false),
	CONNECTION_BANDWIDTH_SENT_LAST_MINUTE_TOTAL(false),
	CONNECTION_BANDWIDTH_SENT_LAST_SECOND_TOTAL(false),
	CONNECTION_BYTES_RECEIVED_TOTAL(false),
	CONNECTION_BYTES_SENT_TOTAL(false),
	CONNECTION_FILETRANSFER_BANDWIDTH_RECEIVED(false),
	CONNECTION_FILETRANSFER_BANDWIDTH_SENT(false),
	CONNECTION_FILETRANSFER_BYTES_RECEIVED_TOTAL(false),
	CONNECTION_FILETRANSFER_BYTES_SENT_TOTAL(false),
	CONNECTION_PACKETS_RECEIVED_TOTAL(false),
	CONNECTION_PACKETS_SENT_TOTAL(false),
	HOST_TIMESTAMP_UTC(false),
	INSTANCE_UPTIME(false),
	SERVERINSTANCE_DATABASE_VERSION(false),
	SERVERINSTANCE_FILETRANSFER_PORT(true),
	SERVERINSTANCE_GUEST_SERVERQUERY_GROUP(true),
	SERVERINSTANCE_MAX_DOWNLOAD_TOTAL_BANDWIDTH(true),
	SERVERINSTANCE_MAX_UPLOAD_TOTAL_BANDWIDTH(true),
	SERVERINSTANCE_PERMISSIONS_VERSION(false),
	SERVERINSTANCE_SERVERQUERY_BAN_TIME(true),
	SERVERINSTANCE_SERVERQUERY_FLOOD_COMMANDS(true),
	SERVERINSTANCE_SERVERQUERY_FLOOD_TIME(true),
	SERVERINSTANCE_TEMPLATE_CHANNELADMIN_GROUP(true),
	SERVERINSTANCE_TEMPLATE_CHANNELDEFAULT_GROUP(true),
	SERVERINSTANCE_TEMPLATE_SERVERADMIN_GROUP(true),
	SERVERINSTANCE_TEMPLATE_SERVERDEFAULT_GROUP(true),
	VIRTUALSERVERS_RUNNING_TOTAL(false),
	VIRTUALSERVERS_TOTAL_CHANNELS_ONLINE(false),
	VIRTUALSERVERS_TOTAL_CLIENTS_ONLINE(false),
	VIRTUALSERVERS_TOTAL_MAXCLIENTS(false);

	private boolean changeable;

	ServerInstanceProperty(boolean changeable) {
		this.changeable = changeable;
	}

	public String getName() {
		return name().toLowerCase();
	}

	public boolean isChangeable() {
		return changeable;
	}

}
