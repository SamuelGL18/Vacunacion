class NodoAVL {
    int key, height;
    NodoAVL left, right;

    NodoAVL(int d) {
        key = d;
        height = 1;
    }
}

public class ArbolAVL {



    private NodoAVL root;

    // Get the height of the node
    int height(NodoAVL N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // Get maximum of two integers
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Right rotate subtree rooted with y
    NodoAVL rightRotate(NodoAVL y) {
        NodoAVL x = y.left;
        NodoAVL T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // Left rotate subtree rooted with x
    NodoAVL leftRotate(NodoAVL x) {
        NodoAVL y = x.right;
        NodoAVL T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get balance factor of node N
    int getBalance(NodoAVL N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    NodoAVL insert(NodoAVL NodoAVL, int key) {
        // 1. Perform the normal BST insertion
        if (NodoAVL == null)
            return (new NodoAVL(key));

        if (key < NodoAVL.key)
            NodoAVL.left = insert(NodoAVL.left, key);
        else if (key > NodoAVL.key)
            NodoAVL.right = insert(NodoAVL.right, key);
        else // Duplicate keys not allowed
            return NodoAVL;

        // 2. Update height of this ancestor NodoAVL
        NodoAVL.height = 1 + max(height(NodoAVL.left), height(NodoAVL.right));

        // 3. Get the balance factor of this ancestor NodoAVL to check whether this NodoAVL became unbalanced
        int balance = getBalance(NodoAVL);

        // If this NodoAVL becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && key < NodoAVL.left.key)
            return rightRotate(NodoAVL);

        // Right Right Case
        if (balance < -1 && key > NodoAVL.right.key)
            return leftRotate(NodoAVL);

        // Left Right Case
        if (balance > 1 && key > NodoAVL.left.key) {
            NodoAVL.left = leftRotate(NodoAVL.left);
            return rightRotate(NodoAVL);
        }

        // Right Left Case
        if (balance < -1 && key < NodoAVL.right.key) {
            NodoAVL.right = rightRotate(NodoAVL.right);
            return leftRotate(NodoAVL);
        }

        // return the (unchanged) NodoAVL pointer
        return NodoAVL;
    }

    // Given a non-empty binary search tree, return the NodoAVL with minimum key value found in that tree.
    NodoAVL minValueNode(NodoAVL NodoAVL) {
        NodoAVL current = NodoAVL;

        // loop down to find the leftmost leaf
        while (current.left != null)
            current = current.left;

        return current;
    }

    NodoAVL deleteNode(NodoAVL root, int key) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller than the root's key, then it lies in left subtree
        if (key < root.key)
            root.left = deleteNode(root.left, key);

            // If the key to be deleted is greater than the root's key, then it lies in right subtree
        else if (key > root.key)
            root.right = deleteNode(root.right, key);

            // if key is same as root's key, then this is the node to be deleted
        else {
            // node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
                NodoAVL temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case
                    root = temp; // Copy the contents of the non-empty child
            } else {
                // node with two children: Get the inorder successor (smallest in the right subtree)
                NodoAVL temp = minValueNode(root.right);

                // Copy the inorder successor's data to this node
                root.key = temp.key;

                // Delete the inorder successor
                root.right = deleteNode(root.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = max(height(root.left), height(root.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether this node became unbalanced)
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

    // A utility function to print preorder traversal of the tree.
    // The function also prints height of every NodoAVL
    void preOrder(NodoAVL NodoAVL) {
        if (NodoAVL != null) {
            System.out.print(NodoAVL.key + " ");
            preOrder(NodoAVL.left);
            preOrder(NodoAVL.right);
        }
    }

//    public static void main(String[] args) {
//        AVLTree tree = new AVLTree();
//
//        /* Constructing tree given in the above figure */
//        root = tree.insert(tree.root, 10);
//        tree.root = tree.insert(tree.root, 20);
//        tree.root = tree.insert(tree.root, 30);
//        tree.root = tree.insert(tree.root, 40);
//        tree.root = tree.insert(tree.root, 50);
//        tree.root = tree.insert(tree.root, 25);
//
//        /* The constructed AVL Tree would be
//            30
//           /  \
//         20   40
//        /  \     \
//       10  25    50
//        */
//        System.out.println("Preorder traversal of constructed tree is : ");
//        tree.preOrder(tree.root);
//
//        tree.root = tree.deleteNode(tree.root, 10);
//
//        /* The AVL Tree after deletion of 10
//            30
//           /  \
//         20   40
//          \     \
//          25    50
//        */
//        System.out.println("\nPreorder traversal after deletion of 10 :");
//        tree.preOrder(tree.root);
//    }
}

