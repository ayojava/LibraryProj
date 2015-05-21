/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.export.book;

import com.javasoft.pgproj.entity.Book;
import com.javasoft.pgproj.export.ExcelProcessor;
import com.javasoft.pgproj.status.EntityStatus;
import com.javasoft.pgproj.util.FileUtil;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author ayojava
 */
public class AllBooksExcelReport extends ExcelProcessor {

    private String[] columnHeaders = {"S/N", "NAME", "AUTHOR", "PUBLISHER", "BOOK CODE", "SHELVE NAME", "REGISTERED DATE", "STATUS"};
    private String[] SheetHeaders = {"LIST OF LIBRARY BOOKS"};
    private List<Book> allBooks;
    private ByteArrayOutputStream outStream;

    public AllBooksExcelReport(String sheetName, List<Book> allBooks) {
        setCurrentSheet(sheetName);
        this.allBooks = allBooks;
    }

    @Override
    public void populateExcelSheet(boolean sendEmail) {
        outStream = new ByteArrayOutputStream();
        populateSheetHeaders(SheetHeaders);
        populateColumnHeaders(columnHeaders);
        try {
            writeExcelData();
            if (sendEmail) {
                sendFileAsAttachment(outStream);
            }

        } catch (Exception ex) {
            System.out.println("Exception ::: " + ex);
        } finally {
            try {
                outStream.close();
            } catch (IOException ex) {
                System.out.println("Exception in Finally Block" + ex);
            }
        }
    }

    public void writeExcelData() throws IOException {
        if (allBooks == null || allBooks.isEmpty()) {
            return;
        }
        int currentPosition = getRowPosition();
        int no = 1;

        for (Book book : allBooks) {
            if (book == null) {
                continue;
            }

            String[] rowValues = {
                String.valueOf(no), book.getBookName(), book.getBookAuthor(), book.getPublisherName(), book.getBookCode(),
                book.getBookShelve().getShelveName(), DateFormatUtils.format(book.getRegisteredDate(), "dd-MM-yyyy"),
                EntityStatus.getStatusName(book.getEntityState().getStatus())
            };

            Row row = getCurrentSheet().createRow(currentPosition);
            for (int i = 0; i < rowValues.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(getCellContentsStyle());
                cell.setCellValue(rowValues[i]);
            }
            currentPosition++;
            no++;
        }
        writeTo(outStream);

        FileOutputStream fileStream = new FileOutputStream(FileUtil.createFile(null, "LMS_Books_" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + ".xls"));
        outStream.writeTo(fileStream);
        outStream.flush();
        if (fileStream != null) {
            fileStream.close();
        }
    }

    private void sendFileAsAttachment(ByteArrayOutputStream outStream) {
        ByteArrayDataSource byteArrayStream = new ByteArrayDataSource(outStream.toByteArray(), "application/vnd.ms-excel");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
