package demo.springboot.kafka.sources.basic.domain;

import demo.springboot.kafka.commons.NoNullFieldStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/25 下午8:44
 */
public class MqKafkaPartitions {

    private Integer partition;
    private Long Offset;

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Long getOffset() {
        return Offset;
    }

    public void setOffset(Long offset) {
        Offset = offset;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new NoNullFieldStringStyle());
    }
}