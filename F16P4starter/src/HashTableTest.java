import student.TestCase;

/**
 * Test class for hash table.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.11.26
 *
 */
public class HashTableTest extends TestCase {
    private HashTable hash;
    private MemPool pool;
    private FreeList<FreeBlock> freeList;

    /**
     * Sets up.
     */
    public void setUp() {
        freeList = new FreeList<FreeBlock>();
        FreeBlock initialBlock = new FreeBlock(0, 100);
        freeList.add(initialBlock);
        pool = new MemPool(freeList, 100);
        hash = new HashTable("Test", 10, pool);
    }


    /**
     * Tests insert method.
     */
    public void testInsert() {
        hash.insert("|test|");
        System.out.println(hash.toString());
        hash.insert("|test|");
        System.out.println(hash.toString());
        hash.insert("|test|");
        System.out.println(hash.toString());
        hash.insert("|test|");
        System.out.println(hash.toString());
        hash.insert("|test|");
        System.out.println(hash.toString());
        assertEquals(hash.getTableSize(), 10);
        // System.out.println(pool.toString());
        System.out.println(hash.toString());
        assertEquals(hash.toString(), "|test| 8\n");

    }


    /**
     * Tests the probing on the insert method.
     */
    public void testProbe() {
        System.out.println(hash.sFoldHash("|abc|"));
        System.out.println(hash.sFoldHash("|cba|"));
        assertEquals(hash.insert("|abcd|"), 9);
        assertEquals(hash.insert("|abcd|"), -1);
        assertEquals(hash.insert("|cbad|"), 0);
        assertEquals(hash.insert("|acbd|"), 3);
        assertEquals(hash.insert("|cabd|"), 8);
        assertEquals(hash.insert("|cadb|"), 5);
        assertEquals(hash.getTableSize(), 10);
        // System.out.println(pool.toString());

    }


    /**
     * Tests remove method.
     */
    public void testRemove() {
        System.out.println("REMOVE TEST: ");

        hash.insert("test");
        hash.insert("test");
        hash.rehash();
        hash.rehash();
        System.out.println(hash.toString());
        hash.insert("test");
        hash.insert("test");
        hash.insert("test");

        System.out.println(hash.toString());
        hash.remove("test");
        System.out.println(hash.toString());
        hash.remove("test");
        System.out.println(hash.toString());
    }


    /**
     * Tests SFold method.
     */
    public void testSFold() {
        HashTable testHash = new HashTable("Test", 20, pool);
        assertEquals(testHash.sFoldHash("|Long Lonesome Blues|"), 15);
        assertEquals(testHash.sFoldHash("|Ma Rainey's Black Bottom|"), 16);
        assertEquals(testHash.sFoldHash("|The Things That I Used To Do|"), 16);
        assertEquals(testHash.sFoldHash("|The Loco-Motion|"), 18);
    }


    /**
     * Tests probing specifically on tombstones.
     */
    public void testProbeTombStone() {
        System.out.println(hash.sFoldHash("|abc|"));

        System.out.println(hash.sFoldHash("|cab|"));
        System.out.println(hash.sFoldHash("|cba|"));

        hash.insert("|abc|");

        hash.insert("|cab|");

        System.out.println(hash.toString());

        hash.remove("|cab|");
        assertFalse(hash.remove("|cab|"));
        assertFalse(hash.remove("|not there|"));
        assertFalse(hash.remove("|cab|"));

        System.out.println(hash.toString());
        assertEquals(hash.insert("|cba|"), 0);
        System.out.println(hash.toString());

    }


    /**
     * Tests probing and rehashing at the same time.
     */
    public void testProbeRehash() {
        hash.insert("|abc|");
        hash.insert("|b|");
        hash.insert("|c|");
        hash.insert("|d|");
        hash.insert("|e|");
        System.out.println(hash.toString());
        hash.insert("|cba|");
        hash.insert("|cab|");
        hash.remove("|abc|");
        hash.insert("|acb|");
        // hash.rehash();
        System.out.println(hash.toString());

    }


    /**
     * Tests checkDuplicates method.
     */
    public void testDuplicates() {
        hash.insert("abc");
        assertEquals(hash.insert("abc"), -1);
        hash.remove("abc");
        assertEquals(hash.insert("abc"), 8);
        assertEquals(hash.insert("cba"), 9);
        hash.remove("abc");

        System.out.println(hash.toString());

        assertEquals(hash.insert("cba"), -1);
        System.out.println(hash.toString());
        hash.rehash();
        System.out.println(hash.toString());
        assertEquals(hash.get(18).getString(), "cba");

    }
}
