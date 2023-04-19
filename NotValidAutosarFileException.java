public class NotValidAutosarFileException extends Exception {

  /**
   * Constructor for NotVaildAutosarFileException
   * 
   * @param fileName The name of the file that is not valid
   */
  public NotValidAutosarFileException(String fileName) {
    // Call the superclass constructor with a custom error message
    super("Invalid file name " + fileName + "\nThe extension must be '.arxml'");
  }
}