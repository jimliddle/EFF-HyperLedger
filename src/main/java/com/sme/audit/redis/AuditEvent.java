package com.sme.audit.redis;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AuditEvent {

    private String actor;
    private String eventType;
    private String ip;
    private long time;
    private String tool;
    private String log;
    private String raw;

//TODO: parse the log entry

    private AuditEvent(String message) {

        JSONObject json = new JSONObject(message);

        raw = message;
        actor = json.getString("actor");
        eventType = json.getString("eventtype");
        ip = json.getString("ip");


        String dateString = json.getString("date");
        try {
            //"Sun, 11 Feb 18 22:13:19 +0000"
            time = new SimpleDateFormat("EEE, d MMM yy HH:mm:ss Z").parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tool = json.getString("tool");
        log = json.getString("log");
    }

    public static AuditEvent createAuditEvent(String message) {
        return new AuditEvent(message);
    }

    public String getActor() {
        return actor;
    }

    public String getEventType() {
        return eventType;
    }

    public String getIp() {
        return ip;
    }


    /**
     * Time from epoch
     * @return
     */
    public long getTime() {
        return time;
    }

    public String getTool() {
        return tool;
    }

    public String getLog() {
        return log;
    }

    public String getRaw() {
        return raw;
    }

    @Override
    public String toString() {
        return "AuditEvent{" +
                "actor='" + actor + '\'' +
                ", eventType='" + eventType + '\'' +
                ", ip='" + ip + '\'' +
                ", time='" + time + '\'' +
                ", tool='" + tool + '\'' +
                ", log='" + log + '\'' +
                ", raw='" + raw + '\'' +
                '}';
    }
}
