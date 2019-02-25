package com.sme.audit;

import com.sme.audit.redis.AuditAction;
import com.sme.audit.redis.AuditEvent;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class BlockChainAuditAction implements AuditAction {
    private String hyperLedgerEndPoint;
    Random rand = new Random();

    @Override
    public void action(AuditEvent event) {
        System.out.println("Publishing event " + event);
        try {
            Content content = Request.Post(hyperLedgerEndPoint)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .bodyString(getBody(event), ContentType.APPLICATION_JSON)
                    .execute().returnContent();

            System.out.println("Resposne: " + content);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void setHyperLedgerEndPoint(String endpoint) {
        this.hyperLedgerEndPoint= endpoint;
    }

    private String getBody(AuditEvent event) {
        JSONObject json = new JSONObject();
        json.put("$class", "org.sme.audit.AuditEvent");
        json.put("id", event.getTime() + "-" + rand.nextInt());
        json.put("actor", event.getActor());
        json.put("eventType", event.getEventType());
        json.put("ip", event.getIp());
        json.put("time", epochToIso8601(event.getTime()));
        json.put("tool", event.getTool());
        json.put("log", event.getLog());
        return json.toString();

    }

    private String epochToIso8601(long time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new Date(time));
    }
}
