import student.TestCase;

/**
 * Test class for MemPool methods.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.11.24
 *
 */
public class MemPoolTest extends TestCase {

    private MemPool pool;
    private FreeList<FreeBlock> freeList;
    private FreeBlock one;
    private FreeBlock two;
    private FreeBlock three;
    private FreeBlock four;
    private FreeBlock five;

    /**
     * Sets up.
     */
    public void setUp() {
        freeList = new FreeList<FreeBlock>();
        pool = new MemPool(freeList, 200);
        one = new FreeBlock(0, 20);
        two = new FreeBlock(20, 30);
        three = new FreeBlock(50, 40);
        four = new FreeBlock(90, 50);
        five = new FreeBlock(140, 60);
    }


    /**
     * Tests getBestFit method.
     */
    public void testGetBestFit() {
// FreeBlock one = new FreeBlock(0, 20);
// FreeBlock two = new FreeBlock(20, 30);
// FreeBlock three = new FreeBlock(50, 40);
// FreeBlock four = new FreeBlock(90, 50);
// FreeBlock five = new FreeBlock(140, 60);

        freeList.add(one);
        freeList.add(two);
        freeList.add(three);
        freeList.add(four);
        freeList.add(five);
        assertEquals(pool.getBestFit(20), 0);
        assertEquals(pool.getBestFit(30), 20);
        assertEquals(pool.getBestFit(51), 140);
        assertEquals(pool.getBestFit(61), 0);

    }


    /**
     * Tests insert method.
     */
    public void testInsert() {
        freeList.add(one);
        freeList.add(two);
        freeList.add(three);
        freeList.add(four);
        freeList.add(five);
        byte[] arr = new byte[51];
        arr[0] = (short)100;
        arr[1] = (short)101;
        assertEquals(pool.insert(arr), 140);
        System.out.println(pool.getData()[139]);
        System.out.println(pool.getData()[140]);
        System.out.println(pool.getData()[141]);
        System.out.println(pool.getData()[142]);
        assertEquals(pool.getData()[139], 0);
        assertEquals(pool.getData()[140], 100);
        assertEquals(pool.getData()[141], 101);
        assertEquals(pool.getData()[142], 0);
        byte[] sizeNine = new byte[9];
        System.out.println(freeList.toString());
        assertEquals(pool.insert(sizeNine), 191);
        System.out.println(freeList.toString());
        byte[] thirtyFive = new byte[35];
        pool.insert(thirtyFive);
        System.out.println(freeList.toString());
        pool.insert(thirtyFive);
        System.out.println(freeList.toString());
        assertEquals(freeList.toString(),
            "(0,20) -> (20,30) -> (85,5) -> (125,15)");
        pool.remove(30, 95);
        System.out.println(freeList.toString());
        assertEquals(freeList.toString(), "(0,140) -> (142,58)");
// assertEquals(pool.insert(thirtyFive), -1);
// assertEquals(pool.insert(new byte[20]), 0);
// System.out.println(freeList.toString());
// assertEquals(freeList.toString(), "(20,30) -> (85,5) -> (125,15)");
// pool.insert(new byte[5]);
// assertEquals(freeList.toString(), "(20,30) -> (125,15)");
// pool.insert(new byte[15]);
// assertEquals(freeList.toString(), "(20,30)");
// pool.insert(new byte[30]);
// assertEquals(freeList.toString(), "");
    }


    /**
     * Tests remove method.
     */
    public void testRemove() {
        freeList.add(one);
        freeList.add(two);
        freeList.add(three);
        freeList.add(four);
        freeList.add(five);
        pool.insert(new byte[28]);
        System.out.println("REMOVE TEST: ");
        System.out.println(freeList.toString());
        pool.remove(20, 28);
        System.out.println(freeList.toString());
        assertEquals(freeList.toString(), "(0,200)");

    }


    /**
     * Tests remove method some more.
     */
    public void testRemoveMore() {
        FreeBlock main = new FreeBlock(0, 100);
        freeList.add(main);
        System.out.println("REMOVE TEST 2: ");
        System.out.println(freeList.toString());
        pool.insert(new byte[30]);
        System.out.println(freeList.toString());
        pool.insert(new byte[40]);
        System.out.println(freeList.toString());
        pool.remove(0, 30);
        System.out.println(freeList.toString());
        pool.remove(30, 40);
        System.out.println(freeList.toString());
        assertEquals(freeList.toString(), "(0,200)");

    }


    /**
     * Tests merge method.
     */
    public void testMerge() {
        freeList.add(one);
        freeList.add(two);
        freeList.add(three);
        freeList.add(four);
        freeList.add(five);
        System.out.println("MERGE TEST: ");
        System.out.println(freeList.toString());
        pool.merge();
        System.out.println(freeList.toString());
        assertEquals(freeList.toString(), "(0,200)");
    }


    /**
     * Tests update freeList method.
     */
    public void testUpdateFreeList() {
        FreeBlock main = new FreeBlock(0, 200);
        freeList.add(main);
        String str = "abcdefghijklmnopqrstuvwxyz";
        byte[] first = str.getBytes();

        System.out.println("FREELIST: ");
        System.out.println(freeList.toString());
        pool.insert(first);

        System.out.println("FREELIST: ");
        System.out.println(freeList.toString());
        pool.expandCapacity();
        System.out.println("FREELIST: ");
        System.out.println(freeList.toString());
        // pool.updateFreeList();

    }


    /**
     * Tests getBestFit more.
     */
    public void testMerge2() {
        freeList.add(one);
        freeList.add(two);
        freeList.add(three);
        freeList.add(four);
        freeList.add(five);
        assertTrue(pool.merge());
        assertFalse(pool.merge());
        freeList.add(new FreeBlock(0, 15));
        assertFalse(pool.merge());
        
    }

// /**
// * Tests getter for specific portion of byte array.
// */
// public void testGetMusic() {
// freeList.add(new FreeBlock(0, 100));
// String test = "11 Test String";
// byte[] testString = test.getBytes();
// pool.insert(testString);
// System.out.println(freeList.toString());
// assertEquals(pool.getMusic(0), "Test String");
// System.out.println(pool.getMusic(0));
//
//
// }
}
