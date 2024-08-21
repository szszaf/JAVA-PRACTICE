package com.webtree;

import com.webtree.WebTree.WebTreeBuilder;

public class Main {
    public static void main(String[] args) {
        WebTreeBuilder wb = new WebTreeBuilder("https://www.premierleague.com/", 20);
        wb.buildWebTree(1);
        wb.print();
    }
}