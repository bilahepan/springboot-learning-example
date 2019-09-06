package demo.springboot.thread.forkjoin;

/**
 *
 *@author: 文若[gaotc@tuya.com]
 *@date: 2019/8/27 下午5:10
 */
public class DemoCase {

    /**
     *
     * 核心的添加是新的ForkJoinPool执行者，专门执行实现了ForkJoinTask接口的实例。
     * ForkJoinTask对象支持创建子任务来等待子任务完成。
     * 有了这些清晰的语义，当一个任务正在等待另一个任务完成并且有待执行的任务时，executor就能够通过”偷取”任务，在内部的线程池里分发任务。
     *
     * ForkJoinTask对象主要有两个重要的方法：
     * fork()方法允许ForkJoinTask任务异步执行，也允许一个新的ForkJoinTask从存在的ForkJoinTask中被启动。
     * join()方法允许一个ForkJoinTask等待另一个ForkJoinTask执行完成
     *
     * */

    public static void main(String[] args) {

    }

    //[rɪ'kɜːsɪv] 递归Action  的实例代表执行没有返回结果。
    //RecursiveAction

    //RecursiveTask  有返回值



}