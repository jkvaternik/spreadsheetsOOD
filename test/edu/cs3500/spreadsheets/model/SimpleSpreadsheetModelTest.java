package edu.cs3500.spreadsheets.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import edu.cs3500.spreadsheets.model.cell.formula.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.DoubleValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.ErrorValue;
import edu.cs3500.spreadsheets.model.cell.formula.value.StringValue;

/**
 * Tests for {@link SimpleSpreadsheet}.
 */
public class SimpleSpreadsheetModelTest {

  private SimpleSpreadsheet spreadsheetOne;
  private SimpleSpreadsheet spreadsheetTwo;
  private SimpleSpreadsheet spreadsheetThree;

  @Before
  public void init() {
    try {
      Readable fileReaderOne = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/test/edu/cs3500/textFiles/fileSampleOne.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderOne =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReaderOne = new WorksheetReader();
      spreadsheetOne = worksheetReaderOne.read(builderOne, fileReaderOne);

      Readable fileReaderTwo = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/test/edu/cs3500/textFiles/fileEmpty.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderTwo =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReaderTwo = new WorksheetReader();
      spreadsheetTwo = worksheetReaderTwo.read(builderTwo, fileReaderTwo);

      Readable fileReaderThree = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/test/edu/cs3500/textFiles/fileSelfReferenceIndirect.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderThree =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReaderThree = new WorksheetReader();
      spreadsheetThree = worksheetReaderThree.read(builderThree, fileReaderThree);
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    }
  }

  @Test
  public void testClearCell() {
    assertEquals("3", spreadsheetOne.getRawContents(new Coord(1, 1)));
    spreadsheetOne.clearCell(new Coord(1, 1));
    assertEquals("", spreadsheetOne.getRawContents(new Coord(1, 1)));

    assertEquals("4", spreadsheetOne.getRawContents(new Coord(2, 1)));
    spreadsheetOne.clearCell(new Coord(2, 1));
    assertEquals("", spreadsheetOne.getRawContents(new Coord(1, 1)));

    assertEquals("", spreadsheetTwo.getRawContents(new Coord(1, 1)));
    spreadsheetTwo.clearCell(new Coord(1, 1));
    assertEquals("", spreadsheetTwo.getRawContents(new Coord(1, 1)));
  }

  @Test
  public void testSetCellValue() {
    // if a coordinate does not exist, model.getValue returns an empty StringValue
    assertEquals(new StringValue(""), spreadsheetOne.getValue(new Coord(5, 5)));
    spreadsheetOne.setCellValue(new Coord(5, 5), "=(SUM 2 3)");
    assertEquals(new DoubleValue(5.0), spreadsheetOne.getValue(new Coord(5, 5)));

    assertEquals(new DoubleValue(3.0), spreadsheetOne.getValue(new Coord(1, 1)));
    spreadsheetOne.setCellValue(new Coord(1, 1), "=B1");
    assertEquals(new DoubleValue(4.0), spreadsheetOne.getValue(new Coord(1, 1)));

    spreadsheetOne.setCellValue(new Coord(1, 1), "=(SUM A1 1)");
    assertEquals(new ErrorValue(new IllegalArgumentException(
            "This cell contains a cyclical reference.")), spreadsheetOne.getValue(
            new Coord(1, 1)));

    assertEquals(new StringValue(""), spreadsheetTwo.getValue(new Coord(1, 1)));
    spreadsheetTwo.setCellValue(new Coord(1, 1), "=(PRODUCT (SUM 2 1) 3)");
    assertEquals(new DoubleValue(9.0), spreadsheetTwo.getValue(new Coord(1, 1)));
  }

  @Test
  public void testGetNumRows() {
    assertEquals(4, spreadsheetOne.getNumRows());

    assertEquals(0, spreadsheetTwo.getNumRows());
    spreadsheetTwo.setCellValue(new Coord(2, 3), "2");
    assertEquals(3, spreadsheetTwo.getNumRows());
    spreadsheetTwo.setCellValue(new Coord(5, 5), "yeet skeet");
    assertEquals(5, spreadsheetTwo.getNumRows());
    spreadsheetTwo.setCellValue(new Coord(7, 5), "=G5");
    assertEquals(5, spreadsheetTwo.getNumRows());

    assertEquals(1, spreadsheetThree.getNumRows());
  }

  @Test
  public void testGetNumColumns() {
    assertEquals(5, spreadsheetOne.getNumColumns());

    assertEquals(0, spreadsheetTwo.getNumColumns());
    spreadsheetTwo.setCellValue(new Coord(2, 3), "2");
    assertEquals(2, spreadsheetTwo.getNumColumns());
    spreadsheetTwo.setCellValue(new Coord(1, 6), "hi");
    assertEquals(2, spreadsheetTwo.getNumColumns());
    spreadsheetTwo.setCellValue(new Coord(7, 4), "=G4");
    assertEquals(7, spreadsheetTwo.getNumColumns());

    assertEquals(2, spreadsheetThree.getNumColumns());
  }

  @Test
  public void testGetValue() {
    assertEquals(new StringValue(""), spreadsheetTwo.getValue(new Coord(1, 1)));
    assertEquals(new DoubleValue(3.0), spreadsheetOne.getValue(new Coord(1, 1)));
    assertEquals(new StringValue("Jack says \"Hi\" and Jill has one backslash \\ here"),
            spreadsheetOne.getValue(new Coord(5, 1)));

    assertEquals(new BooleanValue(false), spreadsheetOne.getValue(new Coord(2, 3)));

    assertEquals(new ErrorValue(
                    new IllegalArgumentException("This cell contains a cyclical reference.")),
            spreadsheetThree.getValue(new Coord(2, 1)));
  }

  @Test
  public void testGetRawContents() {
    assertEquals("", spreadsheetTwo.getRawContents(new Coord(1, 1)));
    assertEquals("3", spreadsheetOne.getRawContents(new Coord(1, 1)));
    assertEquals("Jack says \"Hi\" and Jill has one backslash \\ here",
            spreadsheetOne.getRawContents(new Coord(5, 1)));

    assertEquals("=(< A3 10)", spreadsheetOne.getRawContents(new Coord(2, 3)));

    assertEquals("=(SUM A1 1)", spreadsheetThree.getRawContents(new Coord(2, 1)));
  }

}
