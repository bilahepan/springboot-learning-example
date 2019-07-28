package demo.springboot.kafka.sources.basic.domain;

import demo.springboot.kafka.commons.NoNullFieldStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/25 下午7:22
 */
public class MqKafkaData<T> implements Serializable {

    private static final long serialVersionUID = -1;

    private T data;//消息数据，payload

    private int count;//消息发送次数记录,用于判断是否失败重传

    private long ct = 0L;//消息发送时间戳

    private long rt = 0L;

    private String groupId;

    private List<Long> offsets = null;


    public MqKafkaData() {
    }


    public MqKafkaData(T data) {
        this.data = data;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getCt() {
        return ct;
    }

    public void setCt(long ct) {
        this.ct = ct;
    }

    public long getRt() {
        return rt;
    }

    public void setRt(long rt) {
        this.rt = rt;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<Long> getOffsets() {
        return offsets;
    }

    public void setOffsets(List<Long> offsets) {
        this.offsets = offsets;
    }

    //
    public void addOffsets(long offset) {
        if (this.offsets == null) {
            this.offsets = new ArrayList<>();
        }
        this.offsets.add(offset);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new NoNullFieldStringStyle());
    }
}