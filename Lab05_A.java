class Lab05_A {
    int key;
    Lab05_A left, right;
    int height;

    public Lab05_A(int d) {
        key = d;
        left = right = null;
        height = 1;
    }
}



class AVLTree {
    Lab05_A root;

    int height(Lab05_A N) {
        if (N == null)
            return 0;
        return N.height;
    }

    int getBalance(Lab05_A N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    Lab05_A rightRotate(Lab05_A y) {
        Lab05_A x = y.left;
        Lab05_A T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Lab05_A leftRotate(Lab05_A x) {
        Lab05_A y = x.right;
        Lab05_A T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Lab05_A insert(Lab05_A node, int key) {
        if (node == null)
            return (new Lab05_A(key));

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && key < node.left.key)
            return rightRotate(node);
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void preOrder(Lab05_A node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    void inOrder(Lab05_A node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    void postOrder(Lab05_A node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.key + " ");
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        int[] elements = {10, 20, 30, 40, 50, 25};
        for (int element : elements) {
            tree.root = tree.insert(tree.root, element);
        }

        System.out.println("Preorder traversal of constructed AVL tree is:");
        tree.preOrder(tree.root);
        System.out.println();

        System.out.println("Inorder traversal of constructed AVL tree is:");
        tree.inOrder(tree.root);
        System.out.println();

        System.out.println("Postorder traversal of constructed AVL tree is:");
        tree.postOrder(tree.root);
        System.out.println();
    }
}
