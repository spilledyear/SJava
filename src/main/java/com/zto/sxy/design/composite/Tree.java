package com.zto.sxy.design.composite;

/**
 * Created by spilledyear on 2017/9/6.
 *
 * 不知道咋整这个模式，在处理树的时候要用是吧？或者说要处理一类东西的时候，比如文件、图像、文件夹，将这些统一看作一类来处理
 *
 * 组合模式
 * 组合模式有时又叫部分-整体模式在处理类似树形结构的问题时比较方便
 */
public class Tree {
    TreeNode root = null;

    public Tree(String name){
        root = new TreeNode(name);
    }


    public static void main(String[] args) {
        System.out.println();
        Tree tree = new Tree("A");
        TreeNode nodeB = new TreeNode("B");
        TreeNode nodeC = new TreeNode("C");

        nodeB.add(nodeC);
        tree.root.add(nodeB);
        System.out.println("build the tree finished");
    }
}
