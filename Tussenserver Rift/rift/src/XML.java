import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import static java.lang.Thread.sleep;

public class XML {
    private int i;

    public static String writeXML(String _type,String _pasnummer, String _banknummer,String _pincode, String _money,String _notes){
        String output;
        try {
            System.out.println();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document XML_main = docBuilder.newDocument();
            Element rootElement = XML_main.createElement("ATM_Request");
            XML_main.appendChild(rootElement);

            // shorten way
            // staff.setAttribute("id", "1");



            // type of message, this will determine what te server will do with the request
            Element type = XML_main.createElement("actionType");
            type.appendChild(XML_main.createTextNode(_type));
            rootElement.appendChild(type);

            //passNumber
            Element pasnumber = XML_main.createElement("pasnumber");
            pasnumber.appendChild(XML_main.createTextNode(_pasnummer));
            rootElement.appendChild(pasnumber);

            // bankNumber
            Element lastname = XML_main.createElement("banknumber");
            lastname.appendChild(XML_main.createTextNode(_banknummer));
            rootElement.appendChild(lastname);

            //pincode
            Element lastname1 = XML_main.createElement("pincode");
            lastname1.appendChild(XML_main.createTextNode(_pincode));
            rootElement.appendChild(lastname1);
            // amount of money
            Element nickname = XML_main.createElement("amount");
            nickname.appendChild(XML_main.createTextNode(_money));
            rootElement.appendChild(nickname);

            // eventual notes
            Element salary = XML_main.createElement("errorNote");
            salary.appendChild(XML_main.createTextNode(_notes));
            rootElement.appendChild(salary);

            // write the content into xml file

            output = convertDocumentToString(XML_main);

            return output;
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        return "error";
    }




    public static String getXMLType(String input){
        String type = "null";
       Document x = convertStringToDocument(input);
       type = x.getElementsByTagName("actionType").item(0).getTextContent();
        return type;
    }
    public static String getMessage(String input){
        String saldo = "null";
        Document x = convertStringToDocument(input);
        saldo = x.getElementsByTagName("errorNote").item(0).getTextContent();
        return saldo;
    }








    public static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    public static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
    }
    public String xmlToString(Document doc)
    {
    	try {
    	TransformerFactory tf = TransformerFactory.newInstance();
    	Transformer transformer = tf.newTransformer();
    	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    	StringWriter writer = new StringWriter();
    	
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
    	return writer.getBuffer().toString().replaceAll("\n|\r", "");
    	} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

}

