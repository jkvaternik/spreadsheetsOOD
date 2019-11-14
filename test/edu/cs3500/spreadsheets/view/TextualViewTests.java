package edu.cs3500.spreadsheets.view;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SimpleSpreadsheet;
import edu.cs3500.spreadsheets.model.ViewModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;

import static org.junit.Assert.assertEquals;

/**
 * Represents tests for {@link TextualView}.
 */
public class TextualViewTests {

  private SimpleSpreadsheet spreadsheetOne;
  private SimpleSpreadsheet spreadsheetTwo;
  private SimpleSpreadsheet spreadsheetThree;
  private SimpleSpreadsheet spreadsheetFour;
  private SimpleSpreadsheet spreadsheetFive;

  @Before
  public void init() {
    try {
      Readable fileReaderOne = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileSampleOne.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderOne =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReaderOne = new WorksheetReader();
      spreadsheetOne = worksheetReaderOne.read(builderOne, fileReaderOne);

      Readable fileReaderTwo = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileEmpty.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderTwo =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReaderTwo = new WorksheetReader();
      spreadsheetTwo = worksheetReaderTwo.read(builderTwo, fileReaderTwo);

      Readable fileReaderThree = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileSelfReferenceDirect." +
              "txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderThree =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReaderThree = new WorksheetReader();
      spreadsheetThree = worksheetReaderThree.read(builderThree, fileReaderThree);

      Readable fileReaderFour = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileSelfReferenceIndirect.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderFour =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReaderFour = new WorksheetReader();
      spreadsheetFour = worksheetReaderFour.read(builderFour, fileReaderFour);

      Readable fileReaderFive = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileSimpleSpread.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderFive =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReaderFive = new WorksheetReader();
      spreadsheetFive = worksheetReaderFive.read(builderFive, fileReaderFive);

    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    }
  }

  @Test
  public void testTextViewSampleOne() {
    try {
      PrintWriter p1 = new PrintWriter("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileCopySampleOne.txt");
      TextualView textView = new TextualView(p1, new ViewModel(spreadsheetOne));
      textView.makeVisible();
      p1.close();
      Readable fileReaderOne = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileCopySampleOne.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builder =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReader = new WorksheetReader();
      SimpleSpreadsheet spreadsheetCopy = worksheetReader.read(builder, fileReaderOne);
      assertEquals(spreadsheetOne.getNumRows(), spreadsheetCopy.getNumRows());
      assertEquals(spreadsheetOne.getNumColumns(), spreadsheetCopy.getNumColumns());

      for (int row = 1; row <= spreadsheetCopy.getNumRows(); row++) {
        for (int col = 1; col <= spreadsheetCopy.getNumColumns(); col++) {
          assertEquals(spreadsheetOne.getRawContents(new Coord(row, col)),
                  spreadsheetCopy.getRawContents(new Coord(row, col)));
          assertEquals(spreadsheetOne.getValue(new Coord(row, col)),
                  spreadsheetCopy.getValue(new Coord(row, col)));
        }
      }

    } catch (IOException e) {
      System.out.print("File not found.");
    }
  }

  @Test
  public void testTextViewEmpty() {
    try {
      PrintWriter p1 = new PrintWriter("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileCopyEmpty.txt");
      TextualView textView = new TextualView(p1, new ViewModel(spreadsheetTwo));
      textView.makeVisible();
      p1.close();
      Readable fileReader = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileCopyEmpty.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderOne =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReader = new WorksheetReader();
      SimpleSpreadsheet spreadsheetCopy = worksheetReader.read(builderOne, fileReader);
      assertEquals(spreadsheetTwo.getNumRows(), spreadsheetCopy.getNumRows());
      assertEquals(spreadsheetTwo.getNumColumns(), spreadsheetCopy.getNumColumns());

      for (int row = 1; row <= spreadsheetCopy.getNumRows(); row++) {
        for (int col = 1; col <= spreadsheetCopy.getNumColumns(); col++) {
          assertEquals(spreadsheetTwo.getRawContents(new Coord(row, col)),
                  spreadsheetCopy.getRawContents(new Coord(row, col)));
          assertEquals(spreadsheetTwo.getValue(new Coord(row, col)),
                  spreadsheetCopy.getValue(new Coord(row, col)));
        }
      }

    } catch (IOException e) {
      System.out.print("File not found.");
    }
  }

  @Test
  public void testTextViewSelfRefDirect() {
    try {
      PrintWriter p1 = new PrintWriter("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileCopySelfRefDirect.txt");
      TextualView textView = new TextualView(p1, new ViewModel(spreadsheetThree));
      textView.makeVisible();
      p1.close();
      Readable fileReader = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileCopySelfRefDirect.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderOne =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReader = new WorksheetReader();
      SimpleSpreadsheet spreadsheetCopy = worksheetReader.read(builderOne, fileReader);
      assertEquals(spreadsheetThree.getNumRows(), spreadsheetCopy.getNumRows());
      assertEquals(spreadsheetThree.getNumColumns(), spreadsheetCopy.getNumColumns());

      for (int row = 1; row <= spreadsheetCopy.getNumRows(); row++) {
        for (int col = 1; col <= spreadsheetCopy.getNumColumns(); col++) {
          assertEquals(spreadsheetThree.getRawContents(new Coord(row, col)),
                  spreadsheetCopy.getRawContents(new Coord(row, col)));
          assertEquals(spreadsheetThree.getValue(new Coord(row, col)),
                  spreadsheetCopy.getValue(new Coord(row, col)));
        }
      }

    } catch (IOException e) {
      System.out.print("File not found.");
    }
  }

  @Test
  public void testTextViewSelfRefIndirect() {
    try {
      PrintWriter p1 = new PrintWriter("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileCopySelfRefIndirect.txt");
      TextualView textView = new TextualView(p1, new ViewModel(spreadsheetFour));
      textView.makeVisible();
      p1.close();
      Readable fileReader = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileCopySelfRefIndirect.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderOne =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReader = new WorksheetReader();
      SimpleSpreadsheet spreadsheetCopy = worksheetReader.read(builderOne, fileReader);
      assertEquals(spreadsheetFour.getNumRows(), spreadsheetCopy.getNumRows());
      assertEquals(spreadsheetFour.getNumColumns(), spreadsheetCopy.getNumColumns());

      for (int row = 1; row <= spreadsheetCopy.getNumRows(); row++) {
        for (int col = 1; col <= spreadsheetCopy.getNumColumns(); col++) {
          assertEquals(spreadsheetFour.getRawContents(new Coord(row, col)),
                  spreadsheetCopy.getRawContents(new Coord(row, col)));
          assertEquals(spreadsheetFour.getValue(new Coord(row, col)),
                  spreadsheetCopy.getValue(new Coord(row, col)));
        }
      }

    } catch (IOException e) {
      System.out.print("File not found.");
    }
  }

  @Test
  public void testTextViewSimpleSpread() {
    try {
      PrintWriter p1 = new PrintWriter("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileCopySimpleSpread.txt");
      TextualView textView = new TextualView(p1, new ViewModel(spreadsheetFive));
      textView.makeVisible();
      p1.close();
      Readable fileReader = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileCopySimpleSpread.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderOne =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReader = new WorksheetReader();
      SimpleSpreadsheet spreadsheetCopy = worksheetReader.read(builderOne, fileReader);
      assertEquals(spreadsheetFive.getNumRows(), spreadsheetCopy.getNumRows());
      assertEquals(spreadsheetFive.getNumColumns(), spreadsheetCopy.getNumColumns());

      for (int row = 1; row <= spreadsheetCopy.getNumRows(); row++) {
        for (int col = 1; col <= spreadsheetCopy.getNumColumns(); col++) {
          assertEquals(spreadsheetFive.getRawContents(new Coord(row, col)),
                  spreadsheetCopy.getRawContents(new Coord(row, col)));
          assertEquals(spreadsheetFive.getValue(new Coord(row, col)),
                  spreadsheetCopy.getValue(new Coord(row, col)));
        }
      }

    } catch (IOException e) {
      System.out.print("File not found.");
    }
  }

  @Test
  public void testMakeVisible() {
    WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderOne =
            new SimpleSpreadsheet.Builder();
    SimpleSpreadsheet spreadsheet = builderOne
            .createCell(1, 1, "5.0")
            .createCell(3, 1, "Hello")
            .createCell(2, 2, "=(SUM A1:A5)")
            .createCell(4, 4, "=true")
            .createCell(27, 90, "=(CAPITALIZE A1:B12)")
            .createWorksheet();

    Appendable ap = new StringBuilder();

    View textView = new TextualView(ap, new ViewModel(spreadsheet));
    assertEquals("", ap.toString());

    textView.makeVisible();
    assertEquals("A1 5.0\n" + "C1 Hello\n" + "B2 =(SUM A1:A5)\n"
                    + "D4 =true\n" + "AA90 =(CAPITALIZE A1:B12)\n",
            ap.toString());

    //Add a cell to the worksheet and refresh the view. The appendable should not change.
    spreadsheet.setCellValue(new Coord(3, 3), "-12.7");
    textView.refresh();
    assertEquals("A1 5.0\n" + "C1 Hello\n" + "B2 =(SUM A1:A5)\n"
                    + "D4 =true\n" + "AA90 =(CAPITALIZE A1:B12)\n",
            ap.toString());
  }
}
