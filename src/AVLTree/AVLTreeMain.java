package AVLTree;

public class AVLTreeMain {
    public static void main(String[] args) {

        AVLTree atree = new AVLTree();


        // Insertion of values

        atree.insert(30);
        atree.insert(10);
        atree.insert(5);
        atree.insert(3);
        atree.insert(4);
        atree.insert(50);
        atree.insert(65);
        atree.insert(1);

        atree.levelAVL();

        System.out.println("Printing in order traversal");
        atree.inOrderAVL(atree.getRoot());

        System.out.println("Printing preorder traversal");
        atree.preOrderAVL(atree.getRoot());

        System.out.println("Printing postorder traversal");
        atree.postOrderAVL(atree.getRoot());


        atree.deleteNodeAVL(5); // LL condition
        atree.levelAVL();

        atree.insert(2);
        atree.deleteNodeAVL(4); // LR condition
        atree.levelAVL();


        atree.insert(20);
        atree.levelAVL();
        atree.deleteNodeAVL(65); // RR condition
        atree.levelAVL();


        atree.insert(40);
        atree.deleteNodeAVL(20); // RL condition
        atree.levelAVL();
    }
}
