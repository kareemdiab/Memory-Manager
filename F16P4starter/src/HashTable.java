/**
 * Class will be hash table to either hold songs or artists.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.11.23
 *
 */
public class HashTable {
    private Record[] hashTable;
    private MemPool pool;
    private int tableSize;
    private int initialSize;
    private int size;
    // public static final int TOMBSTONE = -2;
    private String type;

    /**
     * Constructor to initialize hash array with specified hashSize.
     * 
     * 
     * @param dataType
     *            Data type specific to hash table. Useful for printing to
     *            console.
     * @param hashSize
     *            Size of hash array.
     * @param memPool
     *            Memory pool to work with.
     */
    public HashTable(String dataType, int hashSize, MemPool memPool) {
        type = dataType;
        tableSize = hashSize;
        initialSize = hashSize;
        pool = memPool;
        hashTable = new Record[hashSize];
        size = 0;
// for (int i = 0; i < hashSize; i++) {
// hashTable[i] = null;
// }

    }


    /**
     * Hashing formula for strings.
     * 
     * @param unfolded
     *            String for hash input.
     * @return Position created from hashing function.
     */
    public int sFoldHash(String unfolded) {
        unfolded = unfolded.substring(1, unfolded.length() - 1);
        int intLength = unfolded.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = unfolded.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }
        char[] c = unfolded.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }
        return (int)(Math.abs(sum) % tableSize); // don't forget to % table size
    }


    /**
     * Insert memory handle in specified position in hash table.
     * 
     * @param name
     *            Data to insert.
     * @return position if successfully inserted. Otherwise return -1.
     */
    public int insert(String name) {
        boolean duplicates = checkDuplicates(name);
        if (duplicates) {
            return -1;
        }
        int position = sFoldHash(name);
        size++;

        if (size > tableSize / 2) {
            rehash();
            position = sFoldHash(name);
        }

        int handle = pool.getBestFit(name.length());

        // String sub = name.substring(1, name.length() - 1);

        // int i = 0;

// while (hashTable[probe] != null && !hashTable[probe].isTombStone()) {
//
// probe = position + ((i * i) % tableSize);
// i++;
// if (probe >= tableSize) {
// return false;
// }
// // hashTable[position] = handle;
// }
        int probe = probeInsert(position, hashTable);

        hashTable[probe] = new Record(name.length(), handle, pool);
        pool.insert(name.getBytes());
        return probe;
    }


    /**
     * Checks if any duplicates for specified name exist.
     * 
     * @param str
     *            String to check duplicates for.
     * @return True if duplicate exists.
     */
    public boolean checkDuplicates(String str) {
        int position = sFoldHash(str);
        int probe = position;
        for (int i = 0; i < tableSize; i++) {
            if (hashTable[probe] != null && hashTable[probe].getString().equals(
                str)) {
                return true;
            }
            probe = (position + (i * i)) % tableSize;
        }
// int i = 0;
// while (hashTable[probe] != null && !hashTable[probe].isTombStone()) {
// if (hashTable[probe].getString().equals(str)) {
// return true;
// }
// probe = (position + (i * i)) % tableSize;
// i++;
// }
        return false;

    }


    /**
     * Rehashes table elements into new bigger array. Also increases new table
     * size.
     * 
     */
    public void rehash() {
        Record[] newArr = new Record[tableSize * 2];
        tableSize *= 2;

        for (int i = 0; i < hashTable.length; i++) {
            Record rec = hashTable[i];
            if (rec != null && !rec.isTombStone()) {
                String str = rec.getString();
                // str = str.substring(1, str.length() - 1);

                int newPos = sFoldHash(str);
                newPos = probeInsert(newPos, newArr);
                newArr[newPos] = rec;
            }
        }
        hashTable = newArr;
        System.out.println(type + " hash table size doubled.");
    }


    /**
     * Quadratic probe starting from initial position
     * 
     * @param initialPos
     *            Position to start at.
     * @param arr
     *            Array to probe through.
     * @return Destination.
     */
    public int probeInsert(int initialPos, Record[] arr) {
        int probe = initialPos;
        int i = 0;
        while (arr[probe] != null && !arr[probe].isTombStone()) {

            probe = (initialPos + (i * i)) % tableSize;
            i++;
        }
        return probe;
    }


    /**
     * Returns handle at position.
     * 
     * @param position
     *            Position to return element at.
     * @return Returns memory handle.
     */
    public Record get(int position) {
        return hashTable[position];
    }


    /**
     * Removes memory handle from hash table and replaces with tombstone.
     * 
     * @param str
     *            Data that will have its memory handle removed from table.
     * @return True if successfully removed.
     */
    public boolean remove(String str) {
        int pos = sFoldHash(str);
        int probe = pos;
        int i = 0;
        while (hashTable[probe] != null && (hashTable[probe].isTombStone()
            || !hashTable[probe].getString().equals(str))) {

            probe = (pos + (i * i)) % tableSize;
            i++;
            if (i > tableSize) {
                return false;
            }

        }
        if (probe >= tableSize || probe < 0 || hashTable[probe] == null) {
            return false;
        }

        hashTable[probe].setTombStone();
        pool.remove(hashTable[probe].getHandle(), str.length());
        size--;
        return true;
        // hashTable[position].setHandle(-1);

    }


    /**
     * String representation of array.
     * 
     * @return String representation of hash table.
     */
    public String toString() {
        String result = "";
        int i = 0;

        while (i < tableSize - 1) {
            if (hashTable[i] != null && !hashTable[i].isTombStone()) {
                result += hashTable[i].toString() + " " + i + "\n";

            }

            i++;

        }
        if (hashTable[tableSize - 1] != null && !hashTable[i].isTombStone()) {
            result += hashTable[tableSize - 1].toString() + " " + (tableSize
                - 1) + "\n";
        }

        return result;

    }


    /**
     * Table size getter method.
     * 
     * @return Table size.
     */
    public int getTableSize() {
        return tableSize;
    }


    /**
     * Size getter.
     * 
     * @return Current size of table.
     */
    public int getSize() {
        return size;
    }

}
