package edu.cs3500.spreadsheets.model;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link SimpleSpreadsheet}.
 */
public class SimpleSpreadsheetModelTest {

  private SimpleSpreadsheet spreadsheetOne;
  private SimpleSpreadsheet spreadsheetTwo;
  private SimpleSpreadsheet spreadsheetThree;
  private SimpleSpreadsheet spreadsheetFour;

  @Before
  public void init() {
    try {
      Readable fileReaderOne = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileCopySampleOne.txt");
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
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileSelfReferenceIndirect." +
              "txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderThree =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReaderThree = new WorksheetReader();
      spreadsheetThree = worksheetReaderThree.read(builderThree, fileReaderThree);

      Readable fileReaderFour = new FileReader("/Users/jaimekvaternik/Documents/NEU/" +
              "Fall 2019/OOD/spreadsheetsOOD/resources/textFiles/fileSimpleSpread.txt");
      WorksheetReader.WorksheetBuilder<SimpleSpreadsheet> builderFour =
              new SimpleSpreadsheet.Builder();
      WorksheetReader worksheetReaderFour = new WorksheetReader();
      spreadsheetFour = worksheetReaderFour.read(builderFour, fileReaderFour);

    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    }
  }

  @Test
  public void testCreateEmpty() {
    assertEquals(0, spreadsheetTwo.getNumColumns());
    assertEquals(0, spreadsheetTwo.getNumRows());
  }

  @Test
  public void testCreateSpreadsheet() {
    assertEquals(2, spreadsheetFour.getNumColumns());
    assertEquals(2, spreadsheetFour.getNumRows());
    assertEquals("3.000000", spreadsheetFour.getValue(new Coord(1, 1)));
    assertEquals("4.000000", spreadsheetFour.getValue(new Coord(2, 1)));
    assertEquals("3.000000", spreadsheetFour.getValue(new Coord(1, 2)));
    assertEquals("7.000000", spreadsheetFour.getValue(new Coord(2, 2)));
  }

  @Test
  public void testClearCell() {
    assertEquals("3", spreadsheetOne.getRawContents(new Coord(1, 1)));
    spreadsheetOne.clearCell(new Coord(1, 1));
    assertEquals(null, spreadsheetOne.getRawContents(new Coord(1, 1)));

    assertEquals("4", spreadsheetOne.getRawContents(new Coord(2, 1)));
    spreadsheetOne.clearCell(new Coord(2, 1));
    assertEquals(null, spreadsheetOne.getRawContents(new Coord(1, 1)));

    assertEquals("", spreadsheetTwo.getRawContents(new Coord(1, 1)));
    spreadsheetTwo.clearCell(new Coord(1, 1));
    assertEquals(null, spreadsheetTwo.getRawContents(new Coord(1, 1)));
  }

  @Test
  public void testSetCellValue() {
    // if a coordinate does not exist, model.getValue returns an empty StringValue
    assertEquals("", spreadsheetOne.getValue(new Coord(5, 5)));
    spreadsheetOne.setCellValue(new Coord(5, 5), "=(SUM 2 3)");
    assertEquals("5.000000", spreadsheetOne.getValue(new Coord(5, 5)));

    assertEquals("3.000000", spreadsheetOne.getValue(new Coord(1, 1)));
    spreadsheetOne.setCellValue(new Coord(1, 1), "=B1");
    assertEquals("4.000000", spreadsheetOne.getValue(new Coord(1, 1)));

    spreadsheetOne.setCellValue(new Coord(1, 1), "=(SUM A1 1)");
    assertEquals("#ERR: This cell contains a cyclical reference.", spreadsheetOne.getValue(
            new Coord(1, 1)));

    assertEquals("", spreadsheetTwo.getValue(new Coord(1, 1)));
    spreadsheetTwo.setCellValue(new Coord(1, 1), "=(PRODUCT (SUM 2 1) 3)");
    assertEquals("9.000000", spreadsheetTwo.getValue(new Coord(1, 1)));
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
    assertEquals("", spreadsheetTwo.getValue(new Coord(1, 1)));
    assertEquals("3.000000", spreadsheetOne.getValue(new Coord(1, 1)));
    assertEquals("Jack says \\\"Hi\\\" and Jill has one backslash \\\\ here",
            spreadsheetOne.getValue(new Coord(5, 1)));

    assertEquals("false", spreadsheetOne.getValue(new Coord(2, 3)));

    assertEquals("#ERR: This cell contains a cyclical reference.",
            spreadsheetThree.getValue(new Coord(1, 1)));
  }

  @Test
  public void testGetRawContents() {
    assertEquals("", spreadsheetTwo.getRawContents(new Coord(1, 1)));
    assertEquals("3", spreadsheetOne.getRawContents(new Coord(1, 1)));
    assertEquals("Jack says \"Hi\" and Jill has one backslash \\ here",
            spreadsheetOne.getRawContents(new Coord(5, 1)));

    assertEquals("=(< A3 10)", spreadsheetOne.getRawContents(new Coord(2, 3)));

    assertEquals("=(SUM A1 1)", spreadsheetThree.getRawContents(
            new Coord(2, 1)));
  }

}
