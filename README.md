Enterprise File Fabric provides audit logs for user interaction and the events to log can be set by the organization admin.
To integrate with external systems EFF provides a streaming interface that can be used to publish the audit events to externals sytems.

To do this you will need to implement a php interface

Configures config.inc.php.

Note your implementation will be called in the main thread of the operation so your handler should process the enent quickly
Your Handler should not through any exceptions or fail as the code will be executed in the main thread
We recommend that you publish the events to a message system or cache e.g kafka or Redis and then consume from the message system. This will keep the latency low.


# Sample Enterprise File Fabric audit listener with Redis

EFF provides an interface to publish audit events. This sample show the integration with Redis.

<pre>
                                                  ┌─────────────┐
                                                  │             │
┌──────────────────┐                             ╱│ Subscriber  │
│                  │                            ╱ │             │
│                  │         ┌─────────────┐   ╱  └─────────────┘
│                  │         │             │  ╱                  
│ Enterprise File  │         │             │ ╱                   
│      Fabric      │─────────▶    Redis    │▕                    
│                  │ ┌─────┐ │             │ ╲                   
│                  │ │Audit│ │             │  ╲                  
│                  │ │Event│ └─────────────┘   ╲  ┌─────────────┐
│                  │ └─────┘                    ╲ │             │
└──────────────────┘                             ╲│ Subscriber  │
                                                  │             │
                                                  └─────────────┘

</pre> 

In this sample the audit events are published to Redis and a Java client subscribes to these events.

## Setup

### Enterprise File Fabric
* As root install library `yum install php-pecl-redis` or `pecl install redis` 
* restart php-fpm `systemctl restart php-fpm`
* SSH to Appliance and as `smestorage` user
* copy  RedisAuditEventHandlerInterface.php to /var/www/smestorage/auditevents/ (create the folder if does not exist) 
* add the following line to `config.inc.php`

```php
 var $audit_event_handler_path = '/var/www/smestorage/auditevents/RedisAuditEventHandlerInterface.php';
```
* Edit `/var/www/smestorage/auditevents/RedisAuditEventHandlerInterface.php` and change the host and port according to your setup.


### Java Subscriber

* build the project `./gradlew fatjar`
* Execute the subscriber

```
java -jar ./build/libs/auditlistener-all-0.1.jar redishost redisport channel name
```
e.g.
```
java -jar ./build/libs/auditlistener-all-0.1.jar 127.0.0.1 6379 audit_events_channel
```

## Extending
You can extend the subscriber by writing your own implementation of AuditAction
