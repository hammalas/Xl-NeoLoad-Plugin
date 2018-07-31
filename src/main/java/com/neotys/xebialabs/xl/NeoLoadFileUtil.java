package com.neotys.xebialabs.xl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hrexed on 04/04/18.
 */
public class NeoLoadFileUtil {

    private static final String HITS = "hits";
    private static final String ERRORS = "error";
    private static final String RESPONSE = "response";

    public static String getCustomStat(String xpath, byte[] bytes) {
        try {
            InputStream input = new ByteArrayInputStream(bytes);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);

            return getCustomData(doc, xpath);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String getStat(String type, byte[] bytes) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        InputStream input = new ByteArrayInputStream(bytes);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(input);

        switch (type) {
            case HITS:
                return getData(doc, "avg_hits/s");
            case ERRORS:
                return getData(doc, "total_errors");
            case RESPONSE:
                return getData(doc, "avg_reqresponsetime");
        }
        return null;
    }

    private static String getData(Document doc, String key) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String result = null;
        NodeList nodes = (NodeList) xPath.evaluate("/report/summary/statistics/statistic[@name='" + key + "']", doc.getDocumentElement(), XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); ++i) {
            Element e = (Element) nodes.item(i);
            result = e.getAttribute("value");
        }
        return result;
    }

    private static String getCustomData(Document doc, String Xpath) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression = xPath.compile(Xpath);
        return (String) xPathExpression.evaluate(doc, XPathConstants.STRING);
    }
}
