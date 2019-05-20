package demo.springboot.thread.callable.demo2;

import java.util.concurrent.Callable;

/**
 *
 *@author: bilahepan
 *@date: 2018/10/18 下午7:37
 */
public class ComputeTask implements Callable<Integer> {

    /**  */
    private Integer result   = 0;

    /**  */
    private String  taskName = "";

    /**
     *
     */
    public ComputeTask() {
    }

    public ComputeTask(Integer initResult, String taskName) {
        result = initResult;
        this.taskName = taskName;
        System.out.println("生成子线程计算任务:" + taskName);

    }

    /**
     *
     * @return
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public Integer call() throws Exception {
        for (int i = 0; i < 100; i++) {
            result += i;
        }
        //休眠2秒，观察主线程行为，预期结果是主线程会继续执行。
        //Thread.sleep(2000);
        System.out.println("子线程计算任务:" + taskName + "执行完毕.");
        return result;
    }


}
