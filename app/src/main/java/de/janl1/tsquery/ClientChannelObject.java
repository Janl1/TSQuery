package de.janl1.tsquery;


import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public class ClientChannelObject {

    private Channel channel;
    private Client client;

    public ClientChannelObject()
    {

    }

    public Channel getChannel() {
        return this.channel;
    }

    public Client getClient() {
        return this.client;
    }

    public ClientChannelObject setChannel(Channel channel)
    {
        this.channel = channel;
        return this;
    }

    public ClientChannelObject setClient(Client client)
    {
        this.client = client;
        return this;
    }

    public boolean isChannel()
    {
        return (this.client == null);
    }
}
