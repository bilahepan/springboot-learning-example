/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package demo.springboot.thread.exercise.bstree;

/**
 * 
 * @author gao tianci
 * @version $Id: BSTree.java, v 0.1 2017年10月14日 下午3:29:40 gao tianci Exp $
 */
public class BSTree<T extends Comparable<T>> {

    //根节点
    private BSTNode<T> root;

    /**
     * Getter method for property <tt>root</tt>.
     * 
     * @return property value of root
     */
    public BSTNode<T> getRoot() {
        return root;
    }

    /**
     * Setter method for property <tt>root</tt>.
     * 
     * @param root value to be assigned to property root
     */
    public void setRoot(BSTNode<T> root) {
        this.root = root;
    }

    //----------------------------------------------//
    //节点类型声明
    //----------------------------------------------//
    @SuppressWarnings("hiding")
    public class BSTNode<T extends Comparable<T>> {
        T          key;   //节点键值
        BSTNode<T> left;  //左孩子
        BSTNode<T> right; //右孩子
        BSTNode<T> parent;//父节点

        public BSTNode(T key, BSTNode<T> left, BSTNode<T> right, BSTNode<T> parent) {
            super();
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public T getKey() {
            return key;
        }

        @Override
        public String toString() {
            return key.toString();
        }

    }

    /**
     * 构造器，空树
     */
    public BSTree() {
        root = null;
    }

    //-------------------遍历[=>]-------------------

    /*
     * 前序遍历"二叉树"-根->左->右
     */
    public void preIterator(BSTNode<T> tree) {
        if (tree != null) {
            System.out.print("【" + tree.key + "】");
            preIterator(tree.left);
            preIterator(tree.right);
        }
        return;
    }

    /*
     * 中序遍历"二叉树"-左->根->右
     */
    public void midIterator(BSTNode<T> tree) {
        if (tree != null) {
            midIterator(tree.left);
            System.out.print("【" + tree.key + "】");
            midIterator(tree.right);
        }
        return;
    }

    /*
     * 后序遍历"二叉树"-左->右->根
     */
    public void offIterator(BSTNode<T> tree) {
        if (tree != null) {
            offIterator(tree.left);
            offIterator(tree.right);
            System.out.print("【" + tree.key + "】");
        }
        return;
    }
    //-------------------遍历[<=]-------------------

    //*******************查找[=>]*******************
    /*
     * 查找"二叉树tree"中键值为key的节点
     */
    public BSTNode<T> search(BSTNode<T> tree, T key) {
        if (tree == null) {
            return tree;
        }
        int flag = key.compareTo(tree.key);
        if (flag < 0) {
            return search(tree.left, key);
        } else if (flag > 0) {
            return search(tree.right, key);
        } else {
            return tree;
        }
    }

    /* 
     * 查找最小结点：返回tree为根结点的二叉树的最小结点。
     */
    public BSTNode<T> findMin(BSTNode<T> tree) {
        if (tree == null) {
            return null;
        }
        //一直向左遍历查找
        while (tree.left != null) {
            tree = tree.left;
        }
        return tree;
    }

    /* 
     * 查找最大结点：返回tree为根结点的二叉树的最大结点。
     */
    public BSTNode<T> findMax(BSTNode<T> tree) {
        if (tree == null) {
            return null;
        }
        //一直向右遍历查找
        while (tree.right != null) {
            tree = tree.right;
        }
        return tree;
    }
    //*******************查找[<=]*******************

    //===================操作[=>]===================
    /**
     * 新建结点(key为键值)，并将其插入到二叉树中
     * @param key
     */
    public void insert(T key) {
        //新建一个节点
        BSTNode<T> node = new BSTNode<T>(key, null, null, null);

        int flag;
        BSTNode<T> parentNode = null;
        BSTNode<T> x = this.root;

        // 查找node的插入位置
        while (x != null) {
            parentNode = x;
            flag = node.key.compareTo(x.key);
            if (flag < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        //找到待插入节点的父节点
        node.parent = parentNode;
        //插入节点
        if (parentNode == null) {
            this.root = node;
        } else {
            flag = node.key.compareTo(parentNode.key);
            if (flag < 0)
                parentNode.left = node;
            else
                parentNode.right = node;
        }
    }

    //===================操作[<=]===================

    //~~~~~~~~~~~~~~~~~~~删除[=>]~~~~~~~~~~~~~~~~~~~
    /**
     * 删除节点
     * 删除二叉树tree,中键为key的结点
     * 
     * 情况一 如果节点是单独的叶子节点，则直接删除即可
     * 情况二 如果节点有一个儿子，则该节点可以在其父节点作调整后(即调整该节点的父节点和该节点的儿子节点的关系为父子关系)，进行删除
     * 情况三 该节点有两个子节点，删除策略是，先找到其右子树的最小的节点rMinNode,用rMinNode的key替代待删除节点的key，然后如情况二删除rMinNode原始位置节点
     * @param tree
     * @param key
     * @return 
     */
    public void remove(BSTNode<T> tree, T key) {
        BSTNode<T> node = search(tree, key);
        //真正删除节点的子树,代表左子树或右子树
        BSTNode<T> childTree = null;
        //真正删除的节点
        BSTNode<T> removeNode = null;
        //双儿子时，右子树的最小节点
        BSTNode<T> rMinNode = null;

        //获取真正删除节点;有一个或0个儿子时，真正删除节点是其自身;有两个儿子时，真正删除节点是其右子树中的最小结点
        if ((node.left == null) || (node.right == null)) {
            removeNode = node;
        } else {
            rMinNode = findMin(node.right);
            removeNode = rMinNode;
        }

        //获取真正删除节点的子树
        if (removeNode.left != null) {
            childTree = removeNode.left;
        } else {
            childTree = removeNode.right;
        }

        //删除 真正删除节点 
        if (childTree != null)
            childTree.parent = removeNode.parent;

        //删除之后，把子树连接起来
        if (removeNode.parent == null) {
            this.root = childTree;
        } else if (removeNode == removeNode.parent.left) {
            removeNode.parent.left = childTree;
        } else {
            removeNode.parent.right = childTree;
        }
        //针对情况三的删除，做值替换
        if (removeNode != node) {
            node.key = removeNode.key;
        }
    }
    //~~~~~~~~~~~~~~~~~~~删除[<=]~~~~~~~~~~~~~~~~~~~
}
