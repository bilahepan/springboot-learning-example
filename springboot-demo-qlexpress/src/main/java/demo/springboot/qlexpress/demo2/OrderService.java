package demo.springboot.qlexpress.demo2;

import org.apache.commons.lang.StringUtils;

public class OrderService {

    public String getOrderId(String a) {
        if (StringUtils.isBlank(a)) {
            return "orderId-null";
        }
        if (a.equals("a")) {
            return "orderId-a";
        } else {
            return "orderId-b";
        }
    }

}
