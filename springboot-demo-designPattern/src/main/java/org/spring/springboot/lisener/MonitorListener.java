package org.spring.springboot.lisener;

import java.util.EventListener;

/**
 *所有事件监听器接口都要继承这个标签接口 EventListener
 *@author: 文若[gaotc@tuya.com]
 *@date: 2019/11/23 上午10:22
 */
public interface MonitorListener extends EventListener{

    public void handleEvent(PrintEvent event);

}
