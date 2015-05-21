/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.core;

import com.javasoft.pgproj.gui.internalFrames.book.AddBookFrame;
import com.javasoft.pgproj.gui.internalFrames.book.EditBookFrame;
import com.javasoft.pgproj.gui.internalFrames.book.tables.ListAllBooksFrame;
import com.javasoft.pgproj.gui.internalFrames.book.ViewBookFrame;
import com.javasoft.pgproj.gui.internalFrames.book.tables.ListAvailableBooksFrame;
import com.javasoft.pgproj.gui.internalFrames.book.tables.ListBorrowedBooksFrame;
import com.javasoft.pgproj.gui.internalFrames.help.AboutFrame;
import com.javasoft.pgproj.gui.internalFrames.loan.LoanBookFrame;
import com.javasoft.pgproj.gui.internalFrames.loan.ReturnBookFrame;
import com.javasoft.pgproj.gui.internalFrames.member.AddMemberFrame;
import com.javasoft.pgproj.gui.internalFrames.member.EditMemberFrame;
import com.javasoft.pgproj.gui.internalFrames.member.ViewMemberFrame;
import com.javasoft.pgproj.gui.internalFrames.member.tables.ListAllMembersFrame;
import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import javax.swing.JDesktopPane;

/**
 *
 * @author ayojava
 */
public class DesktopPaneHandler {

    private AddBookFrame addBookFrame;
    private EditBookFrame editBookFrame;
    private ListAllBooksFrame listBookFrame;
    private AddMemberFrame addMemberFrame;
    private EditMemberFrame editMemberFrame;
    private AboutFrame aboutFrame;
    private ViewBookFrame viewBookFrame;
    private ViewMemberFrame viewMemberFrame;
    private ListAllMembersFrame listAllMembersFrame;
    private LoanBookFrame loanBookFrame;
    private ReturnBookFrame returnBookFrame;
    private ListAvailableBooksFrame listAvailableBooksFrame;
    private ListBorrowedBooksFrame listBorrowedBooksFrame;

    public void handleEvents(ActionEvent ae, final JDesktopPane desktop) {

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_book_listBorrowedBooks"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    listBorrowedBooksFrame = new ListBorrowedBooksFrame();
                    desktop.add(listBorrowedBooksFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = listBorrowedBooksFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        listBorrowedBooksFrame.setLocation(xLoc, yLoc);
                        listBorrowedBooksFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_book_listAvailableBooks"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    listAvailableBooksFrame = new ListAvailableBooksFrame();
                    desktop.add(listAvailableBooksFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = listAvailableBooksFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        listAvailableBooksFrame.setLocation(xLoc, yLoc);
                        listAvailableBooksFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_book_returnBook"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    returnBookFrame = new ReturnBookFrame();
                    desktop.add(returnBookFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = returnBookFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        returnBookFrame.setLocation(xLoc, yLoc);
                        returnBookFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_book_loanBook"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    loanBookFrame = new LoanBookFrame();
                    desktop.add(loanBookFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = loanBookFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        loanBookFrame.setLocation(xLoc, yLoc);
                        loanBookFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }


        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_member_listMembers"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    listAllMembersFrame = new ListAllMembersFrame();
                    desktop.add(listAllMembersFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = listAllMembersFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        listAllMembersFrame.setLocation(xLoc, yLoc);
                        listAllMembersFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_member_viewMember"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    viewMemberFrame = new ViewMemberFrame();
                    desktop.add(viewMemberFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = viewMemberFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        viewMemberFrame.setLocation(xLoc, yLoc);
                        viewMemberFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_book_addBook"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    addBookFrame = new AddBookFrame();
                    desktop.add(addBookFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = addBookFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        addBookFrame.setLocation(xLoc, yLoc);
                        addBookFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_book_editBook"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    editBookFrame = new EditBookFrame();
                    desktop.add(editBookFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = editBookFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        editBookFrame.setLocation(xLoc, yLoc);
                        editBookFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_book_listAllBooks"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    listBookFrame = new ListAllBooksFrame();
                    desktop.add(listBookFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = listBookFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        listBookFrame.setLocation(xLoc, yLoc);
                        listBookFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_member_addMember"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    addMemberFrame = new AddMemberFrame();
                    desktop.add(addMemberFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = addMemberFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        addMemberFrame.setLocation(xLoc, yLoc);
                        addMemberFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_member_editMember"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    editMemberFrame = new EditMemberFrame();
                    desktop.add(editMemberFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = editMemberFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        editMemberFrame.setLocation(xLoc, yLoc);
                        editMemberFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_file_About"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    aboutFrame = new AboutFrame();
                    desktop.add(aboutFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = aboutFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        aboutFrame.setLocation(xLoc, yLoc);
                        aboutFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }

        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_book_viewBook"))) {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    viewBookFrame = new ViewBookFrame();
                    desktop.add(viewBookFrame);
                    Dimension desktopDim = desktop.getSize();
                    Dimension frameDim = viewBookFrame.getSize();
                    try {
                        int yLoc = (int) ((desktopDim.getHeight() - frameDim.getHeight()) / 2);
                        int xLoc = (int) ((desktopDim.getWidth() - frameDim.getWidth()) / 2);
                        viewBookFrame.setLocation(xLoc, yLoc);
                        viewBookFrame.setSelected(true);
                    } catch (PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        //
        
        if (ae.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("menuItem_file_Exit"))) {
            System.exit(0);
        }
    }
}
