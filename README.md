# Memory-Manager

This project is essentially a memory management package for storing variable-length records in a large memory space. I use a linked list to represent a free block 
to keep track of where there is still space in memory. I also use sequential fit to decide where each record will go in memory. To quickly find the 
locations of stored records, I use a hash table to generate memory handles based on the title of each memory record. When there is not enough space 
in the freeblock list for an incoming record, the list size increases by 32 bytes. When there is not enough space in the hashtable, the table 
doubles in size. The commands for inserting, removing, and printing contents of the freeblock list/hash table are provided in a specified commandFile on 
the command line. Results of either successful or unsuccessful operations are printing to the console. 

Invocation: 
    >%   java MemMan {initial-hash-size} {initial-block-size} {command-file}
