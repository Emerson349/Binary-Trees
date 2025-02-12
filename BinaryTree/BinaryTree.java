package BinaryTree;

public class BinaryTree {
    private Node root;

    public void insert(int value) {
        root = insertRec(value, root);
    }

    private Node insertRec(int value, Node root) {
        if (root == null) {
            return new Node(value);
        }
        if (value < root.getValue()) {
            root.setLeft(insertRec(value, root.getLeft()));
        }
        if (value > root.getValue()) {
            root.setRight(insertRec(value, root.getRight()));
        }
        return root;
    }

    public Node search(int value) {
        return searchRec(value, root);
    }

    private Node searchRec(int value, Node root) {
        if (root == null) {
            return null;
        }
        if (value < root.getValue()) {
            return searchRec(value, root.getLeft());
        }
        if (value > root.getValue()) {
            return searchRec(value, root.getRight());
        }
        return root;
    }

    public void remove(int value) {
        root = removeRec(value, root);
    }

    private Node removeRec(int value, Node node) {
        if (node == null) {
            return null;
        }
        if (value < node.getValue()) {
            node.setLeft(removeRec(value, node.getLeft()));
        } else if (value > node.getValue()) {
            node.setRight(removeRec(value, node.getRight()));
        }
        else {
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }

            if (node.getLeft() == null && node.getRight() != null) {
                return node.getRight();
            }
            else if (node.getRight() == null && node.getLeft() != null) {
                return node.getLeft();
            }

            Node temp = node.getRight();
            while (temp.getLeft() != null) {
                temp = temp.getLeft();
            }
            node.setValue(temp.getValue());
            node.setRight(removeRec(temp.getValue(), node.getRight()));
        }
        return node;
    }

    public void inOrder() {
        recInOrder(root);
    }

    private void recInOrder(Node root) {
        if (root != null) {
            recInOrder(root.getLeft());
            System.out.println(root.getValue());
            recInOrder(root.getRight());
        }
    }
}
