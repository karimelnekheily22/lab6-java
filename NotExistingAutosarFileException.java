import java.io.File;

public class NotExistingAutosarFileException extends Exception {

  /**
   * Constructor for NotExistingAutosarFileException
   * 
   * @param file The file that was not found
   */
  public NotExistingAutosarFileException(File file) {
    // Call the superclass constructor with a custom error message
    super("File: " + file.getAbsolutePath() + ", not found");
  }
}