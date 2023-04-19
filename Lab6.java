import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Lab6 {

  public static void main(String[] args) throws Exception {
    // Get the file name from the command-line arguments
    String fileName = args[0];
    boolean fileNameCheck = false;
    boolean flag = false; // flag rises when exception happens
    do {
      if (flag) {
        // If an exception occurred, prompt the user to enter a valid file path
        Scanner c = new Scanner(System.in);
        System.out.println("Enter a Valid Path:");
        fileName = c.nextLine();
        c.close();
        c = null;
      }

      try {
        // Check if the file name is valid
        Func.checkFileName(fileName);
        fileNameCheck = true;
      } catch (NotValidAutosarFileException ex) {
        // If the file name is not valid, print an error message and set the flag to
        // true
        System.out.println(ex.getMessage());
        flag = true;
      }
    } while (!fileNameCheck);

    // Create a new File object for the specified file name
    File file = new File(fileName);

    // Check if the file exists and if it is empty
    Func.checkExistance(file);
    Func.checkEmptyFile(file);

    // Count the number of containers in the file
    int containerNo = Func.countNoOfContainers(file);

    // Create an array of Container objects
    Container[] containers = new Container[containerNo];

    // Create a Scanner to read from the file
    Scanner sc = new Scanner(file);

    // Get the first two lines of the file
    String[] firstLines = new String[2];
    firstLines = Func.get_1stLines(sc, file);

    // Read each container from the file and store it in the containers array
    for (int i = 0; i < containerNo; i++) {
      containers[i] = new Container(sc, file);
    }

    // Check if the next line is "</AUTOSAR>"
    if (!sc.nextLine().equals("</AUTOSAR>")) {
      throw new AutosarFileException(file);
    }

    // Sort the containers array
    Arrays.sort(containers);

    // Create a new file name by appending "_mod.arxml" to the original file name
    String newFileName = Func.modifyStringName(fileName);

    // Create a new File object for the new file name
    File newFile = new File(newFileName);

    // Check if the new file already exists
    if (newFile.exists()) {
      System.out.println(
          "can't create a new file, file already exists with the same name.");
      System.exit(1);
    }

    // Create a new file with the specified name
    newFile.createNewFile();
    // Create a PrintWriter to write to the new file
    PrintWriter pw = new PrintWriter(newFile);

    // Write the first two lines of the original file to the new file
    pw.println(firstLines[0]);
    pw.println(firstLines[1]);

    // Write each container to the new file
    for (Container container : containers) {
      container.writeContainer(pw);
    }

    // Write the closing "</AUTOSAR>" tag to the new file
    pw.print("</AUTOSAR>");

    // Close the PrintWriter
    pw.close();
  }

  class Func {

    /**
     * Prompts the user to enter a valid file path and returns it.
     *
     * @return String, The valid file path entered by the user
     */
    public static String getFileName_user() {
      // Create a new Scanner object to read input from the user
      Scanner scanner = new Scanner(System.in);
      String str = null;
      boolean checkName = true;
      do {
        // Prompt the user to enter the file path
        System.out.println("Enter File Path:");
        // Read the file path entered by the user
        str = scanner.next();
        try {
          // Check if the file name is valid
          checkFileName(str);
          checkName = false;
        } catch (NotValidAutosarFileException ex) {
          // If the file name is not valid, print the error message
          System.out.println(ex.getMessage());
        }
      } while (checkName); // Repeat until a valid file name is entered
      // Close the scanner
      scanner.close();
      // Return the file name entered by the user
      return str;
    }

    /**
     * Checks if the given file name has a valid .arxml extension.
     *
     * @param fileName String, The file name to check
     * @throws NotVaildAutosarFileException If the file name does not have a valid
     *                                      .arxml extension
     */
    public static void checkFileName(String fileName)
        throws NotValidAutosarFileException {
      // Get the length of the file name
      int length = fileName.length();
      // Check if the file name contains the ".arxml" extension
      boolean checkFileName = fileName.contains(".arxml");
      // Get the index of the ".arxml" extension in the file name
      int index = fileName.indexOf(".arxml");
      // Check if the ".arxml" extension is at the end of the file name
      if (index == (length - 6) && checkFileName) {
        // If the file name is valid, return without throwing an exception
        return;
      } else {
        // If the file name is not valid, throw a NotVaildAutosarFileException
        throw new NotValidAutosarFileException(fileName);
      }
    }

    /**
     * Checks if the given file exists.
     *
     * @param f The file to check
     * @throws NotExistingAutosarFileException If the file does not exist
     */
    public static void checkExistance(File f)
        throws NotExistingAutosarFileException {
      // Check if the file exists
      if (f.exists()) {
        // If the file exists, return without throwing an exception
        return;
      } else {
        // If the file does not exist, throw a NotExistingAutosarFileException
        throw new NotExistingAutosarFileException(f);
      }
    }

    /**
     * Checks if a file is empty and throws an exception if it is
     * 
     * @param f The file to check
     * @throws EmptyAutosarFileException if the file is empty
     */
    public static void checkEmptyFile(File f) throws EmptyAutosarFileException {
      // Check if the file is empty
      if (f.length() == 0) {
        // Throw an exception if the file is empty
        throw new EmptyAutosarFileException(f);
      } else {
        // Return if the file is not empty
        return;
      }
    }

    /**
     * Reads the first two lines of an ARXML file and checks if they are valid
     * 
     * @param sc Scanner object to read from file
     * @param f  The file being read
     * @return An array containing the first two lines of the file
     * @throws DefAutosarFileException if the file is defected
     */
    public static String[] get_1stLines(Scanner sc, File f)
        throws AutosarFileException {
      // Read the first two lines of the file
      String[] l = { sc.nextLine(), sc.nextLine() };

      // Check if the first line starts with "<?xml version=", contains " encoding=",
      // and ends with "?>"
      // and if the second line equals "<AUTOSAR>"
      if (l[0].startsWith("<?xml version=") &&
          l[0].contains(" encoding=") &&
          l[0].endsWith("?>") &&
          l[1].equals("<AUTOSAR>")) {
        // If the lines are valid, return them
        return l;
      } else {
        // If the lines are not valid, throw an exception
        throw new AutosarFileException(f);
      }
    }

    /**
     * Counts the number of containers in an ARXML file
     * 
     * @param f The file to read from
     * @return The number of containers in the file
     * @throws FileNotFoundException if the file is not found
     */
    public static int countNoOfContainers(File f) throws FileNotFoundException {
      // Create a Scanner object to read from the file
      Scanner sc = new Scanner(f);

      // Initialize variables
      String x = sc.nextLine();
      x = sc.nextLine();
      int i = 0;

      // Loop until the end of the file is reached
      do {
        // Read the next line
        x = sc.nextLine();

        // Check if the line equals " </CONTAINER>"
        if (x.equals(" </CONTAINER>")) {
          // If it does, increment the counter
          i++;
        }
      } while (!(x.equals("</AUTOSAR>")));
      sc.close();
      // Return the number of containers found
      return i;
    }

    public static String modifyStringName(String old) {
      int i = old.indexOf(".arxml");
      String newName = old.substring(0, i);
      return newName + "_mod.arxml";
    }
  }
}