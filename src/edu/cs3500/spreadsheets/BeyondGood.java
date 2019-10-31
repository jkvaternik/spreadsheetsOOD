package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet.Builder;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

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

          String cellString = args[3];
          if (new Coord(1, 1).validCellString(cellString)) {
            Coord coord = new Coord(1, 1).cellStringToCoord(cellString);
            System.out.print(spreadsheet.getValue(coord).toString());
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
      System.out.print("Invalid number of commands given");
    }
  }
}
