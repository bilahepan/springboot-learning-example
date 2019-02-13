package demo.springboot.thread.exercise.commandPattern;

/**
 *
 *@author: 文若[gaotc@tuya.com]
 *@date: 2018/10/18 下午7:40
 */
public class Retry {
    /**
     * 最多执行task任务maxTimes多次
     * @param maxTimes
     * @param task
     * @return 是否执行成功
     */
    public static boolean execute(int maxTimes, RetryRunnable task) {
        for (int i = 0; i < maxTimes; i++) {
            try {
                //什么时候做,run方法里抛异常，失败可以重试
                task.run();
                //执行到这里说明执行没问题
                return true;
            } catch (Exception ex) {
                System.out.println("执行出现异常" + ex);
            }
        }
        //执行到这里说明执行出错
        return false;
    }
}