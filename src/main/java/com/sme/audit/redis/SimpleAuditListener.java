package com.sme.audit.redis;

import redis.clients.jedis.JedisPubSub;

import java.util.logging.Logger;


/**
 *  Redis listener for  for audit channel.
 *  On receiving an audit message will simply convert to AuditEvent and pass to action
 *
 */

public class SimpleAuditListener extends JedisPubSub {

    AuditAction action;
    private final static Logger LOGGER = Logger.getLogger(SimpleAuditListener.class.getName());


    public SimpleAuditListener setAction(AuditAction action) {
        this.action = action;
        return this;
    }


    public void onMessage(String channel, String message) {
        LOGGER.fine("onMessage" + ":" + channel + ":" + message);
        action.action(AuditEvent.createAuditEvent(message));
    }

    public void onSubscribe(String channel, int subscribedChannels) {
       LOGGER.fine("onSubscribe" + ":" + channel + ":" + subscribedChannels);
    }

    public void onUnsubscribe(String channel, int subscribedChannels) {
        LOGGER.fine("onUnsubscribe" + ":" + channel + ":" + subscribedChannels);
    }

    public void onPSubscribe(String pattern, int subscribedChannels) {
        LOGGER.fine("onPSubscribe" + ":" + pattern + ":" + subscribedChannels);
    }

    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        LOGGER.fine("onPUnsubscribe" + ":" + pattern + ":" + subscribedChannels);
    }

    public void onPMessage(String pattern, String channel, String message) {
        LOGGER.fine("onPMessage" + ":" + pattern + ":" + channel + ":" + message);
    }
}
