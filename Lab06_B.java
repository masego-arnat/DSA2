public class Lab06_B {
    // Node class for Red-Black Tree
    private class Node {
        int data;
        Node left, right, parent;
        boolean color; // true for Red, false for Black

        Node(int data) {
            this.data = data;
            left = right = parent = null;
            color = true; // New nodes are red by default
        }
    }

    private Node root;
    private Node TNULL;

    // Preorder
    private void preOrderHelper(Node node) {
        if (node != TNULL) {
            System.out.print(node.data + " ");
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
    }

    // Inorder
    private void inOrderHelper(Node node) {
        if (node != TNULL) {
            inOrderHelper(node.left);
            System.out.print(node.data + " ");
            inOrderHelper(node.right);
        }
    }

    // Postorder
    private void postOrderHelper(Node node) {
        if (node != TNULL) {
            postOrderHelper(node.left);
            postOrderHelper(node.right);
            System.out.print(node.data + " ");
        }
    }

    // Balance the tree after deletion of a node
    private void fixInsert(Node k) {
        Node u;
        while (k.parent.color) {
            if (k.parent == k.parent.parent.left) {
                u = k.parent.parent.right;
                if (u.color) {
                    // case 3.1
                    u.color = false;
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        // case 3.2.2
                        k = k.parent;
                        leftRotate(k);
                    }
                    // case 3.2.1
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    rightRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.left;
                if (u.color) {
                    // mirror case 3.1
                    u.color = false;
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        // mirror case 3.2.2
                        k = k.parent;
                        rightRotate(k);
                    }
                    // mirror case 3.2.1
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    leftRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = false;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public Lab06_B() {
        TNULL = new Node(0);
        TNULL.color = false;
        root = TNULL;
    }

    public void insert(int key) {
        Node node = new Node(key);
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = true; // New node must be red

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data < y.data) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = false;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }

    public void preOrder() {
        preOrderHelper(this.root);
    }

    public void inOrder() {
        inOrderHelper(this.root);
    }

    public void postOrder() {
        postOrderHelper(this.root);
    }

    public static void main(String[] args) {
        Lab06_B bst = new Lab06_B();
        bst.insert(55);
        bst.insert(40);
        bst.insert(65);
        bst.insert(60);
        bst.insert(57);
        bst.insert(75);

        System.out.print("Preorder traversal: ");
        bst.preOrder();
        System.out.println();

        System.out.print("Inorder traversal: ");
        bst.inOrder();
        System.out.println();

        System.out.print("Postorder traversal: ");
        bst.postOrder();
        System.out.println();
    }
}
