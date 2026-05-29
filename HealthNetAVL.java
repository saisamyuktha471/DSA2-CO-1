class Node {
    int id;
    String name;
    Node left, right;
    int height;

    Node(int id, String name) {
        this.id = id;
        this.name = name;
        this.height = 1;
    }
}

class AVLTree {
    Node root;

    int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    Node insert(Node node, int id, String name) {
        if (node == null)
            return new Node(id, name);

        if (id < node.id)
            node.left = insert(node.left, id, name);
        else if (id > node.id)
            node.right = insert(node.right, id, name);
        else
            return node;

        node.height = 1 + max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL
        if (balance > 1 && id < node.left.id)
            return rightRotate(node);

        // RR
        if (balance < -1 && id > node.right.id)
            return leftRotate(node);

        // LR
        if (balance > 1 && id > node.left.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balance < -1 && id < node.right.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // 🌳 CONNECTED TREE DISPLAY
    void printTree(Node root, String prefix, boolean isTail) {
        if (root != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ")
                    + root.id + " (" + root.name + ")");

            if (root.left != null || root.right != null) {
                if (root.right != null)
                    printTree(root.right, prefix + (isTail ? "    " : "│   "), false);
                if (root.left != null)
                    printTree(root.left, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }

    // 🔽 INORDER TRAVERSAL
    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.id + " (" + root.name + ")  ");
            inorder(root.right);
        }
    }
}

public class HealthNetAVL {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        tree.root = tree.insert(tree.root, 30, "Ravi");
        tree.root = tree.insert(tree.root, 10, "Anita");
        tree.root = tree.insert(tree.root, 20, "Kiran");
        tree.root = tree.insert(tree.root, 40, "Meena");
        tree.root = tree.insert(tree.root, 50, "John");
        tree.root = tree.insert(tree.root, 25, "Sara");

        System.out.println("🌳 Final AVL Tree Structure:\n");
        tree.printTree(tree.root, "", true);

        System.out.println("\n\n🔽 Inorder Traversal (Sorted Order):\n");
        tree.inorder(tree.root);
    }
}