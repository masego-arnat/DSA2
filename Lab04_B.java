public class Lab04_B {
    // Node class for the binary tree
    static class Node {
        int data;
        Node left, right;

        Node(int item) {
            data = item;
            left = right = null;
        }
    }

    // Root of the binary tree
    Node root;

    // Function to perform postorder traversal
    void postOrder(Node node) {
        if (node == null) {
            return;
        }
        // Traverse left subtree
        postOrder(node.left);
        // Traverse right subtree
        postOrder(node.right);
        // Visit the node
        System.out.print(node.data + " ");
    }

    // Main method to run the program
    public static void main(String[] args) {
        Lab04_B tree = new Lab04_B();
        
        // Manually creating the binary tree
        tree.root = new Node(2);
        tree.root.left = new Node(1);
        tree.root.right = new Node(3);
        tree.root.right.right = new Node(6);
        tree.root.right.right.left = new Node(5);
        tree.root.right.right.right = new Node(7);

        System.out.print("Postorder List is: ");
        tree.postOrder(tree.root);
    }
}
