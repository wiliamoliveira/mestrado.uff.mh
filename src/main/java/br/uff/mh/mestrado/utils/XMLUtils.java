package br.uff.mh.mestrado.utils;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import br.uff.mh.mestrado.config.Config;

public class XMLUtils {

	public static Config readConfigFile(String filepath) {
		return readConfigFile(new File(filepath));
	}

	public static Config saveConfigFile(File file, String text) {
		Config config = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			config = (Config) jaxbUnmarshaller.unmarshal(IOUtils.toInputStream(text));
			saveConfigFile(file, config);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return config;
	}

	public static Config saveConfigFile(File file, Config c) {
		Config config = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(c, file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return config;
	}

	public static Config readConfigFile(File file) {
		Config config = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			config = (Config) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return config;

	}
	
	
	
	public static String format(String unformattedXml) {
        try {
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(unformattedXml));
            final Document document = db.parse(is);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(4);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//	
//	public static String format(String text, String a) {
//		String xmlString = "";
//		try {
//			Transformer transformer = TransformerFactory.newInstance().newTransformer();
//			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			transformer.setOutputProperty(OutputKeys.METHOD, "text");
//			
//			Source source = new StreamSource(IOUtils.toInputStream(text));
//			StreamResult result = new StreamResult();
//			
//			transformer.transform(source, result);
//			xmlString = result.getWriter().toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return xmlString;
//	}
}
