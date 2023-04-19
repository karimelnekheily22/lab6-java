import java.io.File;

public class AutosarFileException extends Exception {

  /**
   * Constructor for AutosarFileException
   * 
   * @param file The file that is defected
   */
  public AutosarFileException(File file) {
    // Call the superclass constructor with a custom error message
    super(
        "Defected autosar file: " +
            file.getAbsolutePath() +
            "\nFile isn't readable.");
  }
}
