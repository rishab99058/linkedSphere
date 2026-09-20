package com.linksphere.common.constants;

public final class RabbitMQConstants {

    private RabbitMQConstants() {
    }

    public static final String EXCHANGE = "linksphere.exchange";

    public static final String USER_QUEUE = "user.queue";
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String EMAIL_QUEUE = "email.queue";

    public static final String USER_CREATED = "user.created";
    public static final String NOTIFICATION_CREATED = "notification.created";
    public static final String EMAIL_SEND = "email.send";

    public static final String N8N_QUEUE = "n8n.queue";
    public static final String N8N_TRIGGER_RECEIVED = "n8n.trigger.received";

    public static final String N8N_RESPONSE_QUEUE = "n8n.response.queue";
    public static final String N8N_RESPONSE_RECEIVED = "n8n.response.received";

    public static final String USER_SYNC_QUEUE = "user.sync.queue";
    public static final String USER_SYNC = "user.sync";

}
