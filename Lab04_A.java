public class Lab04_A {
    static class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    Node root;

    Lab04_A() {
        root = null;
    }

    void insert(int key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }
        if (key < root.key) {
            root.left = insertRec(root.left, key);
        } else if (key > root.key) {
            root.right = insertRec(root.right, key);
        }
        return root;
    }

    void delete(int key) {
        root = deleteRec(root, key);
    }

    Node deleteRec(Node root, int key) {
        if (root == null) return root;

        if (key < root.key) {
            root.left = deleteRec(root.left, key);
        } else if (key > root.key) {
            root.right = deleteRec(root.right, key);
        } else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    int minValue(Node root) {
        int minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    boolean search(int key) {
        return searchRec(root, key);
    }

    boolean searchRec(Node root, int key) {
        if (root == null) return false;
        if (root.key == key) return true;
        return key < root.key ? searchRec(root.left, key) : searchRec(root.right, key);
    }

    int findMax() {
        return findMaxRec(root);
    }

    int findMaxRec(Node root) {
        if (root.right != null) {
            return findMaxRec(root.right);
        }
        return root.key;
    }

    int findMin() {
        return findMinRec(root);
    }

    int findMinRec(Node root) {
        if (root.left != null) {
            return findMinRec(root.left);
        }
        return root.key;
    }

    public static void main(String[] args) {
        Lab04_A bst = new Lab04_A();
        int[] elements = {7, 5, 9, 3,6};

        System.out.print("Creation/Insertion on a tree the following elements: ");
        for (int element : elements) {
            bst.insert(element);
            System.out.print(element + " ");
        }
        System.out.println();

        bst.delete(6);
        System.out.print("Deletion of 6 gives: ");
        // In-order traversal to display the tree after deletion
        bst.inOrder();

        if (bst.search(5)) {
            System.out.println("Key 5 exists in the list");
        } else {
            System.out.println("Key 5 does not exist in the list");
        }

        System.out.println("Maximum is: " + bst.findMax());
        System.out.println("Minimum is: " + bst.findMin());
    }

    void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    void inOrderRec(Node root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.print(root.key + " ");
            inOrderRec(root.right);
        }
    }
}
