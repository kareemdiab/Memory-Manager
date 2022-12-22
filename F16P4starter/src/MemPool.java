import java.util.Iterator;

/**
 * Large array of bytes to hold song and artist data.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.11.24
 *
 */
public class MemPool {

    private FreeList<FreeBlock> freeList;
    private byte[] data;
    private int capacity;
    private int initialSize;

    /**
     * Initializes the freeList to keep track of the free data.
     * 
     * @param list
     *            List to hold free blocks.
     * @param blockSize
     *            Size of array to initialize with.
     */
    public MemPool(FreeList<FreeBlock> list, int blockSize) {
        this.freeList = list;
        this.data = new byte[blockSize];
        this.capacity = blockSize;
        this.initialSize = blockSize;
    }


    /**
     * Finds best fit for particular piece of data.
     * 
     * @param size
     *            Size of data
     * @return starting position for best fit.
     */
    public int getBestFit(int size) {
        int start = -1;
        int bestFit = capacity;
        int currPosition = 0;
        Iterator<FreeBlock> iter = freeList.iterator();
        while (iter.hasNext()) {
            FreeBlock block = (FreeBlock)(iter.next());
            int currVal = block.getSize();
            currPosition = block.getIndex();

            int currDiff = currVal - size;

            if (currDiff >= 0 && (currDiff < bestFit)) {
                start = currPosition;
                bestFit = currDiff;
            }

        }
        while (start < 0) {
            expandCapacity();
            return getBestFit(size);
        }

        return start;
    }


    /**
     * Inserts array of bytes into main array.
     * 
     * @param arr
     *            Array of bytes to insert
     * @return True if successfully inserted.
     */
    public int insert(byte[] arr) {
        int pos = getBestFit(arr.length);
        int index = 0;
// if (pos < 0) {
// return -1;
// }
        System.arraycopy(arr, 0, data, pos, arr.length);
        Iterator<FreeBlock> iter = freeList.iterator();
        while (iter.hasNext()) {

            FreeBlock block = (FreeBlock)(iter.next());
            if (block.getIndex() == pos) {
                block.setIndex(pos + arr.length);
                block.setSize(block.getSize() - arr.length);
                if (block.getSize() == 0) {
                    freeList.remove(index);
                }
            }
            index++;
        }
        // updateList(pos, arr.length);
        // Have to change the free block node starting position and size.

        return pos;

    }


    /**
     * Returns song or artist at specified memory handle.
     * 
     * @param memHandle
     *            Memory location of song or artist.
     * @param size
     *            Size of string to retrieve.
     * @return Song or artist.
     */
    public String getMusic(int memHandle, int size) {
// if (memHandle == -1) {
// return "";
// }

        byte[] arr = new byte[size];
        System.arraycopy(data, memHandle, arr, 0, size);
        return new String(arr);
    }


    /**
     * Frees up specified portion of byte array and updates free block list.
     * 
     * @param memHandle
     *            Index to start removing from.
     * @param size
     *            Size of data to remove.
     * @return Returns the memory handle for the data that is removed.
     */
    public int remove(int memHandle, int size) {

        // FreeBlock newBlock = new FreeBlock(memHandle, size);

        byte[] empty = new byte[size];
        System.arraycopy(empty, 0, data, memHandle, size);
        updateFreeList();
        return memHandle;

        // updateFreeList();

    }
// public int remove(int memHandle, int size) {
// Iterator<FreeBlock> iter = freeList.iterator();
// int pos = 0;
// int index = 0;
//
//
// while (iter.hasNext()) {
//
// FreeBlock block = (FreeBlock)(iter.next());
// index = block.getIndex();
// if (index > memHandle) {
//// if (memHandle + size == index) {
//// block.setIndex(memHandle);
//// block.setSize(block.getSize() + size);
//// return index;
//// }
// FreeBlock newBlock = new FreeBlock(memHandle, size);
// freeList.add(pos, newBlock);
// byte[] empty = new byte[size];
// System.arraycopy(empty, 0, data, memHandle, size);
// merge();
// return index;
//
// }
//
// pos++;
// }
// merge();
// //updateFreeList();
// return -1;
// }


    /**
     * Merges free block list if necessary
     * 
     * @return True if merged.
     */
    public boolean merge() {
        Iterator<FreeBlock> iter = freeList.iterator();
        boolean merged = false;
        int pos = 0;
        while (iter.hasNext()) {
            FreeBlock oBlock = iter.next();
            // oBlock.setSize(77);

            while (iter.hasNext()) {
                int size = oBlock.getSize();
                int index = oBlock.getIndex();

                FreeBlock nextBlock = iter.next();
                int secondIndex = nextBlock.getIndex();
                int secondSize = nextBlock.getSize();
                pos++;
                if (secondIndex == size + index) {

                    freeList.remove(pos);
                    pos--;
                    oBlock.setSize(size + secondSize);
                    merged = true;
                }

            }

        }
        return merged;
    }


    /**
     * toString method for byte data in pool.
     * 
     * @return String representation of pool.
     */
    public String toString() {
        return new String(data);
    }


    /**
     * Update freeList after data array has expanded.
     */
    public void updateFreeList() {

        freeList.clear();
        for (int i = 0; i < capacity; i++) {
            if (data[i] == 0) {
                int size = i;
                while (size < capacity && data[size] == (short)0) {
                    size++;
                }
                size -= i;
                freeList.add(new FreeBlock(i, size));
                i += size;
            }
        }
    }


    /**
     * Expands capacity when byte array can no longer hold data.
     */
    public void expandCapacity() {
        capacity = capacity + initialSize;

        byte[] newArr = new byte[capacity];
        System.arraycopy(data, 0, newArr, 0, data.length);
        data = newArr;
        updateFreeList();
        System.out.println("Memory pool expanded to be " + capacity
            + " bytes.");

    }


    /**
     * Getter for main byte array.
     * 
     * @return Byte array.
     */
    public byte[] getData() {
        return data;
    }

}
