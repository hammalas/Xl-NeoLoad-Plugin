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
public  class  NeoLoadFileUtil {

    static final String HITS="hits";
    static final String ERRORS="error";
    static final String RESPONSE="response";
    static final String CUSTOM="custom";

    public static String GetCustomStat(String Xpath, byte[] bytes)
    {
        String result = null;

        try{

            InputStream input= new ByteArrayInputStream(bytes);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc=builder.parse(input);

            result=GetCustomData(doc,Xpath);

        }
        catch(Exception e)
        {
            return e.getMessage();
        }
        finally {
            return result;
        }
    }
    public static String GetStat(String Type,byte[] bytes) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        String result = null;

        InputStream input= new ByteArrayInputStream(bytes);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc=builder.parse(input);

        switch(Type)
        {
            case HITS:
                result=GetData(doc,"avg_hits/s");
                break;
            case ERRORS:
                result=GetData(doc,"total_errors");
                break;
            case RESPONSE:
                result=GetData(doc,"avg_reqresponsetime");
                break;


        }
        return result;


    }



    public static String GetData(Document doc,String key) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String result = null;
        NodeList nodes = (NodeList)xPath.evaluate("/report/summary/statistics/statistic[@name='"+key+"']", doc.getDocumentElement(), XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); ++i) {
            Element e = (Element) nodes.item(i);
            result= e.getAttribute("value");

        }
        return result;
    }
    public static String GetCustomData(Document doc,String Xpath) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String result = null;
        XPathExpression xPathExpression = xPath.compile(Xpath);
        result=  (String)xPathExpression.evaluate(doc, XPathConstants.STRING);

        return result;
    }
}
