package demo.springboot.qlexpress;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

/**
 *
 *@author: bilahepan
 *@date: 2018/12/18 上午10:40
 */
public class ForTest {

    //for循环
    @org.junit.Test
    public  void testFor() throws Exception{
        String express = "r = 0;"
                +"for(int i = 1;i <=3; i++)"
                +"{"
                 +"r = r+i;"
                +"}"
                +"return r;";

        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        Object result = runner.execute(express,context, null, false,false);
        System.out.println(result);
    }


    //for循环嵌套
    @org.junit.Test
    public void testForLoop2() throws Exception {
        ExpressRunner runner = new ExpressRunner(true, false);
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        Object result =   runner.execute(
                "sum=0;for(i=0;i<10;i=i+1){for(j=0;j<10;j++){sum=sum+i+j;}} return sum;",
                context, null, false, false);
        System.out.println(result);
    }
}