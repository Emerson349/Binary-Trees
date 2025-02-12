package rbTree;

public class RbTree {
    private Node root;
    private Node nil;

    public RbTree(int value) {
        // Na RB tree precisamos considerar nós "NIL" para garantir que todas folhas possuam nós válidos
        nil = new Node(0);
        nil.setColor(false);
        root = nil;
    }

    public void insert(int value) {
        Node newNode = new Node(value);
        newNode.setLeft(nil);
        newNode.setRight(nil);
        newNode.setFather(null);

        root = insertRec(newNode, root);
        balanceTree(newNode);
    }

    private Node insertRec(Node newNode, Node root) {
        if (root == nil) return newNode;
        if (newNode.getValue() < root.getValue()) {
            root.setLeft(insertRec(newNode, root.getLeft()));
            root.getLeft().setFather(root);
        }
        else if (newNode.getValue() > root.getValue()) {
            root.setRight(insertRec(newNode, root.getRight()));
            root.getRight().setFather(root);
        }
        else
            return root;
        return root;
    }

    private void balanceTree(Node newNode) {
        Node father;
        Node parent;

        while (newNode.getFather() != null && newNode.getFather().getColor()) {
            father = newNode.getFather();
            parent = father.getFather();

            if (father.equals(parent.getRight())) {
                Node uncle = parent.getLeft();

                if (uncle.getColor()) {
                    uncle.setColor(false);
                    father.setColor(false);
                    parent.setColor(true);
                    newNode = parent;
                }
                else {
                    if (newNode.equals(parent.getLeft())) {
                       newNode = father;
                       rotateRight(parent);
                    }
                    father.setColor(false);
                    parent.setColor(true);
                    rotateLeft(parent);
                }

            }
            else {
                Node uncle = parent.getRight();
                if (uncle.getColor()) {
                    uncle.setColor(false);
                    father.setColor(false);
                    parent.setColor(true);
                    newNode = parent;
                }
                else {
                    if (newNode.equals(parent.getRight())) {
                        newNode = father;
                        rotateLeft(parent);
                    }
                    father.setColor(false);
                    parent.setColor(true);
                    rotateRight(parent);
                }
            }
        }
        root.setColor(false);
    }

    private Node searchNode(int value, Node node) {
        if (node == nil) return nil;
        if (value == node.getValue())
            return node;
        if (value < node.getValue())
            return searchNode(value, node.getLeft());
        return searchNode(value, node.getRight());
    }

    public void remove(int value) {
        Node nodeToRemove = searchNode(value, root);
        if (nodeToRemove == nil) {
            return;
        }
        deleteNode(nodeToRemove);
    }

    private void deleteNode(Node node) {
        Node x, temp = node;
        boolean tempColor = temp.getColor();
        if (node.getLeft() == nil) {
            x = node.getRight();
            transplant(node, node.getRight());
        } else if (node.getRight() == nil) {
            x = node.getLeft();
            transplant(node, node.getLeft());
        }
        else {
            temp = successor(node.getRight());
            tempColor = temp.getColor();
            x = temp.getRight();
        }
    }

    private Node successor(Node node) {
        while (node.getLeft() != nil) {
            node = node.getLeft();
        }
        return node;
    }

    private void transplant(Node x, Node y) {
        if (x.getFather() == null) {
            root = y;
        }
        else if (x == x.getFather().getLeft()) {
            x.getFather().setLeft(y);
        }
        else {
            x.getFather().setRight(y);
        }
        y.setFather(x.getFather());
    }

    private void rotateLeft(Node node) {
        Node right = node.getRight();
        node.setRight(right.getLeft());
        if (right.getLeft() != null) {
            right.getLeft().setFather(node);
        }
        right.setFather(node.getFather());
        if (node.getFather() == null) {
            root = right;
        }
        else if (node.equals(node.getFather().getLeft())) {
            node.getFather().setLeft(right);
        }
        else {
            node.getFather().setRight(right);
        }
        right.setLeft(node);
        node.setFather(right);
    }

    private void rotateRight(Node node) {
        Node left = node.getLeft();
        node.setLeft(left.getRight());
        if (left.getRight() != null) {
            left.getRight().setFather(node);
        }
        left.setFather(node.getFather());
        if (node.getFather() == null) {
            root = left;
        }
        else if (node.equals(node.getFather().getRight())) {
            node.getFather().setRight(left);
        }
        else {
            node.getFather().setLeft(left);
        }
        left.setRight(node);
        node.setFather(left);
    }

}
