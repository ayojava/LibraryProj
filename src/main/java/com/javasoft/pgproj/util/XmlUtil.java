/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.util;

import com.javasoft.pgproj.entity.BookShelve;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author ayojava
 */
public class XmlUtil {

    private static XmlUtil xmlUtil;

    private XmlUtil() {
    }

    public static XmlUtil getInstance() {
        if (xmlUtil == null) {
            xmlUtil = new XmlUtil();
        }
        return xmlUtil;
    }
    private List<BookShelve> bookShelves;

    public void readAllStartUpConfig() {
        PropertyUtil instance = PropertyUtil.getInstance();
        bookShelves = new ArrayList<BookShelve>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            //File file = new File(instance.getXmlFilePath() + File.separator + "config.xml");

            File file = new File(instance.getXmlFilePath() + File.separator + "config" + File.separator + "shelveConfig.xml");

            Document doc = docBuilder.parse(file);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            XPathExpression expr = xpath.compile("//shelves");
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodesObjList = (NodeList) result;
            System.out.println("" + nodesObjList.getLength());
            for (int i = 0; i < nodesObjList.getLength(); i++) {
                NodeList childNodeList = nodesObjList.item(i).getChildNodes();
                BookShelve shelveObj = new BookShelve();
//                System.out.println("============= " + childNodeList.item(1).getTextContent());
//                System.out.println("============= " + childNodeList.item(3).getTextContent());
//                System.out.println("============= " + childNodeList.item(5).getTextContent());
                shelveObj.setShelveName(childNodeList.item(1).getTextContent());
                shelveObj.setShelveCode(childNodeList.item(3).getTextContent());
                shelveObj.setDescription(childNodeList.item(5).getTextContent());
                shelveObj.setRegisteredDate(new Date());
                bookShelves.add(shelveObj);
            }


        } catch (Exception ex) {
            System.out.println("Exception ::: " + ex);
        }
    }

    public static void main(String args[]) {
        XmlUtil.getInstance().readAllStartUpConfig();
    }

    public List<BookShelve> getBookShelves() {
        return bookShelves;
    }
}
