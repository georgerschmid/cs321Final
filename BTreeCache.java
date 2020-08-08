import java.util.Iterator;
import java.util.LinkedList;

/**
 * The cache class used by BTree
 *
 * @authors Devyn Roth, Melissa Samaniego, Riley Schmid 
 */
public class BTreeCache implements Iterable<BTreeNode>
{

    private final int MAX_SIZE;
    private int numHits, numMisses;
    private LinkedList<BTreeNode> list;
    
    /**
     * Constructor
     * @param MAX
     */
    public BTreeCache(int MAX)
    {
        MAX_SIZE = MAX;
        list = new LinkedList<BTreeNode>();
    }
    
    /**
     * returns the node that was added
     * @param nodeToAdd
     * @param offset
     * @return
     */
    public BTreeNode add(BTreeNode nodeToAdd, int offset)
    {
        BTreeNode nodeToReturn = null;
        if (isFull())
            nodeToReturn = list.removeLast();
        list.addFirst(nodeToAdd);
        return nodeToReturn;
    }

    /**
     * clears the cache
     */
    public void clearCache()
    {
        list.clear();
    }
    
    /**
     * returns the node that was read in to the front
     * @param offset
     * @return
     */
    public BTreeNode readNode(int offset)
    {
        for (BTreeNode n : list)
        {
            if (n.getOffset() == offset)
            {
                list.remove(n);
                list.addFirst(n);
                increaseNumHits();
                return n;
            }
        }
        increaseNumMisses();
        return null;
    }
    
    /**
     * returns the reference number
     * @return
     */
    public int getNumReferences()
    {
        return numHits + numMisses;
    }

    /**
     * increases number of hits
     */
    private void increaseNumHits()
    {
        numHits++;
    }
    
    /**
     * increases the number of misses
     */
    private void increaseNumMisses()
    {
        numMisses++;
    }
    
    /**
     * returns the number of hits
     * @return
     */
    public int getNumHits()
    {
        return numHits;
    }
    
    /**
     * returns the number of misses
     * @return
     */
    public int getNumMisses()
    {
        return numMisses;
    }
    
    /**
     * returns ratio
     * @return
     */
    public double getHitRatio()
    {
        double ratio = ((double) getNumHits()) / getNumReferences();
        return ratio;
    }
    
    /**
     * returns the size of list
     * @return
     */
    public int getSize()
    {
        return list.size();
    }
    
    /**
     * returns the max size
     * @return
     */
    public boolean isFull()
    {
        return getSize() == MAX_SIZE;
    }
    
    /**
     * returns the iterator
     */
    @Override
    public Iterator<BTreeNode> iterator()
    {
        return list.iterator();
    }
}     