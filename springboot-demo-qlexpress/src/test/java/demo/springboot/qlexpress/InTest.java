package demo.springboot.qlexpress;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * In 测试
 *@author: 文若[gaotc@tuya.com]
 *@date: 2018/12/18 下午2:58
 */
public class InTest {

    //in 只有数字类型才能执行 in 操作
    @org.junit.Test
    public void test1() throws Exception {
        ExpressRunner runner = new ExpressRunner(true, false);
        String express = "";
        express = "2 in (2,3) ";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        Object result = runner.execute(express, context, null, false, false);
        System.out.println(result);//true
    }


    //in 只有数字类型才能执行 in 操作
    @org.junit.Test
    public void test2() throws Exception {
        ExpressRunner runner = new ExpressRunner(true, false);
        String express = "";
        express = "2 in a";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        //-------------------------------------//
        int[] a = {1,2,3};
        context.put("a", a);
        //-------------------------------------//
        Object result = runner.execute(express, context, null, false, false);
        System.out.println(result);//true
    }

}