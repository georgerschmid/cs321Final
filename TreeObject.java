public class TreeObject{
   	 private int freq;
	    private long key;

	    /**
	     * Constructor for the BTree object.
	     *
	     * @param key  - the key of the object.
	     * @param freq - the frequency of the object.
	     */

	    public TreeObject(long key, int freq) {
	        this.key = key;
	        this.freq = freq;
	    }

	    /**
	     * Constructor for the BTree object.
	     *
	     * @param key - the key of the object.
	     */

	    public TreeObject(long key) {
	        this.key = key;
	        this.freq = 1;
	    }

	    /**
	     * Returns the key of the BTree object.
	     *
	     * @return
	     */
	    public Long getKey() {
	        return this.key;
	    }

	    /**
	     * Returns the frequency of the BTree object.
	     *
	     * @return
	     */
	    public int getFreq() {
	        return this.freq;
	    }

	    /**
	     * Increments the frequency of the BTree object.
	     */
	    public void increaseFreq() {
	        freq++;
	    }

	    /**
	     * Compares one key to another.
	     *
	     * @param obj
	     * @return
	     */
	    public int compareTo(TreeObject obj) {
	        if (key < obj.key)
	            return -1;
	        if (key > obj.key)
	            return 1;
	        else
	            return 0;
	    }
	    
	    //returns the data
	    public Long getData(){
	        return this.key;
	    }

	 
	    //increments the frequency by one
	    public void incFreq(){
	        freq = freq + 1;
	    }

	    //sets the data with the long value in the parameter
	    public void setData(Long newData){
	        key = newData;
	    }

	    //equal method that checks whether if the value is equal to the data, returns true if it does, otherwise it returns false
	    public boolean isEqual(Long checkData){
	        if(key == checkData){
	            return true;
	        }
	        else{
	            return false;
	        }
	    }
}
