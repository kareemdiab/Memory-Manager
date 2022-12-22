import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class to read through file, interpret commands and make use of data
 * structures appropriately.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version 2022.11.23
 *
 */
public class FileReader {
    private HashTable artistHash;
    private HashTable songHash;
    private MemPool pool;
    private FreeList<FreeBlock> freeList;
    private int printCount;

    /**
     * File Reader constructor.
     * 
     * @param hashSize
     *            Initial size of hash table.
     * @param blockSize
     *            Initial size of array of bytes.
     * @param commandFile
     *            File to read from.
     * @throws FileNotFoundException
     */
    public FileReader(int hashSize, int blockSize, File commandFile)
        throws FileNotFoundException {
        Scanner input = new Scanner(commandFile);
        freeList = new FreeList<FreeBlock>();
        FreeBlock initialBlock = new FreeBlock(0, blockSize);
        freeList.add(initialBlock);
        pool = new MemPool(freeList, blockSize);
        artistHash = new HashTable("Artist", hashSize, pool);
        songHash = new HashTable("Song", hashSize, pool);
        printCount = 0;
        readFile(input);
    }


    /**
     * Read through file and interpret commands.
     * 
     * @param input
     *            Scanner object to read from.
     */
    public void readFile(Scanner input) {
        while (input.hasNext()) {
            // String next = input.next();
            String line = input.nextLine();
            if (line.startsWith("insert")) {

                insert(line);
            }
            else if (line.startsWith("remove")) {
                if (line.contains("artist")) {
                    String str = line.substring(14);

                    remove(artistHash, str, "artist");
                }
                else if (line.contains("song")) {
                    String str = line.substring(12);
                    remove(songHash, str, "song");
                }
            }
            else if (line.startsWith("print")) {
                printCount++;
                if (line.contains("songs")) {

                    print(songHash, "songs");
                }
                else if (line.contains("artists")) {
                    print(artistHash, "artists");
                }
                else if (line.contains("blocks")) {
                    printBlocks();
                }
            }

        }

    }


    /**
     * Print blocks in free list to console.
     * 
     * @return Also returns string that is printed to console.
     */
    public String printBlocks() {
        String blocks = freeList.toString();
        // System.out.println(freeList.toString());
        if (blocks.equals("")) {
            blocks = "(" + pool.getData().length + ",0)";
        }

        System.out.println(blocks);
        return blocks;
    }
    /**
     * Print count getter.
     * @return Returns print count.
     */
    public int getPrintCount() {
        return printCount;
    }


    /**
     * Method to insert song or artist into respective hash table.
     * 
     * @param line
     *            String to hash and insert.
     */
    public void insert(String line) {

        // This needs to convert string into byte array.

        int separator = line.indexOf('<');
        String artist = line.substring(7, separator);
        artist = "|" + artist + "|";
        String song = line.substring(separator + 5);
        song = "|" + song + "|";

        // Insert artist
        // int artistHandle = pool.getBestFit(artist.length());
        // int artistPos = artistHash.sFoldHash(artist);
// if (artistHash.get(artistPos) == null || !artistHash.get(artistPos)
// .getString().equals(artist)) {
// artistHash.insert(artist);
// System.out.println(artist + " is added to the artist database.");
// }
        if (artistHash.insert(artist) >= 0) {

            System.out.println(artist + " is added to the artist database.");
        }
        else {
            System.out.println(artist
                + " duplicates a record already in the artist database.");
        }
//

        // pool.insert(artist.getBytes());

        // Insert song
        // int songHandle = pool.getBestFit(song.length());
        // int songPos = songHash.sFoldHash(song);
// if (songHash.get(songPos) == null || !songHash.get(songPos).getString()
// .equals(song)) {
// songHash.insert(song);
// System.out.println(song + " is added to the song database.");
//
// }
        if (songHash.insert(song) >= 0) {
            System.out.println(song + " is added to the song database.");

        }
        else {
            System.out.println(song
                + " duplicates a record already in the song database.");
        }

        // pool.insert(song.getBytes());
        // print(songHash, "songs");

    }


    /**
     * Remove song or artist from respective hash table.
     * 
     * @param table
     *            Hash Table to remove from.
     * @param str
     *            String to remove
     * @param data
     *            Type of data to remove, either song or artist.
     */
    public void remove(HashTable table, String str, String data) {
        boolean removed = table.remove("|" + str + "|");
        if (removed) {
            System.out.println("|" + str + "| is removed from the " + data
                + " database.");
        }
        else {
            System.out.println("|" + str + "| does not exist in the " + data
                + " database.");
        }
    }


    /**
     * Print out either all songs or artists in the database.
     * 
     * @param table
     *            HashTable to print. Either song or artist hash table.
     * @param data
     *            Type of data, either song or artist.
     */
    public void print(HashTable table, String data) {

        System.out.println(table.toString() + "total " + data + ": " + table
            .getSize());

    }
}
