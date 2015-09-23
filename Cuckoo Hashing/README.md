Cuckoo HashMap
The hashtable using cuckoo hashing that gurantees at most 2 look ups are required to find something. Since linear probing or external chaining has a possibilties to degenerate from the expected time of O(1) to O(n). Cuckoo hashing is used to ensure worst case performance of O(1). 

The basic goal of cuckoo hashing is to require at most 2 comparisons to find something ins the table. To accomplish this, two backing arrays for tables and two different hash functions are used.
