package edu.cs3500.spreadsheets;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import edu.cs3500.spreadsheets.controller.Controller;
import edu.cs3500.spreadsheets.controller.ControllerAdapter;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet.Builder;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.ViewModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.providers.ModelToView;
import edu.cs3500.spreadsheets.view.ModelToViewImpl;
import edu.cs3500.spreadsheets.view.TextualView;
import edu.cs3500.spreadsheets.view.View;
import edu.cs3500.spreadsheets.view.VisualEditView;
import edu.cs3500.spreadsheets.view.VisualReadView;

/**
 * The main class for our program.
 */
public class BeyondGood {

  /**
   * The main entry point.
   *
   * @param args any command-line argumentsB
   */
  public static void main(String[] args) {
    if (args.length == 1) {
      if (args[0].equals("-gui")) {
        WorksheetBuilder<SimpleSpreadsheet> builder = new Builder();
        SpreadsheetModel spreadsheet = builder.createWorksheet();
        ViewModel viewModel = new ViewModel(spreadsheet);
        View view = new VisualReadView(viewModel);
        view.makeVisible();
      } else if (args[0].equals("-edit")) {
        WorksheetBuilder<SimpleSpreadsheet> builder = new Builder();
        SpreadsheetModel spreadsheet = builder.createWorksheet();
        ViewModel viewModel = new ViewModel(spreadsheet);
        View view = new VisualEditView(viewModel);
        view.makeVisible();
        Controller controller = new Controller(spreadsheet, view);
      } else if (args[0].equals("-provider")) {
        SimpleSpreadsheet spreadsheet = new SimpleSpreadsheet.Builder().createWorksheet();
        //To be able to create the empty JTable using their view implementation, we need to make
        //sure that the model is not empty without erroring upon launch
        spreadsheet.setCellValue(new Coord(50, 50), "");
        ModelToView modelToView = new ModelToViewImpl(spreadsheet);
        ControllerAdapter controllerAdapter = new ControllerAdapter(spreadsheet);
      } else {
        System.out.print("Invalid command");
      }
    } else if (args.length == 3) {
      if (args[0].equals("-in") && args[2].equals("-gui")) {
        try {
          Readable fileReader = new FileReader(args[1]);
          WorksheetBuilder<SimpleSpreadsheet> builder = new Builder();
          WorksheetReader worksheetReader = new WorksheetReader();
          SimpleSpreadsheet spreadsheet = worksheetReader.read(builder, fileReader);
          ViewModel viewModel = new ViewModel(spreadsheet);
          View view = new VisualReadView(viewModel);
          view.makeVisible();
        } catch (FileNotFoundException e) {
          System.out.print("File was not found.");
        }
      } else if (args[0].equals("-in") && args[2].equals("-edit")) {
        try {
          Readable fileReader = new FileReader(args[1]);
          WorksheetBuilder<SimpleSpreadsheet> builder = new Builder();
          WorksheetReader worksheetReader = new WorksheetReader();
          SimpleSpreadsheet spreadsheet = worksheetReader.read(builder, fileReader);
          ViewModel viewModel = new ViewModel(spreadsheet);
          View view = new VisualEditView(viewModel);
          view.makeVisible();
          Controller controller = new Controller(spreadsheet, view);
        } catch (FileNotFoundException e) {
          System.out.print("File was not found.");
        }
      } else if (args[0].equals("-in") && args[2].equals("-provider")) {
        try {
          Readable fileReader = new FileReader(args[1]);
          WorksheetBuilder<SimpleSpreadsheet> builder = new Builder();
          WorksheetReader worksheetReader = new WorksheetReader();
          SimpleSpreadsheet spreadsheet = worksheetReader.read(builder, fileReader);
          ModelToView modelToView = new ModelToViewImpl(spreadsheet);
          ControllerAdapter controllerAdapter = new ControllerAdapter(spreadsheet);
        } catch (FileNotFoundException e) {
          System.out.print("File was not found.");
        }
      } else {
        System.out.print("Invalid command");
      }
    } else if (args.length == 4) {
      if (args[0].equals("-in") && args[2].equals("-eval")) {
        try {
          Readable fileReader = new FileReader(args[1]);
          WorksheetBuilder<SimpleSpreadsheet> builder = new Builder();
          WorksheetReader worksheetReader = new WorksheetReader();
          SimpleSpreadsheet spreadsheet = worksheetReader.read(builder, fileReader);

          if (spreadsheet.getErrorCoords().size() != 0) {
            for (Coord c : spreadsheet.getErrorCoords()) {
              System.out.println("Error in " + Coord.colIndexToName(c.col) + c.row + ": "
                      // Use substring to get the exception message
                      + spreadsheet.getValue(c).substring(6));
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
      } else if (args[0].equals("-in") && args[2].equals("-save")) {
        try {
          Readable fileReader = new FileReader(args[1]);
          WorksheetBuilder<SimpleSpreadsheet> builder = new Builder();
          WorksheetReader worksheetReader = new WorksheetReader();
          SimpleSpreadsheet spreadsheet = worksheetReader.read(builder, fileReader);
          ViewModel viewModel = new ViewModel(spreadsheet);

          PrintWriter fileWriter = new PrintWriter(args[3]);
          View view = new TextualView(fileWriter, viewModel);
          view.makeVisible();
          fileWriter.close();
        } catch (FileNotFoundException e) {
          System.out.print("File was not found.");
        }
      } else {
        System.out.print("Invalid command");
      }
    } else {
      System.out.print("Invalid number of commands given.");
    }
  }
}
