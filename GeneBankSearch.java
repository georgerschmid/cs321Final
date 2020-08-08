//we might end up using some of these
import java.io.*;
import java.util.*;

/**
* This class searches a BTree for certain sequences from a gbk file.
*
* @author Riley Schmid
* @author Devyn Roth
* @author Melissa Samaniego
*
*
*/

public class GeneBankSearch{
	
    private static int debugLevel, sequenceLength, degree, size, offset, count2, count;
    private static BTreeNode root;
	private static PrintStream dump;
	private static boolean useCache = false;
	private static int cacheSize;
	private static BTreeCache cache;
	

    //java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>][<debug level>]
    public static void main(String[] args) throws Exception{
        if(args.length < 3 || args.length > 5){
            usage();
            System.exit(-1);
        }
    
	    
	if (args.length == 5) {
		debugLevel = Integer.parseInt(args[4]);

	}

	
	//cache arguments
	if(args[0].equals("1")) {
		cacheSize = Integer.parseInt(args[3]);
		 cache = new BTreeCache(cacheSize);
		useCache = true;
	}else if(args[0].equals("0")) {
		cache = null;
	} 
    try{
	    
    String gbkFile = args[1];
    String queryFile = args[2];
    
    	 RandomAccessFile readNode = new RandomAccessFile(gbkFile, "rw");
    
   
    Scanner scan = new Scanner(new File(queryFile));
    readNode.seek(0);
    sequenceLength = readNode.readInt();
    degree = readNode.readInt();
    size = readNode.readInt();
    offset = readNode.readInt();
    root = readNode(readNode, 12);
    if(useCache == true) {
    	BTree tree = new BTree(degree, new File(gbkFile), useCache, cacheSize, sequenceLength);
    }
        count = 0;
        while(scan.hasNextLine()){
        	String DNA = scan.next();
        	String base = DNA.replaceAll("\\s","");

        	if(DNA.length() != sequenceLength) continue;

        	Long change = toLong(base);
        	TreeObject x = searchFile(readNode,root, change);

        	int total = 0;      
        	if(x != null){
        		total = x.getFreq();
        	}else {
        	
        	Long alter = change ^ ~(~0<<(2*sequenceLength));
        	x = searchFile(readNode, root, alter);
        	}
        	if(x != null){
        		total = x.getFreq();
        		
        	}
        	if(!(total == 0)) {
        		System.out.println(base.toLowerCase() + ": " + total);
        	}
        	count++;
        }  
        
    }

    catch(FileNotFoundException e){
    	System.out.println("File has not been found");
    	System.exit(1);
    }catch(NoSuchElementException e){
    	System.exit(1);
    }
    }
	
	/**
	*
	* Prints the usage of the program
	*
	*/
		 
    public static void usage(){
	    
        System.out.println("Please see the following usage instructions below.");
        System.out.println();
        System.out.println("Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>][<debug level>]");
        System.out.println();
        System.out.println("<0/1>: choose if you want a cache to be implemented for memory efficiency while searching");
        System.out.println("<btree file>: select the btree file that was created after running GeneBankCreateBTree");
        System.out.println("<query file>: select one of the provided query files given to search for the desired substring");
        System.out.println("[<cache size>]: Optional input, if using a cache then must input a designated cache size, if chosen not to use a cache do not input an argument here");
        System.out.println("[<debug level>]: Optional input, please enter only 0 or 1,argument will default to 0 if no input is specified");

        System.exit(0); //exits the program
    }

	/**
	*
	* Prints to the console for the dump file
	*
	* @param BTreeNode x
	*/

   public static void printToConsole(BTreeNode x){
	   
         for(int i = 0; i < ((CharSequence) x.getKeys()).length(); i++){
            TreeObject value = x.getKey(i);
            if(x.getKey(i) != null){
            	dump = null;
                dump.println(value + ":" + " ");
            }
        }
   }

	/**
	*
	* Converts the DNA to long value
	*
	* @param DNA
	*/
   private static long toLong(String DNA){
	  
       String temp = DNA.replaceAll("A", "00");
       temp = temp.replaceAll("T", "11");
       temp = temp.replaceAll("C", "01");
       temp = temp.replaceAll("G", "10");
       Long m = 1l;
       m = m<<63;
       return (Long.parseLong(temp,2) | m);
    }
  
	
	/**
	*
	* Converts the long value to DNA
	*
	* @param DNA
	* @param length
	*/

   private static String toDNA(long DNA, int length){
       String temp = Long.toString(DNA, 4);
       temp = temp.replaceAll("0", "A");
       temp = temp.replaceAll("1", "C");
       temp = temp.replaceAll("2", "G");
       temp = temp.replaceAll("3", "T");
       temp = temp.substring(32-length,32);
       return temp;
   }
	/**
	*
	* Method that reads the node from BTree
	*
	* @param raf
	* @param off
	*/
   
   public static BTreeNode readNode(RandomAccessFile raf, int off) {
	   BTreeNode n = null;
	   if(cache != null) {
       	n = cache.readNode(off);
       }
       if(n != null) {
       	return n;
       }
       n = new BTreeNode();
       TreeObject object = null;
       n.setOffset(off);
       int k = 0;
       try {
           raf.seek(off);
           boolean isLeaf = raf.readBoolean();
           n.setLeaf(isLeaf);
           int temp = raf.readInt();
           n.setNumKeys(temp);
           int parent = raf.readInt();
           n.setParent(parent);
           for (k = 0; k < (2 * degree) - 1; k++) {
               if (k < n.getNumKeys() + 1 && !n.isLeaf()) {
                   int child = raf.readInt();
                   n.addChild(child);
               } else if (k >= n.getNumKeys() + 1 || n.isLeaf()) {
                   raf.seek(raf.getFilePointer() + 4);
               }
               if (k < n.getNumKeys()) {
                   long value = raf.readLong();
                   int frequency = raf.readInt();
                   object = new TreeObject(value, frequency);
                   n.addKey(object);
               }
           }
           if (k == n.getNumKeys() && !n.isLeaf()) {
               int child = raf.readInt();
               n.addChild(child);
           }
       } catch (IOException ioe) {
           System.err.println(ioe.getMessage());
           System.exit(-1);
       }
       return n; 
   }
   
	/**
	*
	* Method that searches the file
	*
	* @param raf
	* @param root
	* @param key
	*/
   public static TreeObject searchFile(RandomAccessFile raf, BTreeNode root, long key){
       int i = 0;
       TreeObject x = new TreeObject(key);
       while(i < root.getNumKeys() && (x.compareTo(root.getKey(i)) > 0)) {
    	   i++;
       }
       if(i < root.getNumKeys() && (x.compareTo(root.getKey(i)) == 0)) {
    	   return root.getKey(i);
       }
       if(root.isLeaf()) {
    	   return null;
       }else {
    	   int offset = root.getChild(i);
    	   BTreeNode y = readNode(raf, offset);
    	   return searchFile(raf, y, key);
       }
   }
}
