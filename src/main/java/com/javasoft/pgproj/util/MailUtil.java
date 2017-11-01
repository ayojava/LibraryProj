/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.util;

import com.javasoft.pgproj.DTO.BookDTO;
import com.javasoft.pgproj.DTO.LoanBookDTO;
import com.javasoft.pgproj.entity.Book;
import com.javasoft.pgproj.entity.BorrowedBook;
import com.javasoft.pgproj.entity.Member;
import com.javasoft.pgproj.interfaces.Constants;
import com.javasoft.pgproj.lms.LibraryService;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 *
 * @author ayojava
 */
public class MailUtil {

    private String mailSender;
    private String password;
    private String mailServer;
    private PropertyUtil propertyUtil;

    public MailUtil() {
        propertyUtil = PropertyUtil.getInstance();
        try {
            propertyUtil.loadConfig();
        } catch (Exception ex) {
        }
        this.mailSender = propertyUtil.getMailSender();
        this.password = propertyUtil.getMailPassword();
        this.mailServer = propertyUtil.getMailServer();
    }

    public static void main(String[] args) {
        new MailUtil().sendMail("memberRegistration.subject", "memberRegistration.message", "memberRegistration.detail", null,
                new String[]{"Mr Ayodeji Ilori"}, new String[]{"Mr Ayodeji Ilori"}, false, null, null, "ayodeji.ilori@empirebsl.com");
    }

    public void sendMemberRegistrationDetails(Member memberObj, boolean isEdit) {
        String[] msgParams = {memberObj.returnFullName()};
        String[] msgDetailParams = {memberObj.returnFullName(), memberObj.getRegNo(), memberObj.getEmailAddress(), memberObj.getOccupation(),
            memberObj.getPhoneNumber(), memberObj.getHouseAddress().replaceAll(Constants.SUBSTRING_XTER, "  ")};
        sendMail("memberRegistration.subject", isEdit ? "memberRegistration.editMessage" : "memberRegistration.message", "memberRegistration.detail",
                null, msgParams, msgDetailParams, true, null, null, memberObj.getEmailAddress());
    }

    public void sendBookDetails(BookDTO bookDTO, boolean isEdit) {
        Book bookObj = bookDTO.getBookObj();
        String[] msgParams = {"ADMINISTRATOR"};
        String[] msgDetailParams = {bookObj.getBookName(), bookObj.getBookAuthor(), bookObj.getBookISBN(), bookObj.getPublisherName(),
            String.valueOf(bookObj.getPages()), DateFormatUtils.format(bookObj.getRegisteredDate(), Constants.DATE_FORMAT_PATTERN),
            String.valueOf(bookDTO.getCopies()), bookObj.getBookShelve().getShelveName()};
        sendMail("bookRegistration.subject", isEdit ? "bookRegistration.editMessage" : "bookRegistration.message", "bookRegistration.detail",
                null, msgParams, msgDetailParams, true, null, null, LibraryService.getInstance().getSystemUser().getEmailAddress());
    }

    public void sendLoanedBookDetails(LoanBookDTO loanBookDTO) {
        Book bookObj = loanBookDTO.getBorrowedBook().getBook();
        String[] msgParams = {loanBookDTO.getBorrowedBook().getUser().returnFullName()};
        String[] msgDetailParams = {bookObj.getBookName(), bookObj.getBookAuthor(), bookObj.getBookISBN(), bookObj.getPublisherName(),
            String.valueOf(bookObj.getPages()), DateFormatUtils.format(loanBookDTO.getBorrowedBook().getBorrowedDate(), Constants.DATE_FORMAT_PATTERN),
            DateFormatUtils.format(loanBookDTO.getBorrowedBook().getDueDate(), Constants.DATE_FORMAT_PATTERN)
        };
        String[] recipients = {LibraryService.getInstance().getSystemUser().getEmailAddress(), loanBookDTO.getBorrowedBook().getUser().getEmailAddress()};
        sendMail("loanBook.subject", "loanBook.message", "loanBook.detail", null, msgParams, msgDetailParams, true, null, null, recipients);
    }

    public void sendOutstandingBooksDetails(List<BorrowedBook> borrowedBooks) {
        Properties msgMap = propertyUtil.getMailMessagesProperties();
        String[] msgParams = {"ADMINISTRATOR"};
        StringBuilder borrowedBksList = new StringBuilder();
        MessageFormat msgFormat = null;
        int j = 1;
        for (int i = 0; i < borrowedBooks.size(); i++) {
            BorrowedBook borrowedBook = borrowedBooks.get(i);
            if (borrowedBook == null) {
                continue;
            }
            String[] loanBookInnerDetails = {String.valueOf(j),borrowedBook.getBook().getBookName(),borrowedBook.getUser().returnFullName(),
                    borrowedBook.getUser().getEmailAddress(),DateFormatUtils.format(borrowedBook.getBorrowedDate(), Constants.DATE_FORMAT_PATTERN),
                    DateFormatUtils.format(borrowedBook.getDueDate(), Constants.DATE_FORMAT_PATTERN)
            };
            msgFormat= new MessageFormat(msgMap.getProperty(j%2 >0 ?"unReturnedBook.innerDetail.odd":"unReturnedBook.innerDetail.even"));
            borrowedBksList.append(msgFormat.format(loanBookInnerDetails));
            j++;      
        }
        msgFormat = new MessageFormat(msgMap.getProperty("unReturnedBook.detail"));
        String msgDetailValue = msgFormat.format(new String[]{borrowedBksList.toString()});
        sendMail("unReturnedBook.subject","unReturnedBook.message",null,null,msgParams,null,true,msgDetailValue,null, 
                LibraryService.getInstance().getSystemUser().getEmailAddress());
    }

    public void sendMail(String sbjKey, String msgKey, String msgDetailKey, final String fileName,
            String[] msgParams, String[] msgDetailParams, final boolean isHtml, String msgDetailValue, final DataSource attachment, 
            final String... recipients) {
        Properties msgMap = propertyUtil.getMailMessagesProperties();
        if (recipients == null || msgMap == null) {
            return;
        }

        MessageFormat subjectFormat = new MessageFormat(msgMap.getProperty(sbjKey));
        final String mailSubject = subjectFormat.format(new String[]{"LIBRARY MANAGEMENT SERVICE"});

        MessageFormat msgFormat = new MessageFormat(msgMap.getProperty(msgKey));
        String mailMessage = msgFormat.format(msgParams);

        String messageDetail = "";
        if (StringUtils.isNotBlank(msgDetailKey)) {
            msgFormat = new MessageFormat(msgMap.getProperty(msgDetailKey));
            messageDetail = msgFormat.format(msgDetailParams);
        } else {
            messageDetail = msgDetailValue;
        }

        final String message = htmlTemplateMessage(msgMap, mailMessage + messageDetail);
        //System.out.println("Message :::: " + message);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < recipients.length; i++) {
                        if (isHtml) {
                            sendMessage(true, message, mailSubject, recipients[i]);
                        }
                        if (attachment != null) {
                            sendMessageWithDatasourceAttachment(message, mailSubject, recipients[i], attachment);
                        }
                        if (fileName != null) {
                            sendMessageWithAttachment(message, mailSubject, recipients[i], fileName);
                        }
                    }
                } catch (MessagingException ex) {
                    System.out.println("Exception ::: " + ex);
                }
            }
        });
        thread.start();
    }

    private String htmlTemplateMessage(Properties msgMap, String msgBody) {
        String header = msgMap.getProperty("msg.header");
        String footer = msgMap.getProperty("msg.footer");
        return header + msgBody + footer;
    }

    private void sendMessage(boolean isHtml, String message, String subject, String recipient) throws MessagingException {

        Session mailSession = getMailSession(true);

        MimeMessage mimeMessage = new MimeMessage(mailSession);
        mimeMessage.setSentDate(new Date());
        mimeMessage.setSubject(subject);
        mimeMessage.setFrom(new InternetAddress(mailSender));

        mimeMessage.setContent(message, (isHtml) ? "text/html" : "text/plain");
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        Transport transport = mailSession.getTransport();
        transport.connect();
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }

    private void sendMessageWithAttachment(String message, String subject, String recipient, String fileName) throws MessagingException {
        Multipart multipart = new MimeMultipart();

        Session mailSession = getMailSession(true);
        MimeMessage mimeMessage = new MimeMessage(mailSession);
        mimeMessage.setSentDate(new Date());
        mimeMessage.setSubject(subject);
        mimeMessage.setFrom(new InternetAddress(mailSender));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(fileName);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);

        multipart.addBodyPart(messageBodyPart);

        mimeMessage.setContent(multipart);

        Transport transport = mailSession.getTransport();
        transport.connect();
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();

    }

    private void sendMessageWithDatasourceAttachment(String message, String subject, String recipient, DataSource attachment) throws MessagingException {
        Multipart multipart = new MimeMultipart();

        Session mailSession = getMailSession(true);
        MimeMessage mimeMessage = new MimeMessage(mailSession);
        mimeMessage.setSentDate(new Date());
        mimeMessage.setSubject(subject);
        mimeMessage.setFrom(new InternetAddress(mailSender));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(attachment));

        multipart.addBodyPart(messageBodyPart);

        mimeMessage.setContent(multipart);

        Transport transport = mailSession.getTransport();
        transport.connect();
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();

    }

    private Properties builMailProperties(String debug) {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.enabled", "false");
        props.setProperty("mail.smtp.host", mailServer);
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.debug", debug);
        return props;
    }

    private Session getMailSession(boolean authenticate) {
        Session session = null;
        if (authenticate) {

            session = Session.getInstance(builMailProperties("true"),
                    new Authenticator() {

                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(mailSender, password);
                        }
                    });
        } else {
            session = Session.getInstance(builMailProperties("true"), null);
        }
        return session;
    }
}
