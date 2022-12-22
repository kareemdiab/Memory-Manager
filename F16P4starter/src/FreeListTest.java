import java.util.Iterator;
import student.TestCase;

/**
 * Test class for FreeList.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.11.28
 *
 */
public class FreeListTest extends TestCase {
    private FreeList<FreeBlock> freeList;

    /**
     * Sets up.
     */
    public void setUp() {
        freeList = new FreeList<FreeBlock>();
    }


    /**
     * Tests isEmpty method.
     */
    public void testisEmpty() {
        assertTrue(freeList.isEmpty());
        freeList.add(new FreeBlock(0, 0));

        assertFalse(freeList.isEmpty());
        freeList.add(new FreeBlock(1, 1));
        freeList.remove(0);
        freeList.remove(0);
        assertTrue(freeList.isEmpty());

    }


    /**
     * Tests add method.
     */
    public void testAdd() {
        Exception e = null;
        try {
            freeList.add(-1, new FreeBlock(0, 0));
        }
        catch (IndexOutOfBoundsException exception) {
            e = exception;
        }
        assertNotNull(e);

        e = null;
        try {
            freeList.add(100, new FreeBlock(0, 0));
        }
        catch (IndexOutOfBoundsException exception) {
            e = exception;
        }
        assertNotNull(e);

        e = null;
        try {
            freeList.add(0, null);
        }
        catch (IllegalArgumentException exception) {
            e = exception;
        }
        assertNotNull(e);

        freeList.add(0, new FreeBlock(1, 1));
        assertEquals(freeList.toString(), "(1,1)");
        freeList.add(1, new FreeBlock(2, 2));
        // System.out.println(freeList.size());
        freeList.add(1, new FreeBlock(3, 3));
        assertEquals(freeList.toString(), "(1,1) -> (3,3) -> (2,2)");

        freeList.add(3, new FreeBlock(4, 4));

        assertEquals(freeList.toString(), "(1,1) -> (3,3) -> (2,2) -> (4,4)");

    }


    /**
     * Tests next() method.
     */
    public void testNext() {
        Iterator<FreeBlock> iter = freeList.iterator();
        Exception e = null;
        try {
            iter.next();
        }
        catch (Exception exception) {
            e = exception;
        }
        assertNotNull(e);
    }

}
