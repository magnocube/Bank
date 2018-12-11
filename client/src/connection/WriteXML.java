package connection;

import java.io.File;
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

public class WriteXML {
	public static void main(String args[])
	{
		makeXml();
	}
	public static String makeXml()
	{
	  try {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("atmRequest");
		doc.appendChild(rootElement);

		// shorten way
		// staff.setAttribute("id", "1");

		// firstname elements
		Element type = doc.createElement("type");
		type.appendChild(doc.createTextNode("withdraw"));
		rootElement.appendChild(type);
		Element pasnumber = doc.createElement("pasnumber");
		pasnumber.appendChild(doc.createTextNode("123456"));
		rootElement.appendChild(pasnumber);

		// lastname elements
		Element lastname = doc.createElement("banknumber");
		lastname.appendChild(doc.createTextNode("6543"));
		rootElement.appendChild(lastname);

		// nickname elements
		Element nickname = doc.createElement("money");
		nickname.appendChild(doc.createTextNode("100.00"));
		rootElement.appendChild(nickname);

		// salary elements
		Element salary = doc.createElement("notes");
		salary.appendChild(doc.createTextNode("d"));
		rootElement.appendChild(salary);

		// write the content into xml file
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		System.out.println(output);
		return output;
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	return null;
	}
}