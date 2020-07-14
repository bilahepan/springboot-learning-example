package demo.springboot.thread.exercise.beibao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestCase {
    public static void main(String[] args) {
        //
        int resCount = 1000;
        List<ItemPackageVO> packageList = new ArrayList() {{
            //批次越小，规格越小
            //满足为1000的订单
//            add(new ItemPackageVO("b", 30, 10, 101));
//            add(new ItemPackageVO("f", 10, 20, 101));
//
//            add(new ItemPackageVO("e", 200, 2, 101));
//            add(new ItemPackageVO("g", 1200, 1, 101));
//            add(new ItemPackageVO("a", 10, 20, 201));
//            //
//            add(new ItemPackageVO("c", 50, 5, 301));
//            add(new ItemPackageVO("d", 100, 5, 401));

            add(new ItemPackageVO("a", 10, 20, 201));
            add(new ItemPackageVO("b", 30, 10, 101));
            add(new ItemPackageVO("c", 50, 5, 301));
            add(new ItemPackageVO("d", 100, 5, 401));
            add(new ItemPackageVO("e", 200, 2, 101));
            add(new ItemPackageVO("f", 10, 20, 101));
            add(new ItemPackageVO("g", 1200, 1, 101));
            //

        }};





//        for (ItemPackageVO item : packageList) {
//            System.out.println(item.getSn());
//        }
//        //
//        System.out.println("---");
//        //
//        packageList.remove(0);
//        for (ItemPackageVO item : packageList) {
//            System.out.println(item.getSn());
//        }

        //排序
        //把所有的组合都找出来，对组合排序筛选

    }

    //public class EggComparator implements Comparator<Egg> {
    //
    //    /**
    //     *  先按名字排序，名字相同按价格排序
    //     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
    //     */
    //    @Override
    //    public int compare(Egg one, Egg another) {
    //        int flag = one.getName().compareTo(another.getName());
    //        //名字相同比价格
    //        if (flag == 0) {
    //            return one.getPrice().compareTo(another.getPrice());
    //        }
    //        return flag;
    //    }
    //}


    /***/
    public class bsComaprator implements Comparator<ItemPackageVO> {

        //先比批次
        //批次相同比规格
        @Override
        public int compare(ItemPackageVO one, ItemPackageVO another) {
            int flag = one.getBatch() - another.getBatch();
            //批次相同比规格
            if (flag == 0) {
                return one.getSpec() - another.getSpec();
            }
            return flag;
        }
    }
}
