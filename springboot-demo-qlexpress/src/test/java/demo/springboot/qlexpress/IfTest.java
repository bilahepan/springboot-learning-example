package demo.springboot.qlexpress;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

/**
 * If 条件测试
 *
 * @author: bilahepan
 * @date: 2018/12/18 下午2:53
 */
public class IfTest {

    //if
    @org.junit.Test
    public void test1() throws Exception {
        ExpressRunner runner = new ExpressRunner(true, false);
        String express = "";
        express = "if 1==1 then return 100   else return 10;";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        Object result = runner.execute(express, context, null, false, false);
        System.out.println(result);
    }


    //if
    @org.junit.Test
    public void test2() throws Exception {
        ExpressRunner runner = new ExpressRunner(true, false);
        String express = "";
        express = "if 1==2 then {return 100}  else {return 10;}";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        Object result = runner.execute(express, context, null, false, false);
        System.out.println(result);
    }
}