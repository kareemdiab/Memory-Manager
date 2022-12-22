import java.io.File;

//"I have neither given nor received unauthorized assistance on this
//assignment." - Kareem Diab


/**
 * Project runner.
 * 
 * @author Kareem Diab (kareemdiab3)
 * @version (2022.11.23)
 *
 */
public class MemMan {
    /**
     * Main method to start the project.
     * 
     * @param args
     *            Command line arguments.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length == 3) {
            File commandFile = new File(args[2]);
            int hashSize = Integer.parseInt(args[0]);
            int blockSize = Integer.parseInt(args[1]);
            @SuppressWarnings("unused")
            FileReader fileReader = new FileReader(hashSize, blockSize,
                commandFile);

        }
        else {
            throw new Exception("Incorrect amount of command line arguments!");
        }
    }
}
