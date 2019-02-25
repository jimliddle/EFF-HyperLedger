package com.sme.audit.redis;

/**
 * Audit Action class implement this interface for SimpleAuditListener
 */
public interface AuditAction {
     void action(AuditEvent event);
}
