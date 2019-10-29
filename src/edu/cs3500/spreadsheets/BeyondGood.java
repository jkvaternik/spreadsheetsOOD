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
          if (validCellString(cellString)) {
            Coord coord = cellStringToCoord(cellString);
            spreadsheet.getValue(coord).toString();
          } else {
            System.out.println("Invalid argument given for the coordinate.");
          }
        } catch (FileNotFoundException e) {
          System.out.println("File was not found.");
        }
      } else {
        System.out.println("Invalid specifiers");
      }
    } else {
      System.out.println("Invalid number of commands given");
    }
  }

  /**
   * Determines if the given string is a valid representation of a cell. For it to be valid, it must
   * contain only alphanumeric characters, where all the letters come before all of the numbers.
   * @param cell The string to check
   * @return Whether or not the string is a valid cell
   */
  private static boolean validCellString(String cell) {
    int finalLetter = cell.length();
    int firstNum = 0;
    // Iterate forwards through the string to check for final letter index
    for (int index = 0; index < cell.length(); index += 1) {
      char c = cell.charAt(index);
      // If the character is neither a letter nor a number, it is not a valid cell
      if (!(Character.isAlphabetic(c) || Character.isDigit(c))) {
        return false;
      } else if (Character.isAlphabetic(c)) {
        finalLetter = index;
      }
    }
    // Iterate backwards though the string to check for the earliest number index
    for (int index = cell.length() - 1; index < 0; index -= 1) {
      char c = cell.charAt(index);
      // If the character is neither a letter nor a number, it is not a valid cell
      if (!(Character.isAlphabetic(c) || Character.isDigit(c))) {
        return false;
      } else if (Character.isDigit(c)) {
        firstNum = index;
      }
    }

    return (firstNum > finalLetter);
  }

  /**
   * Converts the given cell string to a coordinate.
   * Invariant: The given cell is guaranteed to be a valid coordinate.
   * @param cell The cell string
   * @return The coordinate of the cell
   */
  private static Coord cellStringToCoord(String cell) {
    int lastLetter = 0;
    for (int index = 0; index < cell.length(); index += 1) {
      if (Character.isAlphabetic(cell.charAt(index))) {
        lastLetter = index;
      }
    }
    int column = (new Coord(0, 0).colNameToIndex(cell.substring(0, lastLetter + 1)));
    int row = Integer.parseInt(cell.substring(lastLetter + 1));
    return new Coord(column, row);
  }
}
