
package org.gtri.niem.xml_schema_validator;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogManager;
import javax.xml.catalog.CatalogResolver;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.XMLConstants;

public class XMLCatalogParser extends SAXParser
{
  private static final String CATALOG_FILE = CatalogFeatures.Feature.FILES.getPropertyName();
  private static final String CATALOG_PREFER = CatalogFeatures.Feature.PREFER.getPropertyName();

  private SAXParser parser = null;
  private XMLReader reader = null;

  public XMLCatalogParser(List<String> catalogURIs, String schemaLocations) {
    try {
      SAXParserFactory spf = SAXParserFactory.newInstance();
      spf.setNamespaceAware(true);
      spf.setXIncludeAware(true);
      // TODO: set ACCESS constants to "file" string, make setFeature and setProperty work
      spf.setFeature(XMLConstants.ACCESS_EXTERNAL_DTD, false);
      spf.setFeature(XMLConstants.ACCESS_EXTERNAL_SCHEMA, false);
      spf.setFeature(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, false);
      spf.setFeature(XMLConstants.USE_CATALOG, true);
      parser = spf.newSAXParser();
      reader = parser.getXMLReader();

      if (catalogURIs.size() > 0) {
          List<URI> catalogUriList = new ArrayList<>();

          for (String uriString : catalogURIs) {
              catalogUriList.add(URI.create(uriString));
          }

          CatalogResolver resolver = CatalogManager.catalogResolver(CatalogFeatures.defaults(),
                  catalogUriList.toArray(new URI[catalogUriList.size()]));
          parser.setProperty(CATALOG_FILE, resolver);
          parser.setProperty(CATALOG_PREFER, "system");
      }
    }
    catch (SAXException exception) {
      Logger.getInstance().error("SAXException", exception);
    }
    catch (ParserConfigurationException exception) {
      Logger.getInstance().error("invalid configuration", exception);
    }
  }

  public Parser getParser() {
    return (Parser) parser;
  }

  public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
    return parser.getProperty(name);
  }

  public boolean isNamespaceAware() {
    return true;
  }

  public boolean isValidating() {
    return true;
  }

  public XMLReader getXMLReader() throws SAXException {
    return reader;
  }

  public void parse(File file) throws SAXException, IOException {
    // TODO: use XMLHandler class here?
    DefaultHandler dh = new DefaultHandler();
    InputSource is = new InputSource(file.toURI().toString());
    parser.parse(is, dh);
  }

  public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
    parser.setProperty(name, value);
  }
}
