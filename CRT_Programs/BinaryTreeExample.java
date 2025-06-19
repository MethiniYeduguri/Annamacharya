
package xyz;

class TreeNode {
    String name;
    TreeNode left, right;

    TreeNode(String name) {
        this.name = name;
    }
}

public class BinaryTreeExample {
    TreeNode root;

    void createTree() {
        root = new TreeNode("Mom");
        root.left = new TreeNode("Dad");
        root.right = new TreeNode("You");
    }

    void printRoot() {
        System.out.println("Root: " + root.name);
    }

    void printChildren(TreeNode node) {
        System.out.println("Left of " + node.name + ": " + (node.left != null ? node.left.name : "null"));
        System.out.println("Right of " + node.name + ": " + (node.right != null ? node.right.name : "null"));
    }

    void inorder(TreeNode node) {
        if (node == null) return;
        inorder(node.left);
        System.out.print(node.name + " ");
        inorder(node.right);
    }

    int countNodes(TreeNode node) {
        return (node == null) ? 0 : 1 + countNodes(node.left) + countNodes(node.right);
    }

    int countLeafNodes(TreeNode node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;
        return countLeafNodes(node.left) + countLeafNodes(node.right);
    }

    TreeNode mirror(TreeNode node) {
        if (node == null) return null;
        TreeNode left = mirror(node.left);
        TreeNode right = mirror(node.right);
        node.left = right;
        node.right = left;
        return node;
    }

    void printPaths(TreeNode node, String path) {
        if (node == null) return;
        path += node.name + " ";
        if (node.left == null && node.right == null)
            System.out.println("Path: " + path);
        else {
            printPaths(node.left, path);
            printPaths(node.right, path);
        }
    }

    boolean isEmpty() {
        return root == null;
    }

    public static void main(String[] args) {
        BinaryTreeExample bt = new BinaryTreeExample();
        bt.createTree();
        bt.printRoot();
        bt.printChildren(bt.root);
        System.out.print("In-order: ");
        bt.inorder(bt.root);
        System.out.println("\nTotal nodes: " + bt.countNodes(bt.root));
        System.out.println("Leaf nodes: " + bt.countLeafNodes(bt.root));
        bt.root = bt.mirror(bt.root);
        System.out.print("In-order after mirror: ");
        bt.inorder(bt.root);
        System.out.println("\nPaths from root to leaves:");
        bt.printPaths(bt.root, "");
        System.out.println("Is tree empty? " + bt.isEmpty());
    }
}
