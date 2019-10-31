package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet.Builder;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    /*
      TODO: For now, look in the args array to obtain a filename and a cell name,
      - read the file and build a model from it, 
      - evaluate all the cells, and
      - report any errors, or print the evaluated value of the requested cell.
    */
    if (args.length == 4) {
      if (args[0].equals("-in") && args[2].equals("-eval")) {
        try {
          Readable fileReader = new FileReader(args[1]);
          WorksheetBuilder<SimpleSpreadsheet> builder = new Builder();
          WorksheetReader worksheetReader = new WorksheetReader();
          SimpleSpreadsheet spreadsheet = worksheetReader.read(builder, fileReader);

          if (spreadsheet.getErrorCoords().size() != 0) {
            for (Coord c : spreadsheet.getErrorCoords()) {
              System.out.println("Error in " + Coord.colIndexToName(c.col) + c.row + ": "
                      + spreadsheet.getValue(c));
            }
          }

          String cellString = args[3];
          if (Coord.validCellString(cellString)) {
            Coord coord = Coord.cellStringToCoord(cellString);
            System.out.print(spreadsheet.getValue(coord));
          } else {
            System.out.print("Invalid argument given for the coordinate.");
          }
        } catch (FileNotFoundException e) {
          System.out.print("File was not found.");
        }
      } else {
        System.out.print("Invalid specifiers");
      }
    } else {
      System.out.print("Invalid number of commands given.");
    }
  }
}
