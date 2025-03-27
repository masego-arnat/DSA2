public class Lab06_B_old {
    // Node class for the binary search tree
    static class Node {
        int value;
        Node left, right;

        Node(int item) {
            value = item;
            left = right = null;
        }
    }

    // Root of the BST
    Node root;

    // Method to insert a new value in the BST
    void insert(int value) {
        root = insertRec(root, value);
        inorder(); // Print inorder traversal after each insertion
        System.out.println(); // New line after each traversal output
    }

    // Recursive function to insert a new value
    Node insertRec(Node root, int value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    // Method for inorder traversal of the BST
    void inorder() {
        inorderRec(root);
    }

    // Recursive function for inorder traversal
    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.value + " ");
            inorderRec(root.right);
        }
    }

  

    public static void main(String[] args) {
        Lab06_B_old tree = new Lab06_B_old();

        // Sample input values in the order to match the output
        int[] values = {1, 4, 6, 3, 5, 7, 8, 2, 9};

        // Insert values into the tree
        for (int value : values) {
            tree.insert(value);
        }

     
    }
}
