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

public enum ClientProperty implements Property {

	CID(false),
	CLIENT_AWAY(false),
	CLIENT_AWAY_MESSAGE(false),
	CLIENT_BASE64HASHCLIENTUID(false),
	CLIENT_CHANNEL_GROUP_ID(false),
	CLIENT_CHANNEL_GROUP_INHERITED_CHANNEL_ID(false),
	CLIENT_COUNTRY(false),
	CLIENT_CREATED(false),
	CLIENT_DATABASE_ID(false),
	CLIENT_DEFAULT_CHANNEL(false),
	CLIENT_DEFAULT_TOKEN(false),
	CLIENT_DESCRIPTION(true),
	CLIENT_FLAG_AVATAR(false),
	CLIENT_ICON_ID(true),
	CLIENT_IDLE_TIME(false),
	CLIENT_INPUT_HARDWARE(false),
	CLIENT_INPUT_MUTED(false),
	CLIENT_IS_CHANNEL_COMMANDER(true),
	CLIENT_IS_PRIORITY_SPEAKER(false),
	CLIENT_IS_RECORDING(false),
	CLIENT_IS_TALKER(true),
	CLIENT_LASTCONNECTED(false),
	CLIENT_LOGIN_NAME(false),
	CLIENT_META_DATA(false),
	CLIENT_MONTH_BYTES_DOWNLOADED(false),
	CLIENT_MONTH_BYTES_UPLOADED(false),
	CLIENT_NEEDED_SERVERQUERY_VIEW_POWER(false),
	CLIENT_NICKNAME(true),
	CLIENT_NICKNAME_PHONETIC(false),
	CLIENT_OUTPUT_HARDWARE(false),
	CLIENT_OUTPUT_MUTED(false),
	CLIENT_OUTPUTONLY_MUTED(false),
	CLIENT_PLATFORM(false),
	CLIENT_SERVERGROUPS(false),
	CLIENT_TALK_POWER(false),
	CLIENT_TALK_REQUEST(false),
	CLIENT_TALK_REQUEST_MSG(false),
	CLIENT_TOTAL_BYTES_DOWNLOADED(false),
	CLIENT_TOTAL_BYTES_UPLOADED(false),
	CLIENT_TOTALCONNECTIONS(false),
	CLIENT_TYPE(false),
	CLIENT_UNIQUE_IDENTIFIER(false),
	CLIENT_UNREAD_MESSAGES(false),
	CLIENT_VERSION(false),
	CONNECTION_BANDWIDTH_RECEIVED_LAST_MINUTE_TOTAL(false),
	CONNECTION_BANDWIDTH_RECEIVED_LAST_SECOND_TOTAL(false),
	CONNECTION_BANDWIDTH_SENT_LAST_MINUTE_TOTAL(false),
	CONNECTION_BANDWIDTH_SENT_LAST_SECOND_TOTAL(false),
	CONNECTION_BYTES_RECEIVED_TOTAL(false),
	CONNECTION_BYTES_SENT_TOTAL(false),
	CONNECTION_CLIENT_IP(false),
	CONNECTION_CONNECTED_TIME(false),
	CONNECTION_FILETRANSFER_BANDWIDTH_RECEIVED(false),
	CONNECTION_FILETRANSFER_BANDWIDTH_SENT(false),
	CONNECTION_PACKETS_RECEIVED_TOTAL(false),
	CONNECTION_PACKETS_SENT_TOTAL(false);

	private boolean changeable;

	ClientProperty(boolean changeable) {
		this.changeable = changeable;
	}

	public String getName() {
		return name().toLowerCase();
	}

	public boolean isChangeable() {
		return changeable;
	}

}
