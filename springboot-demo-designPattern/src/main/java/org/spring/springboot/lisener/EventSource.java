package org.spring.springboot.lisener;

import java.util.Vector;

/**
 * 事件源
 * 事件源是是事件对象的入口，包含监听器的注册、撤销、通知
 *
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/11/23 上午10:27
 */
public class EventSource {
    //监听器列表，如果监听事件源的事件，注册监听器可以加入此列表
    private Vector<MonitorListener> listenerList = new Vector<MonitorListener>();

    //注册监听器
    public void addListener(MonitorListener eventListener) {
        listenerList.add(eventListener);
    }

    //删除监听器
    public void removeListener(MonitorListener eventListener) {
        int i = listenerList.indexOf(eventListener);
        if (i >= 0) {
            listenerList.remove(eventListener);
        }
    }

    //接受外部事件，通知所有的监听器
    public void notifyListenerEvents(PrintEvent event) {
        for (MonitorListener listener : listenerList) {
            listener.handleEvent(event);
        }
    }
}