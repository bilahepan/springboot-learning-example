package org.spring.springboot.lisener;

import java.util.EventObject;

/**
 * <p>
 * The root class from which all event state objects shall be derived.
 * <p>
 * All Events are constructed with a reference to the object, the "source",
 * that is logically deemed to be the object upon which the Event in question
 * initially occurred upon.
 *
 * @since JDK1.1
 */

/**
 * 事件对象
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/11/23 上午10:24
 */
public class PrintEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public PrintEvent(Object source) {
        super(source);
    }


    public void doEvent() {
        System.out.println("通知一个事件源 source: " + this.getSource());
    }

}