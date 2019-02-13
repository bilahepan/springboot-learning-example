package demo.springboot.qlexpress;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.Test;

/**
 *Map测试
 *@author: 文若[gaotc@tuya.com]
 *@date: 2018/12/18 上午10:30
 */
public class MapTest {

    //map
    @Test
    public void test1() throws Exception {
        String express = "Map abc = NewMap(1:1,2:2); return abc.get(1) + abc.get(2)";
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        //
        String result = runner.execute(express, context, null, false, false).toString();

        System.out.println(result);//3
    }



    //map
    @Test
    public void test2() throws Exception {
        String express = "Map abc = NewMap(\"a\":1,\"b\":2); return abc.a + abc.b";
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        //
        String result = runner.execute(express, context, null, false, false).toString();

        System.out.println(result);//3
    }



}