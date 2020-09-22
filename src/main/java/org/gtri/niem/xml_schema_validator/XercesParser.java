
package org.gtri.niem.xml_schema_validator;

import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.util.XMLCatalogResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXException;
import org.apache.xerces.util.SymbolTable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XercesParser extends SAXParser
{
  public enum SchemaFullCheckingIndicator { SchemaFullChecking, NoSchemaFullChecking }
  /* public GrammarPool getGrammarPool() {
    return (GrammarPool)fConfiguration.getProperty(XMLGRAMMAR_POOL);
  } */
	
  public XercesParser(SchemaFullCheckingIndicator schemaFullCheckingIndicator,
                      List<String> catalogURIs,
                      String schemaLocations) {
    super(new SymbolTable(), new GrammarPool());

    try {
      setFeature("http://apache.org/xml/features/allow-java-encodings", false);
      setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);
      setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      setFeature("http://apache.org/xml/features/standard-uri-conformant", true);
      setFeature("http://apache.org/xml/features/validation/schema", true);
      setFeature("http://apache.org/xml/features/validation/schema/element-default", true);
      setFeature("http://apache.org/xml/features/validation/schema/normalized-value", true);
      setFeature("http://apache.org/xml/features/validation/warn-on-duplicate-attdef", true);
      setFeature("http://apache.org/xml/features/warn-on-duplicate-entitydef", true);
      setFeature("http://xml.org/sax/features/external-general-entities", false);
      setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      setFeature("http://xml.org/sax/features/namespaces", true);
      setFeature("http://xml.org/sax/features/validation", true);

      setFeature("http://apache.org/xml/features/validation/schema-full-checking",
                 (schemaFullCheckingIndicator == SchemaFullCheckingIndicator.SchemaFullChecking));

      if (schemaLocations != null) {
        Logger.getInstance().debug("Setting schemaLocations property: " + schemaLocations);
        setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", schemaLocations);
      }

      setContentHandler(XMLHandler.getInstance());
      setDTDHandler(XMLHandler.getInstance());
      setErrorHandler(XMLHandler.getInstance());

      if (catalogURIs.size() > 0) {
        XMLCatalogResolver resolver = new XMLCatalogResolver();
        resolver.setCatalogList(catalogURIs.toArray(new String[catalogURIs.size()]));
        setProperty("http://apache.org/xml/properties/internal/entity-resolver", resolver);
      }
      
    }
    catch (SAXNotRecognizedException exception) {
      Logger.getInstance().error("unrecognized feature", exception);
    }
    catch (SAXNotSupportedException exception) {
      Logger.getInstance().error("unsupported feature", exception);
    }
  }

  public void parse(File file) throws SAXException, IOException {
    parse(new InputSource(file.toURI().toString()));
  }
    
}
