package demo.springboot.thread.semaphore.demo2;

/**
 * @author: bilahepan
 * @date: 2018/10/19 下午12:47
 */
public class ResourceTask implements Runnable {
    private ResourceManage resourceManage;

    private int userId;

    public ResourceTask(ResourceManage resourceManage, int userId) {
        this.resourceManage = resourceManage;
        this.userId = userId;
    }

    @Override
    public void run() {
        System.out.println("userId:" + userId + "准备使用资源...");
        resourceManage.useResource(userId);
        System.out.println("userId:" + userId + "使用资源完毕...");
    }
}