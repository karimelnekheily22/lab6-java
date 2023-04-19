import java.io.File;

public class EmptyAutosarFileException extends Exception {

  /**
   * Constructor for EmptyAutosarFileException
   * 
   * @param file The file that is empty
   */
  public EmptyAutosarFileException(File file) {
    // Call the superclass constructor with a custom error message
    super("File :" + file.getAbsolutePath() + " ,is empty");
  }
}
