package demo.springboot.qlexpress;

import com.ql.util.express.DefaultContext;
import org.junit.Assert;

import com.ql.util.express.ExpressRunner;
import org.junit.Test;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/12/17 下午5:17
 */
public class AClassDefine {

    //简单表达式计算
    @org.junit.Test
    public void testDemo() throws Exception {
        String express = "10 * 10 + 1 + 2 * 3 + 5 * 2";
        ExpressRunner runner = new ExpressRunner();
        Object r = runner.execute(express, null, null, false, false);
        Assert.assertTrue("表达式计算", r.toString().equalsIgnoreCase("117"));
        System.out.println("表达式计算：" + express + " = " + r);
    }


    //定义一个类，并进行简单操作
    @Test
    public void testNewVClass() throws Exception {
        String express = "" +
                "int a = 100;" +
                "class ABC(){" +
                " int a = 200;" +
                //" println a;" +
                "}" +
                "ABC  abc = new ABC();" +
                "return a + abc.a;";
        ExpressRunner runner = new ExpressRunner(false, false);
        DefaultContext<String, Object> context = new DefaultContext<>();
        runner.loadMutilExpress("ClassTest", express);

        Object r = runner.executeByExpressName("ClassTest", context, null, false, false, null);
        System.out.println(r);
    }



    //定义一个类，并进行简单操作
    @org.junit.Test
    public void testVirtualClass() throws Exception {
        ExpressRunner runner = new ExpressRunner(false, false);
        runner.loadMutilExpress("类初始化", "class People(){sex;height;money;skin};");
        runner.loadMutilExpress("创建小强", "a = new People();a.sex='male';a.height=185;a.money=10000000;");
        runner.loadMutilExpress("体检", "if(a.sex=='male' && a.height>180 && a.money>5000000) return '高富帅，鉴定完毕'");
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();

        Object r = runner.execute("类初始化;创建小强;体检", context, null, false, false);
        System.out.println(r);
    }

}