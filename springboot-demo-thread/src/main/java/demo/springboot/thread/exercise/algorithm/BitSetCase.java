package demo.springboot.thread.exercise.algorithm;

import java.util.BitSet;

/**
 * BitSet顾名思义即Bit的一个集合，每个Bit只能取1或0（True或False)，是存储海量数据一个途径。
 * <p>
 * 而实际上BitSet内部核心是一个long的数组。
 * <p>
 * 由于一个long在java中占用8个字节，即64位。
 */


/**
 * BitSet就是用一个bit来表示一个数据。
 * 平时32位存储一个数据，我们可以换一种想法，用一个字节32位来存储0-31这32个数据，
 * 例如我们对2，1，5，12这四个数据进行由小到大的排序，首先把32位初始化为0，
 * 我们可以把这4个数据存储为 0000 0000 0000 0000 0001 0000 0010 0110
 * <p>
 * 我们就把32位中的分别把 2 1 5 12位置为1，然后从第0位开始遍历，看相应位是否为1，为1就进行输出，就完成了数据从小到大的排序。
 * <p>
 * 优点：既大量节省了空间，又把时间复杂度降低到O(n)。
 * 不足：如果数据过于稀疏就会有大量无用遍历，浪费时间。
 */
public class BitSetCase {
    //10
    public static final int NUM = 64;

    public static void main(String[] args) {
        test0();
        System.out.println("---");
        test1();
    }


    //使用bitSet
    public static void test0() {
        int[] data = {2, 42, 5, 6, 6, 18, 33, 15, 25, 31, 28, 37};
        //默认64，超过集合容量会扩大，从而影响效率，所以初始化时最好按照数组最大值进行初始化
        //默认初始化为false
        BitSet bs = new BitSet(NUM);
        System.out.println("bs.size()--" + bs.size());

        //默认BitSet是false
        for (int i = 0; i < data.length; i++) {
            bs.set(data[i], true);
        }
        for (int i = 0; i < bs.size(); i++) {
            if (bs.get(i)) {
                System.out.println(i);
            }
        }
    }


    //用数组来构建
    public static void test1() {
        //默认数组元素初始化为0
        int[] input = new int[NUM];
        int[] data = {2, 42, 5, 6, 6, 18, 33, 15, 25, 31, 28, 37};
        for (int num : data) {
            //默认初始化为0，有值设置为1
            input[num] = 1;
        }
        for (int i = 0; i < NUM; i++) {
            if (input[i] == 1) {
                System.out.println(i);
            }
        }
    }

}
