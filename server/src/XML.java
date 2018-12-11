

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


public class XML {
    private int i;

    public static String writeXML(String _type,boolean succes ,String _pasnummer, String _banknummer,String _amount,String _message){
        String output;
        try {
            System.out.println();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document XML_main = docBuilder.newDocument();
            Element rootElement = XML_main.createElement("ATM_Request");
            XML_main.appendChild(rootElement);

            // type of message, this will determine what te server will do with the request
            Element type = XML_main.createElement("actionType");
            type.appendChild(XML_main.createTextNode(_type));
            rootElement.appendChild(type);

            //succes
            Element Succes = XML_main.createElement("succes");
            if(succes==true)
            {
                Succes.appendChild(XML_main.createTextNode("true"));
            }
            else {
                Succes.appendChild(XML_main.createTextNode("false"));
            }
            rootElement.appendChild(Succes);

            //passNumber
            Element pasnumber = XML_main.createElement("pasnumber");
            pasnumber.appendChild(XML_main.createTextNode(_pasnummer));
            rootElement.appendChild(pasnumber);

            // bankNumber
            Element lastname = XML_main.createElement("banknumber");
            lastname.appendChild(XML_main.createTextNode(_banknummer));
            rootElement.appendChild(lastname);

            Element amount = XML_main.createElement("amount");
            amount.appendChild(XML_main.createTextNode(_amount));
            rootElement.appendChild(amount);
            // eventual notes
            Element salary = XML_main.createElement("errorNote");
            salary.appendChild(XML_main.createTextNode(_message));
            rootElement.appendChild(salary);

            // write the content into xml file

            output = convertDocumentToString(XML_main);

            return output;
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        return "error";
    }
    public Document convertStringToDocument(String xmlStr) {
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


}
