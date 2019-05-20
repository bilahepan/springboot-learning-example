package demo.springboot.qlexpress;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.Test;

/**
 * 数组操作-测试
 *
 * @author: bilahepan
 * @date: 2018/12/18 上午10:18
 */
public class ArrayListTest {


    //数组的简单测试
    @Test
    public void test0() throws Exception {
        String express = "List abc = NewList(1,2,3); return abc.get(1)";
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        //
        String result = runner.execute(express, context, null, false, false).toString();

        System.out.println(result);//2
    }


    //数组的简单测试
    @Test
    public void test1() throws Exception {
        String express = "int[] abc = [1,2,3];"
                + "return abc[2]";
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        //
        String result = runner.execute(express, context, null, false, false).toString();

        System.out.println(result);
    }



    //字符串数组
    @Test
    public void test2() throws Exception {
        String express = "String[] abc = [\"xuannan\",\"qianghui\"];return abc[1]";
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        //
        String result = runner.execute(express, context, null, false, false).toString();

        System.out.println(result);
    }




    //数组长度
    @Test
    public void test3() throws Exception {
        String express = "String[] abc = [\"xuannan\",\"qianghui\"];return abc.length";
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        //
        String result = runner.execute(express, context, null, false, false).toString();

        System.out.println(result);
    }


}