package demo.springboot.thread.exercise.commandPattern;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/18 下午7:42
 */

/**
 * @author gao tianci
 * @version $Id: Test.java, v 0.1 2018年3月17日 下午4:19:49 gao tianci Exp $
 */
public class DemoTest {

    public static void main(String[] args) {
        boolean b = Retry.execute(3, new RetryRunnable() {
            //怎么做
            @Override
            public void run() {
                try {
                    //todo 做具体的任务
                    //URL url = new URL("http://www.ly.com");
                    //String html = IOUtils.toString(url, "UTF-8");
                    //System.out.println(html);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new IllegalArgumentException("任务处理出错!");
                }
            }
        });
        System.out.println("执行结果" + b);
    }

}
