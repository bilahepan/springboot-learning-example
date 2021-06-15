package org.springboot.pulsar.pulsar.mq;

public interface IMessageHandler {
    /**
     * payload business processing
     */
    public void handler(String payload);
}
