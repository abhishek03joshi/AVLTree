package AVLTree;

import node.BinaryNode;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTree {

    BinaryNode root;
    public BinaryNode getRoot() { return root; }


    AVLTree() { root = null; }

    //Create new node
    public BinaryNode createNode(int value) {
        BinaryNode node = new BinaryNode();
        node.setValue(value);
        node.setHeight(0); // Addition of Node takes place on the leaf and hence height of leaf node is 0.
        return node;
    }

    //Searching for a value in node
    public BinaryNode search(BinaryNode root, int value){
        if (root == null){
            System.out.println("Value " + value + " is not found!");
            return null;
        }

        if ( value == root.getValue()){
            System.out.println("Match found!");
            return root;

        }

        else if (value < root.getValue()) {
            root.setLeft(search(root.getLeft(), value));
        } else {
            root.setRight(search(root.getRight(), value));
        }
        return root;

    }

    //Search wrapper method
    void search(int value){
        search(root, value);
    }


    //Level Order Traversal of AVL Tree
    void levelAVL(){
        System.out.println("Printing Level Order Traversal of AVL tree!");
        Queue<BinaryNode> queue = new LinkedList<BinaryNode>();
        queue.add(root);
        if(root == null){
            System.out.println("Tree does not exist");
            return;
        }
        while(!queue.isEmpty()){
           BinaryNode presentNode = queue.remove();
            System.out.println("Value is " + presentNode.getValue());
           //Add left child
            if (presentNode.getLeft() != null){
                queue.add(presentNode.getLeft());
            }
            // Add right child
            if ( presentNode.getRight() != null){
                queue.add(presentNode.getRight());
            }
        }
    }

    //Pre Order traversal
    void preOrderAVL(BinaryNode root){
        if(root == null){
            return;
        }
        System.out.println("Value : " + root.getValue());
        preOrderAVL(root.getLeft());
        preOrderAVL(root.getRight());
    }

    //In Order traversal
    void inOrderAVL(BinaryNode root){
        if ( root  == null) {
            return;
        }
        inOrderAVL(root.getLeft());
        System.out.println("Value : " + root.getValue());
        inOrderAVL(root.getRight());

    }

    //Post Order traversal
    void postOrderAVL(BinaryNode root) {
        if ( root == null) {
            return;
        }

        postOrderAVL(root.getLeft());
        postOrderAVL(root.getRight());
        System.out.println("Value : " + root.getValue());

    }

    int calculateHeight(BinaryNode node){
        if ( node == null) {
            return 0;
        }
        return 1 + Math.max((node.getLeft() != null ? node.getLeft().getHeight() : -1 ),(node.getRight() != null ? node.getRight().getHeight() : -1));

    }

    // Check tree balance
    int checkBalance(BinaryNode left, BinaryNode right){
        //if current node is a leaf node
        if((left == null) && (right == null)) {
            return 0;
        } else if (left == null){
            return -1 * (right.getHeight() + 1);
        } else if (right == null) {
            return left.getHeight() +  1;
        } else {
            return left.getHeight() - right.getHeight();
        }

    }

    //General right rotation
    BinaryNode rightRotate(BinaryNode disbalNode){
        BinaryNode newRoot = disbalNode.getLeft();
        disbalNode.setLeft(disbalNode.getLeft().getRight());
        newRoot.setRight(disbalNode);
        disbalNode.setHeight(calculateHeight(disbalNode));
        newRoot.setHeight(calculateHeight(newRoot));
        return newRoot;
    }


    //General left rotation
    BinaryNode leftRotate(BinaryNode disbalNode){
        BinaryNode newRoot = disbalNode.getRight();
        disbalNode.setRight(disbalNode.getRight().getLeft());
        newRoot.setLeft(disbalNode);
        disbalNode.setHeight(calculateHeight(disbalNode));
        newRoot.setHeight(calculateHeight(newRoot));
        return newRoot;

    }



    //Insertion
    BinaryNode insert(BinaryNode node, int value){

        if ( node == null) {
            System.out.println("Inserted value " + value + " in node");
            return createNode(value);
        } else if ( value <= node.getValue()) {
            node.setLeft(insert(node.getLeft(), value));
        } else {
            node.setRight(insert(node.getRight(), value));
        }


        //AVL specific work checking the AVL tree balance

        int balance = checkBalance(node.getLeft(), node.getRight());
        if ( balance > 1 ) {
            if(checkBalance(node.getLeft().getLeft(),node.getLeft().getRight()) > 0){
                // LL Condition
                node = rightRotate(node);
            } else{
                // LR Condition
                node.setLeft(leftRotate(node.getLeft()));
                node = rightRotate(node);

            }
        } else if ( balance < -1 ) {
            if(checkBalance(node.getRight().getRight(), node.getRight().getLeft()) > 0){
                // RR Condition
                node = leftRotate(node);

            } else {
                //RL Condition
                node.setRight(rightRotate(node.getRight()));
                node = leftRotate(node);

            }
        }

        //height setting
        if (node.getLeft() != null){
            node.getLeft().setHeight(calculateHeight(node.getLeft()));

        }
        if (node.getRight() != null) {
            node.getRight().setHeight(calculateHeight(node.getRight()));
        }

        node.setHeight(calculateHeight(node));

        return node;
    }

    //Insertion wrapper method
    void insert(int value) {
        this.root = insert(this.root, value);
    }


    //Delete node from BST Wrapper
    void deleteNodeAVL(int value){
        System.out.println("Deleting value " + value + " from AVL tree..");
        root = deleteNodeAVL(root, value);
    }

    //Find Minimum node on the right
    public BinaryNode minimumElement (BinaryNode root) {
        if (root.getLeft() == null) {
            return root;
        } else {
            return minimumElement(root.getLeft());
        }

    }

    //Delete node from BST with conditions
    BinaryNode deleteNodeAVL(BinaryNode node, int value){
        if ( node == null ) {
            // Node to be deleted by value cannot be found in the AVL tree.
            System.out.println("Cannot find value " + value + " to be deleted..");
            return null;
        }

        if ( value < node.getValue()) {
            node.setLeft(deleteNodeAVL(node.getLeft(), value));
        } else if (value > node.getValue()){
            node.setRight(deleteNodeAVL(node.getRight(), value));
        } else {
            //Current is the node to be deleted.
            System.out.println("Found the node to be deleted");

            if(node.getLeft() != null && node.getRight() != null) {
                //Find minimum node to the right
                BinaryNode minNodeForRight = minimumElement(node.getRight()); // Find the minimum element in the right subtree of the node to be deleted.
                node.setValue(minNodeForRight.getValue()); // Set the value of the current node with that of the minimum node in the right subtree of node to be deleted.
                deleteNodeAVL(node.getRight(), minNodeForRight.getValue()); // Deleting the minimum node on the right

            } else if (node.getLeft() != null) {
                node = node.getLeft();
            } else if (node.getRight() != null) {
                node = node.getRight();
            } else {
                // The node to be deleted is a leaf node
                System.out.println("this is a leaf node to be deleted");
                node = null;
                return node; // For a leaf node, there is no need to be AVL tree balancing and hence just return the node.
            }
        }


        int balance = checkBalance(node.getLeft(), node.getRight());

        if ( balance > 1 ) {
            // Right condition
            if (checkBalance(node.getLeft().getLeft(),node.getLeft().getRight()) > 0) {
                // LL Condition
                node = rightRotate(node);
            } else {
                // LR condition
                node.setLeft(leftRotate(node.getLeft()));
                node = rightRotate(node);

            }
        } else if ( balance < -1 ) {
            // Left condition
            if (checkBalance(node.getRight().getRight(), node.getRight().getLeft()) > 0) {
                // RR condition
                node = leftRotate(node);
            } else {
                // RL condition
                node.setRight(rightRotate(node.getRight()));
                node = leftRotate(node);
            }
        }


        if (node.getLeft() != null) {
            node.getLeft().setHeight(calculateHeight(node.getLeft()));
        }

        if (node.getRight() != null) {
            node.getRight().setHeight(calculateHeight(node.getRight()));
        }
        node.setHeight(calculateHeight(node));
        return node;
    }

    //Delete BST
    void deleteAVL(){
        root = null;
    }


}
