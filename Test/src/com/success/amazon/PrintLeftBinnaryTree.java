package com.success.amazon;

public class PrintLeftBinnaryTree {

  public static void main(String[] args) {
    Node<String> main = new Node<>();
    populateNodes(main, 4, "1");
    System.out.println(main);
    printLeftNode(main);
  }

  static void populateNodes(Node<String> node, int depth, String val) {
    if (depth > 0) {
      node.value = val;
      node.left = new Node<>();
      node.right = new Node<>();
      populateNodes(node.left, depth - 1, "L" + String.valueOf(depth));
      populateNodes(node.right, depth - 1, "R" + String.valueOf(depth));
    }
  }

  static void printLeftNode(Node<String> node) {
    if(node!=null && node.value!=null) {
      System.out.println("left value is "+node.value);
      printLeftNode(node.left);
//      printLeftNode(node.right);
    }
  }
}



class Node<T> {
  Node<T> left;
  Node<T> right;
  T value;

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    if (value != null) {
      s.append(value.toString());
    }
    if (left != null) {
      s.append(left.toString());
    }
    if (right != null) {
      s.append(right.toString());
    }
    return s.toString();
  }
}
