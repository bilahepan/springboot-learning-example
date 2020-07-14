/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package demo.springboot.thread.exercise.bstree;


/**
 * @author gao tianci
 * @version $Id: BSTreeTest.java, v 0.1 2017年10月12日 下午10:54:17 gao tianci Exp $
 */
public class BSTreeTest {
    private static final int[] arr = {7, 4, 9, 2, 6, 1, 3, 5, 8, 10};

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        //构造空树
        BSTree<Integer> tree = new BSTree<Integer>();
        System.out.print("  依次添加: ");
        //添加元素
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
            tree.insert(arr[i]);
        }

        //遍历
        System.out.print("\n\n  前序遍历: ");
        tree.preIterator(tree.getRoot());

        System.out.print("\n  中序遍历: ");
        tree.midIterator(tree.getRoot());

        System.out.print("\n  后序遍历: ");
        tree.offIterator(tree.getRoot());

        //findMin/findMax
        System.out.println("\n\n 最小值 :" + tree.findMin(tree.getRoot()));
        System.out.println(" 最大值 :" + tree.findMax(tree.getRoot()));

        //删除节点
        tree.remove(tree.getRoot(), 9);
        System.out.print("\n 中序遍历: ");
        tree.midIterator(tree.getRoot());

        //删除后再进行添加
        tree.insert(9);
        System.out.print("\n 中序遍历: ");
        tree.midIterator(tree.getRoot());

    }

}
