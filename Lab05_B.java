public class Lab05_B {
    // Node class for AVL Tree
    class Node {
        int key;
        Node left, right;
        int height;

        Node(int d) {
            key = d;
            height = 1; // new node is initially added at leaf
        }
    }

    Node root;

    // Function to get the height of the tree
    int height(Node N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // Function to right rotate subtree rooted with y
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // Function to left rotate subtree rooted with x
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get balance factor of node N
    int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    // Recursive function to insert a key in the subtree rooted with node
    Node insert(Node node, int key) {
        // 1. Perform the normal BST insert
        if (node == null) {
            return (new Node(key));
        }
        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else // Duplicate keys are not allowed
            return node;

        // 2. Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // 3. Get the balance factor of this ancestor node to check whether
        // this node became unbalanced
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // return the (unchanged) node pointer
        return node;
    }

    // Function to do preorder traversal of the tree
    void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    // Function to delete a node
    Node deleteNode(Node root, int key) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null) return root;

        if (key < root.key)
            root.left = deleteNode(root.left, key);
        else if (key > root.key)
            root.right = deleteNode(root.right, key);
        else {
            // node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
                Node temp = root.left != null ? root.left : root.right;

                // No child case
                if (temp == null) {
                    return null;
                } else // One child case
                    return temp;
            } else {
                // node with two children: Get the inorder successor (smallest
                // in the right subtree)
                Node temp = minValueNode(root.right);

                // Copy the inorder successor's content to this node
                root.key = temp.key;

                // Delete the inorder successor
                root.right = deleteNode(root.right, temp.key);
            }
        }

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Function to find the node with minimum value
    Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    public static void main(String[] args) {
        Lab05_B tree = new Lab05_B();

        // Inserting elements
        int[] elements = {3, 1, 5, 0, 2, 4, 6};
        for (int element : elements) {
            tree.root = tree.insert(tree.root, element);
        }

        // Preorder traversal before deletion
        System.out.println("Preorder traversal is :");
        tree.preOrder(tree.root);
        System.out.println();

        // Deleting elements
        int[] toDelete = {6, 5, 4};
        for (int key : toDelete) {
            tree.root = tree.deleteNode(tree.root, key);
        }

        // Preorder traversal after deletion
        System.out.println("Preorder traversal after deletion of [6,5,4] :");
        tree.preOrder(tree.root);
        System.out.println();
    }
}
