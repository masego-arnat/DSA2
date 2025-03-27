class RedBlackTree {
    private static class Node {
        int data;
        Node left, right, parent;
        boolean color; // true for Red, false for Black

        Node(int data) {
            this.data = data;
            this.color = true; // New nodes are always red
        }
    }

    private Node root;
    private final Node TNULL = new Node(0);

    public RedBlackTree() {
        TNULL.color = false;
        root = TNULL;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
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
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    private void deleteFixup(Node x) {
        while (x != root && x.color == false) {
            if (x == x.parent.left) {
                Node s = x.parent.right;
                if (s.color) {
                    s.color = false;
                    x.parent.color = true;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }
                if (!s.left.color && !s.right.color) {
                    s.color = true;
                    x = x.parent;
                } else {
                    if (!s.right.color) {
                        s.left.color = false;
                        s.color = true;
                        rightRotate(s);
                        s = x.parent.right;
                    }
                    s.color = x.parent.color;
                    x.parent.color = false;
                    s.right.color = false;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node s = x.parent.left;
                if (s.color) {
                    s.color = false;
                    x.parent.color = true;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }
                if (!s.left.color && !s.right.color) {
                    s.color = true;
                    x = x.parent;
                } else {
                    if (!s.left.color) {
                        s.right.color = false;
                        s.color = true;
                        leftRotate(s);
                        s = x.parent.left;
                    }
                    s.color = x.parent.color;
                    x.parent.color = false;
                    s.left.color = false;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = false;
    }

    private void rbTransplant(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    public void deleteNode(int data) {
        Node z = root, x, y;
        while (z != TNULL) {
            if (z.data == data) break;
            z = (data < z.data) ? z.left : z.right;
        }
        if (z == TNULL) return;
        
        y = z;
        boolean yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (!yOriginalColor) {
            deleteFixup(x);
        }
    }

    private void inorderHelper(Node node) {
        if (node != TNULL) {
            inorderHelper(node.left);
            System.out.print(node.data + " ");
            inorderHelper(node.right);
        }
    }

    public void inorder() {
        inorderHelper(root);
        System.out.println();
    }

    public void levelOrder() {
        if (root == TNULL) return;
        java.util.Queue<Node> queue = new java.util.LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            System.out.print(temp.data + " ");
            if (temp.left != TNULL) queue.add(temp.left);
            if (temp.right != TNULL) queue.add(temp.right);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        int[] values = {10, 7, 18, 3, 8, 11, 22, 2, 6, 13, 26};
        for (int value : values) {
            rbt.deleteNode(value); // No insertion method, for testing deletion only
        }
        
        System.out.println("Inorder:");
        rbt.inorder();
        System.out.println("Level order:");
        rbt.levelOrder();
        
        int[] deletions = {18, 11, 3, 10, 22};
        for (int value : deletions) {
            rbt.deleteNode(value);
        }
        
        System.out.println("Inorder:");
        rbt.inorder();
        System.out.println("Level order:");
        rbt.levelOrder();
    }
}
