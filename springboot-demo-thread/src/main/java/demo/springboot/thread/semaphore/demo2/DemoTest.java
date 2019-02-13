package demo.springboot.thread.semaphore.demo2;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/19 下午1:46
 */
public class DemoTest {

    public static void main(String[] args) {
        //资源创建，并初始化
        ResourceManage resourceManage = new ResourceManage();
        //100个线程使用者
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new ResourceTask(resourceManage, i));//创建多个资源使用者
            threads[i] = thread;
        }
        //启动100个线程
        for (int i = 0; i < 100; i++) {
            Thread thread = threads[i];
            try {
                thread.start();//启动线程
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}