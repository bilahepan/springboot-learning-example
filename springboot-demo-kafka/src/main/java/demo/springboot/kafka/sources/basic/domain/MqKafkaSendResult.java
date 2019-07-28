package demo.springboot.kafka.sources.basic.domain;

import demo.springboot.kafka.commons.NoNullFieldStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/25 下午9:36
 */
public class MqKafkaSendResult implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long offset;

    private Integer partition;

    private Long timestamp;

    private String messageId;

    private boolean success = true;

    private String errorCode;

    private String errorMsg;

    public MqKafkaSendResult(long timestamp, String messageId) {
        this.success = true;
        this.timestamp = timestamp;
        this.messageId = messageId;
    }

    public MqKafkaSendResult(long offset, int partition) {
        this.success = true;
        this.offset = offset;
        this.partition = partition;
    }

    public MqKafkaSendResult(long offset, int partition, long timestamp, String messageId) {
        this.success = true;
        this.offset = offset;
        this.partition = partition;
        this.timestamp = timestamp;
        this.messageId = messageId;
    }

    public MqKafkaSendResult(String errorCode, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
        this.success = false;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new NoNullFieldStringStyle());
    }
}