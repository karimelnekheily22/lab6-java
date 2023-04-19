import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Container implements Comparable<Container> {

  private String ID;
  private String longName;
  private String shortName;

  /**
   * Constructor for Container class
   * 
   * @param sc Scanner object to read from file
   * @param f  The file being read
   * @throws DefAutosarFileException if the file is defected
   */
  public Container(Scanner sc, File f) throws AutosarFileException {
    // Read the first line and check if it starts with "<CONTAINER UUID=" and ends
    // with ">"
    String Line = sc.nextLine();
    if (Line.startsWith(" <CONTAINER UUID=\"") && Line.endsWith("\">")) {
      // If the line is valid, set the ID
      this.ID = Line;
    } else {
      // If the line is not valid, throw an exception
      throw new AutosarFileException(f);
    }

    // Read the next line and check if it starts with "<SHORT-NAME>" and ends with
    // "</SHORT-NAME>"
    Line = sc.nextLine();
    if (Line.startsWith(" <SHORT-NAME>") && Line.endsWith("</SHORT-NAME>")) {
      // If the line is valid, extract and set the shortName
      this.shortName = Line;
    } else {
      // If the line is not valid, throw an exception
      throw new AutosarFileException(f);
    }

    // Read the next line and check if it starts with "<LONG-NAME>" and ends with
    // "</LONG-NAME>"
    Line = sc.nextLine();
    if (Line.startsWith(" <LONG-NAME>") && Line.endsWith("</LONG-NAME>")) {
      // If the line is valid, set the longName
      this.longName = Line;
    } else {
      // If the line is not valid, throw an exception
      throw new AutosarFileException(f);
    }

    // Read the next line and check if it is " </CONTAINER>"
    Line = sc.nextLine();
    if (!Line.equals(" </CONTAINER>")) {
      // If the line is not valid, throw an exception
      throw new AutosarFileException(f);
    }
  }

  /**
   * @return String return the ID
   */
  public String getID() {
    return ID;
  }

  /**
   * @return String return the longName
   */
  public String getLongName() {
    return longName;
  }

  /**
   * @return String return the shortName
   */
  public String getShortName() {
    return shortName;
  }

  @Override
  public int compareTo(Container c) {
    return this.shortName.compareTo(c.shortName);
  }

  /**
   * Writes the container to the given PrintWriter
   * 
   * @param p the PrintWriter to write to
   */
  public void writeContainer(PrintWriter p) {
    // Print the ID
    p.println(ID);
    // Print the short name
    p.println(this.shortName);
    // Print the long name
    p.println(longName);
    // Print the closing container tag
    p.println(" </CONTAINER>");
  }
}
