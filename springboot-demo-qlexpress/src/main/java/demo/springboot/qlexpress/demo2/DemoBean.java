package demo.springboot.qlexpress.demo2;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

public class DemoBean {

    public static void main(String[] args) throws Exception {
        String express = "orderId = orderService.getOrderId(input);";
        //
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();

        context.put("input","a");
        context.put("orderService", new OrderService());
        Object res = runner.execute(express, context, null, false, false);
        System.out.println(res.toString());
    }
}
