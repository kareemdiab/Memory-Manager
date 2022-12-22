/**
 * Class to represent free blocks within the free list.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.11.25
 *
 */
public class FreeBlock {

    private int index;
    private int size;

    /**
     * Initialize index and Size
     * 
     * @param i
     *            Index.
     * @param s
     *            Size.
     */
    public FreeBlock(int i, int s) {
        this.index = i;
        this.size = s;
    }


    /**
     * Size setter.
     * 
     * @param s
     *            New size.
     */
    public void setSize(int s) {
        this.size = s;

    }


    /**
     * Index setter.
     * 
     * @param i
     *            New index.
     */
    public void setIndex(int i) {
        this.index = i;
    }


    /**
     * Size getter.
     * 
     * @return Size.
     */
    public int getSize() {
        return size;
    }


    /**
     * Index getter.
     * 
     * @return Index.
     */
    public int getIndex() {
        return index;
    }


    /**
     * ToString method for FreeBlock.
     * 
     * @return String representation.
     */
    public String toString() {
        
        return "(" + index + "," + size + ")";

    }

}
