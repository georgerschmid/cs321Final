//we might need these
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneBankCreateBTree{
    
	private static int debugLevel = 0;
    private static int cacheOrNoCache = 0;
    private static int degree = 0;
    private static int sequenceLength = 0;
    private static String gbkFile;
    private static boolean debug = false;
    private static StringBuilder sequence = null;
    private static String FileDataName;
    private static String DNA = "";
   
    private static boolean useCache;
    private static int cacheSize;
    private static BTree tree;
    
    public static void main(String[] args) throws Exception{
	    
        try {
		
        if(args.length < 3 || args.length > 6){
            usage();
            System.exit(-1);
        }
			useCache = false;
        if(args.length == 6){
            debug = true;
            debugLevel = Integer.parseInt(args[5]);

        }
		
        //reading in number to see if we are using cache
        cacheOrNoCache = Integer.parseInt(args[0]);
		
        if(cacheOrNoCache != 0 && cacheOrNoCache != 1) {
             System.out.println("Please enter the correct arguments");
             System.exit(-1);
        }
		
        
		
        //reading on degree
        degree = Integer.parseInt(args[1]);
		
        if(degree == 0) {
            int answer = calculateOptimalDegree();
            degree = answer;
        }
        
        if(cacheOrNoCache == 1) {
        	useCache = true;
        	cacheSize = Integer.parseInt(args[4]);
           
            int answer = calculateOptimalDegree();
            degree = answer;
        }
		
        if(degree < 0) {
            System.out.println("Please enter a valid degree number");
            System.out.println();
            usage();
            System.exit(-1);
        }
        
        //reading in gbk file
        gbkFile = args[2];
       
        //sequence length
        sequenceLength = Integer.parseInt(args[3]);
        FileDataName = fileName(gbkFile, degree, sequenceLength);
        if(sequenceLength < 1 || sequenceLength > 31) {
            System.out.println("Please enter the proper sequence length between 1-31");
            System.out.println();
            usage();
            System.exit(-1);
        }
		
        } catch(Exception e) {
            usage();
            System.exit(-1);
            }
	    
        if(!debug || debugLevel == 0) {
        	if(useCache == true) {
        		tree = new BTree(degree, FileDataName,cacheSize, sequenceLength);
        	}else {
        		tree = new BTree(degree, FileDataName, sequenceLength);
        	}
            try {
                Parse(args[2], sequenceLength, tree);
                tree.writeTreeData();
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }
		
        } else if(debugLevel == 1) {
        	if(useCache == true) {
        		tree = new BTree(degree, FileDataName,cacheSize, sequenceLength);
        	}else {
        		tree = new BTree(degree, FileDataName, sequenceLength);
        	}
	    try {
	    		Parse(args[2], sequenceLength, tree);
                tree.writeTreeData();
		    
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }
	    PrintStream dumpFile = null;
		try {
			String str = "dump";
			dumpFile = new PrintStream(new FileOutputStream(str));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(dumpFile);
		tree.inOrderPrint(tree.getNode());
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        }
    }

   /**
    * 
    * @param fileName
    * @param length
    * @param destination
    * @throws FileNotFoundException
    */
    private static void Parse(String fileName, int length, BTree destination) throws FileNotFoundException {
        //Open file in a scanner
        int total = 0;
        int lineNo = 1;
        File fi = new File(fileName);
        Scanner scan = new Scanner(fi);
        boolean toggle = false;
        Pattern p = Pattern.compile("(?i)(?=([actg]{"+length+"}))");
					String prevChars = "";
        while(scan.hasNextLine()) {
          lineNo++;
          String line = scan.nextLine().trim();
          if(!toggle) {
            if(line.equals("ORIGIN")) {
              toggle = true;
            }
        } else {
          if(line.equals("//")) {
            toggle = false;
            continue;
          }
          line = line.replaceAll("[\\s0-9]*","");
						//prevChars is the previous length-1 bits from the last line, it allows for line wrapping
          Matcher m = p.matcher(prevChars+line);
          while(m.find()) {
            total++;

          destination.insert(toLong(m.group(1)));
        }
					prevChars = line.substring(line.length() - length + 1,line.length());
        }

        }
        System.out.println(total+" total matches.");
  }
    
    /**
     * 
     * @param name
     * @param degree
     * @param sequenceLength
     * @return
     */
    public static String fileName(String name, int degree, int sequenceLength) {
        String fname = "";
        
        fname = fname + name;
        fname = fname + ".btree";
        fname = fname + ("." + sequenceLength);
        fname = fname + ("." + degree);
        
        return fname; 
    }

    public static void usage(){
        
        System.out.println("Please use the following usage down below.");
        System.out.println();
        System.out.println("Usage: java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]");
        System.out.println();
        System.out.println("<0/1>: choose if you want the BTree to be created without or with a cache");
        System.out.println("<degree>: enter a number greater than or equal to 0 to decide how many objects can be stored in each tree node");
        System.out.println("<gbk file>: decide the .gbk file you will be searching");
        System.out.println("<sequence length>: enter a number between 1-31 (inclusive) to decide how the length of the sequence you are searching for");
        System.out.println("[<cache size>]: Optional input, if using a cache then must input a designated cache size, if chosen not to use a cache do not input an argument here");
        System.out.println("[<debug level>]: Optional input, please enter only 0 or 1,argument will default to 0 if no input is specified");
        
        System.exit(0); //exits program
    }


   /**
    * 
    * @return
    */
   public static int calculateOptimalDegree() {
	  
	   double optimum;
       int sizeOfPointer = 4;
       int sizeOfObject = 12;
       int sizeOfMetadata = 5;
       double diskBlockSize = optimum = 4096;
       optimum += sizeOfObject;
       optimum -= sizeOfPointer;
       optimum -= sizeOfMetadata;
       optimum /= (2 * (sizeOfObject + sizeOfPointer));
       return (int) Math.floor(optimum);
	  
   }
   
   /**
    * 
    * @param code
    * @return
    */
   private static Long toLong(String code) {
	      String s = code.toLowerCase();
	      s = s.replaceAll("a","00");
	      s = s.replaceAll("t","11");
	      s = s.replaceAll("c","01");
	      s = s.replaceAll("g","10");
	      Long m = 1l;
	      m = m<<63; //One with 63 zeroes after it, so we can always have 64 bits
	      return (Long.parseLong(s,2) | m); //Mask it so if we ever wanted to see the full binary value we can
	    }
}
