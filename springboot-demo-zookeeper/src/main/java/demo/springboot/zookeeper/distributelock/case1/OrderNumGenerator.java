package demo.springboot.zookeeper.distributelock.case1;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成订单类
 *
 * @author: bilahepan
 * @date: 2019/5/12 下午9:47
 */
public class OrderNumGenerator {
    //全局订单id
    public static int count = 0;

    public String getNumber() {
        try {
            Thread.sleep(200);
        } catch (Exception e) {

        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date()) + "-" + ++count;
    }
}