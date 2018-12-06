
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
import java.util.List;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.XMLConstants;

public class XMLCatalogParser extends SAXParser
{
  public enum SchemaFullCheckingIndicator { SchemaFullChecking, NoSchemaFullChecking }

  private static final String CATALOG_FILE = CatalogFeatures.Feature.FILES.getPropertyName();
  private static final String CATALOG_PREFER = CatalogFeatures.Feature.PREFER.getPropertyName();
  private static final String FEATURE_DISALLOW_DOCTYPE = "http://apache.org/xml/features/disallow-doctype-decl";
  private static final String FEATURE_EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
  private static final String FEATURE_EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
  private static final String FEATURE_FATAL_ERROR_CONTINUE = "http://apache.org/xml/features/continue-after-fatal-error";
  private static final String FEATURE_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
  private static final String FEATURE_LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
  private static final String FEATURE_NAMESPACES = "http://xml.org/sax/features/namespaces";
  private static final String FEATURE_STANDARD_URI_CONFORMANT = "http://apache.org/xml/features/standard-uri-conformant";
  private static final String FEATURE_USE_ENTITY_RESOLVER2 = "http://xml.org/sax/features/use-entity-resolver2";
  private static final String FEATURE_VALIDATION = "http://xml.org/sax/features/validation";
  private static final String FEATURE_VALIDATION_NORMALIZED = "http://apache.org/xml/features/validation/schema/normalized-value";
  private static final String FEATURE_VALIDATION_SCHEMA = "http://apache.org/xml/features/validation/schema";
  private static final String FEATURE_VALIDATION_SCHEMA_ELEMENT = "http://apache.org/xml/features/validation/schema/element-default";
  private static final String FEATURE_VALIDATION_SCHEMA_FULL = "http://apache.org/xml/features/validation/schema-full-checking";
  private static final String FEATURE_WARN_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
  private static final String FEATURE_WARN_DUPLICATE_ENTITYDEF = "http://apache.org/xml/features/warn-on-duplicate-entitydef";
  private static final String PROPERTY_EXTERNAL_SCHEMA = "http://apache.org/xml/properties/schema/external-schemaLocation";

  private SAXParser parser = null;
  private XMLReader reader = null;

  public XMLCatalogParser(SchemaFullCheckingIndicator schemaFullCheckingIndicator, List<String> catalogURIs, String schemaLocations) {
    try {
      SAXParserFactory spf = SAXParserFactory.newInstance();
      spf.setNamespaceAware(true);
      spf.setXIncludeAware(true);

      spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      spf.setFeature(XMLConstants.USE_CATALOG, true);

      spf.setFeature(FEATURE_DISALLOW_DOCTYPE, true);
      spf.setFeature(FEATURE_FATAL_ERROR_CONTINUE, true);
      spf.setFeature(FEATURE_NAMESPACES, true);
      spf.setFeature(FEATURE_STANDARD_URI_CONFORMANT, true);
      spf.setFeature(FEATURE_VALIDATION, true);
      spf.setFeature(FEATURE_VALIDATION_NORMALIZED, true);
      spf.setFeature(FEATURE_VALIDATION_SCHEMA, true);
      spf.setFeature(FEATURE_VALIDATION_SCHEMA_ELEMENT, true);
      spf.setFeature(FEATURE_WARN_DUPLICATE_ATTDEF, true);
      spf.setFeature(FEATURE_WARN_DUPLICATE_ENTITYDEF, true);

      spf.setFeature(FEATURE_EXTERNAL_GENERAL_ENTITIES, false);
      spf.setFeature(FEATURE_EXTERNAL_PARAMETER_ENTITIES, false);
      spf.setFeature(FEATURE_JAVA_ENCODINGS, false);
      spf.setFeature(FEATURE_LOAD_EXTERNAL_DTD, false);
      spf.setFeature(FEATURE_USE_ENTITY_RESOLVER2, false);

      spf.setFeature(FEATURE_VALIDATION_SCHEMA_FULL,
              (schemaFullCheckingIndicator == SchemaFullCheckingIndicator.SchemaFullChecking));

      parser = spf.newSAXParser();
      reader = parser.getXMLReader();

      setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "file");
      setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file");

      if (schemaLocations != null) {
        Logger.getInstance().debug("Setting schemaLocations property: " + schemaLocations);
        setProperty(PROPERTY_EXTERNAL_SCHEMA, schemaLocations);
      }

      if (catalogURIs.size() > 0) {
        String catalogURIList = String.join(";", catalogURIs);
        Logger.getInstance().debug("Setting CATALOG_FILE property: " + catalogURIList);
        setProperty(CATALOG_FILE, catalogURIList);
        setProperty(CATALOG_PREFER, "system");
      }
    }
    catch (SAXException exception) {
      Logger.getInstance().error("SAXException:", exception);
    }
    catch (ParserConfigurationException exception) {
      Logger.getInstance().error("invalid configuration:", exception);
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
