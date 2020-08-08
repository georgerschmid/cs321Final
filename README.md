# CS321-Final-Project
Final project for Dr. Yeh's 321 Data Structures class

# Team Members
Devyn Roth, Riley Schmid, Melissa Samaniego

# Files
GeneBankSearch: Class that searches a BTree for certain sequences from a gbk file.

GeneBankCreateBTree: Class that creates a BTree from a gbk file.

BTree: Class that is an implementation of a BTree. It creates and searches a BTree.

BTreeNode: A node class that is used by the BTree class.

BTreeCacheNode: The node class used by the BTreeCache.

BTreeCache: Cache class implemented when a Cache is declared in the arguments.

TreeObject: Object classed that is used by the BTree class.

README: This file.

# Compiling and Usage 
The program is compiled by typing the following into the terminal: $ javac *.java

Execute the following to run GeneBankCreateBTree: java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length>
[<cache size>] [<debug level>]
  
Execute the following to run GeneBankSearch: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>]
[<debug level>]
  
# With Cache vs. Without Cache
With Cache:
The program runs significantly faster when using the cache feature. It is still kind of slow but the dump file was finished within 37 seconds. 

Without Cache:
The program runs really slow without using the cache feature. It was taking over a minute, specifically 1 minute and 18 seconds, to write to the filewithout the cache. 

Based from our observations and data, we can conclued that the program runs more efficiently using cache than without.

For GeneBankSearch:
With Cache: We ran the program with a timer and got an approximate time of 22.1 seconds to finish the run.

Without Cache: When running without the cache we saw no significant difference, the program ran approximately 0.2 seconds slower when run without the cache.

When it comes to GeneBankSearch we concluded that adding a cache does not make a significant difference in the runtime of the program.
  
# Notes: Issues..
Issues:
We had a lot of problems trying to implement the program as we kept getting multiple exceptions and nothing was being printed out to the console. Some of this issues arose within a few methods such as in the parser, convert to DNA method, and methods from the BTree and BTreeNode class. In addition, the frequency was producing a really high number. We realized the issue was that we had two getFrequency methods and we were not using the correct one. Not only that, the output would only produce a few lines of the file when we should have had way more. 

# BTree Layout:
We have a few pieces of metadata within our BTree that are all stored at the front of the file, the pieces of data are stored in order of: sequenceLength, degree, nodeSize, and the root offset.

# Debugging
Debugging primarily occured after we finished drafting all of the classes, which did leave us with allot of bugs to fix but we managed to get through and solve them so the program runs.
