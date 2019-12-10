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
    assertEquals("6.0", spreadsheetOne.getRawContents(new Coord(1, 1)));
    spreadsheetOne.clearCell(new Coord(1, 1));
    assertEquals(null, spreadsheetOne.getRawContents(new Coord(1, 1)));

    assertEquals("=A1", spreadsheetOne.getRawContents(new Coord(2, 1)));
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

    assertEquals("6.000000", spreadsheetOne.getValue(new Coord(1, 1)));
    spreadsheetOne.setCellValue(new Coord(1, 1), "=B1");
    assertEquals("#ERR: This cell contains a cyclical reference.",
            spreadsheetOne.getValue(new Coord(1, 1)));

    spreadsheetOne.setCellValue(new Coord(1, 1), "=(SUM A1 1)");
    assertEquals("#ERR: This cell contains a cyclical reference.", spreadsheetOne.getValue(
            new Coord(1, 1)));

    assertEquals("", spreadsheetTwo.getValue(new Coord(1, 1)));
    spreadsheetTwo.setCellValue(new Coord(1, 1), "=(PRODUCT (SUM 2 1) 3)");
    assertEquals("9.000000", spreadsheetTwo.getValue(new Coord(1, 1)));
  }

  @Test
  public void testGetNumRows() {
    assertEquals(3, spreadsheetOne.getNumRows());

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
    assertEquals("6.000000", spreadsheetOne.getValue(new Coord(1, 1)));
    assertEquals("Jack says \\\"Hi\\\" and Jill has one backslash \\\\ here",
            spreadsheetOne.getValue(new Coord(5, 1)));

    assertEquals("true", spreadsheetOne.getValue(new Coord(1, 2)));
  }

  @Test
  public void testGetRawContents() {
    assertEquals("", spreadsheetTwo.getRawContents(new Coord(1, 1)));
    assertEquals("6.0", spreadsheetOne.getRawContents(new Coord(1, 1)));
    assertEquals("Jack says \"Hi\" and Jill has one backslash \\ here",
            spreadsheetOne.getRawContents(new Coord(5, 1)));

    assertEquals("=(< A3 10)", spreadsheetOne.getRawContents(new Coord(2, 3)));

    assertEquals("=(SUM A1 1)", spreadsheetThree.getRawContents(
            new Coord(2, 1)));
  }

  @Test
  public void testGetRowHeight() {
    for (int i = 0; i < spreadsheetOne.getNumRows(); i++) {
      assertEquals(25, spreadsheetOne.getRowHeight(i + 1));
    }
    for (int i = 0; i < spreadsheetTwo.getNumRows(); i++) {
      assertEquals(25, spreadsheetTwo.getRowHeight(i + 1));
    }
    for (int i = 0; i < spreadsheetThree.getNumRows(); i++) {
      assertEquals(25, spreadsheetThree.getRowHeight(i + 1));
    }
    for (int i = 0; i < spreadsheetFour.getNumRows(); i++) {
      assertEquals(25, spreadsheetFour.getRowHeight(i + 1));
    }
    assertEquals(25, spreadsheetFour.getRowHeight(70));

    spreadsheetOne.setRowHeight(4, 100);
    assertEquals(100, spreadsheetOne.getRowHeight(4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetRowHeightZero() {
    spreadsheetOne.getRowHeight(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetRowHeightNeg() {
    spreadsheetOne.getRowHeight(-1);
  }

  @Test
  public void testGetColWidth() {
    for (int i = 0; i < spreadsheetOne.getNumColumns(); i++) {
      assertEquals(75, spreadsheetOne.getColWidth(i + 1));
    }
    for (int i = 0; i < spreadsheetTwo.getNumRows(); i++) {
      assertEquals(75, spreadsheetTwo.getColWidth(i + 1));
    }
    for (int i = 0; i < spreadsheetThree.getNumRows(); i++) {
      assertEquals(75, spreadsheetThree.getColWidth(i + 1));
    }
    for (int i = 0; i < spreadsheetFour.getNumRows(); i++) {
      assertEquals(75, spreadsheetFour.getColWidth(i + 1));
    }
    assertEquals(75, spreadsheetFour.getColWidth(52));

    spreadsheetOne.setColWidth(4, 25);
    assertEquals(25, spreadsheetOne.getRowHeight(4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetColWidthZero() {
    spreadsheetTwo.getColWidth(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetColWidthNeg() {
    spreadsheetTwo.getColWidth(-1);
  }

  @Test
  public void testSetRowHeight() {
    for (int i = 0; i < 10; i++) {
      spreadsheetOne.setRowHeight(i + 1, 100);
    }
    spreadsheetOne.setRowHeight(9, 25);

    for (int i = 0; i < 10; i++) {
      if (i == 8) {
        assertEquals(25, spreadsheetOne.getRowHeight(i + 1));
      } else {
        assertEquals(100, spreadsheetOne.getRowHeight(i + 1));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSetRowHeightZero() {
    spreadsheetOne.setRowHeight(100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSetRowHeightNeg() {
    spreadsheetOne.setRowHeight(100, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSetRowHeightZeroCoord() {
    spreadsheetOne.setRowHeight(0, 100);
  }

  @Test
  public void testSetColWidth() {
    for (int i = 0; i < 10; i++) {
      spreadsheetOne.setColWidth(i + 1, 100);
    }
    spreadsheetOne.setColWidth(9, 25);

    for (int i = 0; i < 10; i++) {
      if (i == 8) {
        assertEquals(25, spreadsheetOne.getColWidth(i + 1));
      } else {
        assertEquals(100, spreadsheetOne.getColWidth(i + 1));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSetColWidthZero() {
    spreadsheetOne.setColWidth(100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSetColWidthNeg() {
    spreadsheetOne.setColWidth(100, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSetColWidthZeroCoord() {
    spreadsheetOne.setColWidth(0, 100);
  }

  @Test
  public void testMaxRowChanged() {
    assertEquals(0, spreadsheetOne.maxRowChanged());
    spreadsheetOne.setRowHeight(12, 125);
    assertEquals(12, spreadsheetOne.maxRowChanged());
    spreadsheetOne.setRowHeight(12, 25);
    assertEquals(12, spreadsheetOne.maxRowChanged());
    spreadsheetOne.setRowHeight(1, 150);
    assertEquals(12, spreadsheetOne.maxRowChanged());
    spreadsheetOne.setRowHeight(1, 25);
    assertEquals(12, spreadsheetOne.maxRowChanged());
  }

  @Test
  public void testMaxColChanged() {
    assertEquals(0, spreadsheetOne.maxColChanged());
    spreadsheetOne.setColWidth(12, 125);
    assertEquals(12, spreadsheetOne.maxColChanged());
    spreadsheetOne.setColWidth(12, 25);
    assertEquals(12, spreadsheetOne.maxColChanged());
    spreadsheetOne.setColWidth(1, 150);
    assertEquals(12, spreadsheetOne.maxColChanged());
    spreadsheetOne.setColWidth(1, 25);
    assertEquals(12, spreadsheetOne.maxColChanged());
  }

  @Test
  public void copyPasteContents() {
    spreadsheetOne.copyPasteContents(new Coord(1, 1), new Coord(2, 2));
    assertEquals("6.0", spreadsheetOne.getRawContents(new Coord(2, 2)));
    spreadsheetOne.copyPasteContents(new Coord(2, 2), new Coord(5, 2));
    assertEquals("6.0", spreadsheetOne.getRawContents(new Coord(5, 2)));

    spreadsheetOne.copyPasteContents(new Coord(2, 1), new Coord(3, 3));
    assertEquals("=A1", spreadsheetOne.getRawContents(new Coord(3, 3)));
    spreadsheetOne.copyPasteContents(new Coord(2, 1), new Coord(3, 1));
    assertEquals("=B1", spreadsheetOne.getRawContents(new Coord(3, 1)));
    spreadsheetOne.copyPasteContents(new Coord(2, 1), new Coord(2, 3));
    assertEquals("=A3", spreadsheetOne.getRawContents(new Coord(2, 3)));

    spreadsheetOne.setCellValue(new Coord(4, 1), "=A$1");
    spreadsheetOne.copyPasteContents(new Coord(4, 1), new Coord(5, 1));
    assertEquals("=B$1", spreadsheetOne.getRawContents(new Coord(5, 1)));
    assertEquals("6.000000", spreadsheetOne.getValue(new Coord(5, 1)));
    spreadsheetOne.copyPasteContents(new Coord(4, 1), new Coord(4, 2));
    assertEquals("=A$1", spreadsheetOne.getRawContents(new Coord(4, 2)));
    assertEquals("6.000000", spreadsheetOne.getValue(new Coord(4, 2)));

    spreadsheetOne.setCellValue(new Coord(2, 5), "=$B1");
    spreadsheetOne.copyPasteContents(new Coord(2, 5), new Coord(5, 1));
    assertEquals("=$B1", spreadsheetOne.getRawContents(new Coord(5, 1)));
    assertEquals("6.000000", spreadsheetOne.getValue(new Coord(5, 1)));
    spreadsheetOne.copyPasteContents(new Coord(2, 5), new Coord(2, 6));
    assertEquals("=$B2", spreadsheetOne.getRawContents(new Coord(2, 6)));
    assertEquals("6.000000", spreadsheetOne.getValue(new Coord(2, 6)));

    spreadsheetOne.setCellValue(new Coord(4, 1), "=$A$1");
    spreadsheetOne.copyPasteContents(new Coord(4, 1), new Coord(5, 1));
    assertEquals("=$A$1", spreadsheetOne.getRawContents(new Coord(5, 1)));
    assertEquals("6.000000", spreadsheetOne.getValue(new Coord(5, 1)));
    spreadsheetOne.copyPasteContents(new Coord(4, 1), new Coord(4, 2));
    assertEquals("=$A$1", spreadsheetOne.getRawContents(new Coord(4, 2)));
    assertEquals("6.000000", spreadsheetOne.getValue(new Coord(4, 2)));
  }
}
