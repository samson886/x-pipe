package com.ctrip.xpipe.redis.console.alert;

import com.ctrip.xpipe.endpoint.HostPort;
import com.ctrip.xpipe.utils.ObjectUtils;

import java.util.Date;

/**
 * @author chen.zhu
 * <p>
 * Oct 12, 2017
 */
public class AlertEntity {

    private Date date;

    private String clusterId;

    private String shardId;

    private String message;

    private HostPort hostPort;

    private ALERT_TYPE alertType;

    public AlertEntity(HostPort hostPort, String clusterId, String shardId, String message, ALERT_TYPE alertType) {
        this.hostPort = hostPort;
        this.clusterId = clusterId;
        this.shardId = shardId;
        this.message = message;
        this.alertType = alertType;
        this.date = new Date(System.currentTimeMillis());
    }

    @Override
    public int hashCode() {
        int hashCode = alertType.hashCode();
        if(clusterId != null) {
            hashCode = hashCode * 31 + clusterId.hashCode();
        }
        if(shardId != null) {
            hashCode = hashCode * 31 + shardId.hashCode();
        }
        if(hostPort != null) {
            hashCode = hashCode * 31 + hostPort.hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof AlertEntity)) return false;
        if(obj == this) return true;
        AlertEntity other = (AlertEntity) obj;
        return ObjectUtils.equals(other.getAlertType(), this.alertType) &&
                ObjectUtils.equals(other.getClusterId(), this.clusterId) &&
                ObjectUtils.equals(other.getShardId(), this.shardId) &&
                ObjectUtils.equals(other.getHostPort(), this.hostPort);
    }

    @Override
    public String toString() {
        return "Alert: " + alertType + ", Cluster: " + clusterId
                + ", Shard: " + shardId + ", HostPort: " + hostPort + ", Message: " + message;
    }

    public String getKey() {
        StringBuffer sb = new StringBuffer(alertType + "");
        if(clusterId != null && !clusterId.isEmpty()) {
            sb.append(":").append(clusterId);
        }
        if(shardId != null && !shardId.isEmpty()) {
            sb.append(":").append(shardId);
        }
        if(hostPort != null) {
            sb.append(":").append(hostPort.toString());
        }
        if(message != null && !message.isEmpty()) {
            sb.append(":").append(message);
        }
        return sb.toString();
    }

    public ALERT_TYPE getAlertType() {
        return alertType;
    }

    public String getMessage() {
        return message;
    }

    public AlertEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public HostPort getHostPort() {
        return hostPort;
    }

    public AlertEntity setHostPort(HostPort hostPort) {
        this.hostPort = hostPort;
        return this;
    }

    public String getClusterId() {
        return clusterId;
    }

    public AlertEntity setClusterId(String clusterId) {
        this.clusterId = clusterId;
        return this;
    }

    public String getShardId() {
        return shardId;
    }

    public AlertEntity setShardId(String shardId) {
        this.shardId = shardId;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public AlertEntity setDate(Date date) {
        this.date = date;
        return this;
    }

    public void setAlertType(ALERT_TYPE alertType) {
        this.alertType = alertType;
    }

    public static class RedisAlertBuilder {
        private HostPort hostPort;
        private String clusterId;
        private String shardId;
        private String message;
        private ALERT_TYPE alertType;

        public RedisAlertBuilder hostPort(HostPort hostPort) {
            this.hostPort = hostPort;
            return this;
        }

        public RedisAlertBuilder clusterId(String clusterId) {
            this.clusterId = clusterId;
            return this;
        }

        public RedisAlertBuilder shardId(String shardId) {
            this.shardId = shardId;
            return this;
        }

        public RedisAlertBuilder message(String message) {
            this.message = message;
            return this;
        }

        public RedisAlertBuilder alertType(ALERT_TYPE alertType) {
            this.alertType = alertType;
            return this;
        }

        public AlertEntity createRedisAlert() {
            return new AlertEntity(this.hostPort, this.clusterId, this.shardId, this.message, this.alertType);
        }
    }
}
