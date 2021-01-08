package demo.springboot.qlexpress.demo1;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

/**
 * @author: bilahepan
 * @date: 2018/12/17 下午3:50
 *
 * 测试用例：
 * https://github.com/alibaba/QLExpress/tree/master/src/test/java/com/ql/util/express/test
 */
public class HelloWorld {

    public static void main(String[] args) {
        //oneTest();
    }


    private static void oneTest() {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        //
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        //
        String express = "a+b*c";
        Object result = 0;
        try {
            result = runner.execute(express, context, null
                    , true, false);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(result);//7
    }

}