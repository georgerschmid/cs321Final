/**
* This class represents the BTree Node
*
* @author Riley Schmid
* @author Devyn Roth
* @author Melissa Samaniego
*
*
*/

//we might need these
import java.io.*;
import java.util.*;

public class BTreeNode{

    LinkedList<TreeObject> keys;
    LinkedList<Integer> child;
    private int parent;
    private int offset;
    private boolean isLeaf;
    public int numKeys;

    /**
     * Constructor of the BTreeNode
     *
     * @param height    - height of the node being created
     * @param isRoot    - is the node a root node?
     * @param isLeaf    - or is it a leaf?
     * @param nodeCount - number of nodes in the tree
     */
    public BTreeNode() {
        keys = new LinkedList<TreeObject>();
        child = new LinkedList<Integer>();

        numKeys = 0;
        parent = -1;

    }

    /**
     * Returns the key give my param from the BTree
     *
     * @param key
     * @return object
     */
    public TreeObject getKey(int key) {
        TreeObject object = keys.get(key);
        return object;
    }

    /**
     * Adds a key using our TreeObject
     *
     * @param object
     */
    public void addKey(TreeObject object) {
        keys.add(object);
    }

    /**
     * Adds a key using our TreeObject and a integer
     *
     * @param object
     * @param key
     */
    public void addKey2(TreeObject object, int key) {
        keys.add(key, object);
    }

    /**
     * removes a key from out TreeObject using a key
     *
     * @param key
     * @return keys.remove(key)
     */
    public TreeObject removeKey(int key) {
        return keys.remove(key);
    }

    /**
     * returns the amount of keys in the BTree
     *
     * @return keys
     */
    public LinkedList<TreeObject> getKeys() {
        return keys;
    }

    /**
     * Returns the child specified in the BTree
     *
     * @param key
     * @return child.get(key).intValue()
     */
    public int getChild(int key) {
        return child.get(key).intValue();
    }

    /**
     * Adds a child into our BTree
     *
     * @param key
     */
    public void addChild(int key) {
        child.add(key);
    }

    /**
     * Adds a child into our BTree using two parameters
     *
     * @param c
     * @param key
     */
    public void addChild2(Integer c, int key) {
        child.add(key, c);
    }

    /**
     * returns the removed child from the BTree
     *
     * @param key
     * @return child.remove(key)
     */
    public int removeChild(int key) {
        return child.remove(key);
    }

    /**
     * Returns the amount of childs in the BTree
     *
     * @return child
     */
    public LinkedList<Integer> getChild() {
        return child;
    }

    /**
     * Sets the offset of the given node.
     *
     * @param index - the index to set the node to.
     */
    public void setOffset(int index) {
        offset = index;
    }

    /**
     * Returns if the node is a leaf or not.
     *
     * @return isLeaf
     */
    public boolean isLeaf() {
        return isLeaf;
    }

    /**
     * Returns the offset of the BTree.
     *
     * @return offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Returns the number of key value for the BTree
     *
     * @return numKeys
     */
    public int getNumKeys() {
        return numKeys;
    }

    /**
     * Sets the number of keys in the BTree
     *
     * @param numKeys
     */
    public void setNumKeys(int numKeys) {
        this.numKeys = numKeys;
    }

    /**
     * Sets the given node as a leaf node.
     *
     * @param isLeaf
     */
    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * Sets the amount of parents a node has.
     *
     * @param parent - number of parents
     */
    public void setParent(int parent) {
        this.parent = parent;
    }

    /**
     * Returns the amount of parents a node has.
     *
     * @return parent
     */
    public int getParent() {
        return parent;
    }

      /**
     * Returns the different of the offset and BTreeNode offset
     *
     * @return offset - o.offset
     */
    public int compareTo(BTreeNode o) {
        return offset - o.offset;
    }
 
    
    //another add method for addChild
    //when added, it shifts all the children to the right
    //newNode is the index of the new node of the child
    //node is where it is going to be added to
    public void addChild(Integer newNode, int node) {
        child.add(node, newNode);
    }
 
    //sets the key by overwriting the current key within the array list
    // k is the key that will be overwritten
    //key is what will replace k
    public void setKey(int k, TreeObject key) {
        keys.set(k, key);
    }
    
}
