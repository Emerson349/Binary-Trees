package binaryTreeAvl;

public class BinaryTreeAVL {
    private Node root;

    public BinaryTreeAVL() {
        this.root = null;
    }

    private int getHeight(Node node) {
        return (node == null) ? 0 : node.getHeight();
    }

    public void insert(int value) {
        root = insertRec(value, root);
    }
    
    public void remove(int value) {
        root = removeRec(value, root);
    }

    private void updateHeight(Node node) {
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
    }

    private int getBalanceFactor(Node node) {
        return (node == null) ? 0 : (getHeight(node.getLeft()) - getHeight(node.getRight()));
    }

    private Node insertRec(int value, Node root) {
        if (root == null) return new Node(value);
        if (value < root.getValue())
            root.setLeft(insertRec(value, root.getLeft()));
        else if (value > root.getValue())
            root.setRight(insertRec(value, root.getRight()));
        else
            return root;

        updateHeight(root);
        int balanceFactor = getBalanceFactor(root);

        if (balanceFactor > 1) {
            if (value < root.getLeft().getValue()) {
                return rotateRight(root);
            }
            else if (value > root.getLeft().getValue()) {
                root.setLeft(rotateLeft(root.getLeft()));
                return rotateRight(root);
            }
        }
        else if (balanceFactor < -1) {
            if (value > root.getRight().getValue()) {
                return rotateLeft(root);
            }
            else if (value < root.getRight().getValue()) {
                root.setRight(rotateRight(root.getRight()));
                return rotateLeft(root);
            }
        }

        return root;
    }

    private Node removeRec(int value, Node root) {
        if (root == null) return null;
        if (value < root.getValue()) {
            root.setLeft(removeRec(value, root.getLeft()));
        } else if (value > root.getValue()) {
            root.setRight(removeRec(value, root.getRight()));
        }
        else {
            if (root.getLeft() == null && root.getRight() == null) {
                return null;
            }

            if (root.getLeft() == null && root.getRight() != null) {
                return root.getRight();
            } else if (root.getRight() == null && root.getLeft() != null) {
                return root.getLeft();
            }

            Node temp = root.getRight();
            while (temp.getLeft() != null) {
                temp = temp.getLeft();
            }
            root.setValue(temp.getValue());
            root.setRight(removeRec(temp.getValue(), root.getRight()));
        }

        updateHeight(root);
        int balanceFactor = getBalanceFactor(root);

        if (balanceFactor > 1) {
            if (getBalanceFactor(root.getLeft()) >= 0) {
                return rotateRight(root);
            } else {
                root.setLeft(rotateLeft(root.getLeft()));
                return rotateRight(root);
            }
        }
        else if (balanceFactor < -1) {
            if (getBalanceFactor(root.getRight()) >= 0) {
                return rotateLeft(root);
            }
            else {
                root.setRight(rotateRight(root.getRight()));
                return rotateLeft(root);
            }
        }

        return root;
    }

    private Node rotateLeft(Node root) {
        Node newRoot = root.getRight();
        Node subTree = newRoot.getLeft();

        newRoot.setLeft(root);
        root.setRight(subTree);

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }

    private Node rotateRight(Node root) {
        Node newRoot = root.getLeft();
        Node subTree = newRoot.getRight();

        newRoot.setRight(root);
        root.setLeft(subTree);

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
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
