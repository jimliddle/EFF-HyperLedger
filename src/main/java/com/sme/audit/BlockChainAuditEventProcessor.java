package com.sme.audit;

import com.sme.audit.redis.SimpleAuditListener;
import com.sme.audit.redis.RedisAuditHandler;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class BlockChainAuditEventProcessor {

    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String channel = args[2];
        String hyperledgerEndpoint = args[3];
        System.out.println("Will listen on " + host + " " + port + " for" + channel + " and publish to " + hyperledgerEndpoint );
        JedisPool pool = Utils.getJedisPool(host, port);
        BlockChainAuditAction action = new BlockChainAuditAction();
        action.setHyperLedgerEndPoint(hyperledgerEndpoint);
        JedisPubSub listener = new SimpleAuditListener().setAction(action);
        RedisAuditHandler handler = new RedisAuditHandler().setJedisPool(pool).
                setChannel(channel).setListener(listener);
        handler.start();
    }

}
