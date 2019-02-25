<?php

/**
 * Class RedisAuditEventHandlerInterface
 *
 * This is an example Event Handler Interface that recieves event notifications and publishes out
 * to a local file called audit.log
 *
 * @author Ihor Kostyrko <ihor@storagemadeeasy.com>
 */
class RedisAuditEventHandlerInterface implements AuditEventHandlerInterface
{
    private $redis;

    /**
     * Connect to Redis
     */
	function __construct() {
        $this->redis = new Redis();
        $this->redis->connect('127.0.0.1', 6379);
    }

    /**
     * Handles an event from the audit stream
     *
     * @param SMEAPP_Audit_Event $auditEvent
     */
    public function handleEvent(SMEAPP_Audit_Event $auditEvent)
    {
        $message = json_encode(array(
                'actor' => $auditEvent->getActor(),
                'eventtype' => $auditEvent->getEventType(),
                'ip' => $auditEvent->getIp(),
                'date' => $auditEvent->getDate()->format(DATE_RFC822),
                'tool' => $auditEvent->getTool(),
                'log' => $auditEvent->getLog(),
        ));
            
        $this->redis->publish('audit_events_channel', $message);
    }

    /**
     * Connect to Redis
     */
	function __destruct() {
        $this->redis->close();
    }
   
}