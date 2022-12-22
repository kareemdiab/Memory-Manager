/**
 * This class will represent data type for both songs and artists, containing a
 * name and memory handle.
 * 
 * @author Kareem Diab
 * @version 2022.11.27
 *
 */
public class Record {
    private boolean tombstone;

    private int handle;
    private int size;
    private MemPool pool;

    /**
     * Constructor to initialize record with name and handle.
     * 
     * @param s
     *            Size of record.
     * @param h
     *            Memory Handle for record.
     * @param memPool
     *            MemPool which the record will be located.
     * 
     */
    public Record(int s, int h, MemPool memPool) {
        this.size = s;
        this.handle = h;
        this.pool = memPool;
        this.tombstone = false;

    }


    /**
     * Marks record as tombstone;
     */
    public void setTombStone() {
        this.tombstone = true;
    }


    /**
     * Checks if record is a tombstone.
     * 
     * @return True if tombstone;
     */
    public boolean isTombStone() {
        return tombstone;
    }


    /**
     * Memory handle setter.
     * 
     * @param newHandle
     *            New memory handle.
     */
    public void setHandle(int newHandle) {
        this.handle = newHandle;
    }


    /**
     * Name getter.
     * 
     * @return Name.
     */
    public String getString() {

        return pool.getMusic(handle, size);
    }


    /**
     * Handle getter.
     * 
     * @return Handle.
     */
    public int getHandle() {
        return this.handle;
    }


    /**
     * ToString method for record.
     * 
     * @return String representation of record.
     */
    public String toString() {

        return getString();

    }
}
