
package org.gtri.niem.xml_schema_validator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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

public class XMLCatalogParser
{
  private static final String CATALOG_FILE = CatalogFeatures.Feature.FILES.getPropertyName();

  public XMLCatalogParser(List<String> catalogURIs, String schemaLocations) {
    try {
      SAXParserFactory spf = SAXParserFactory.newInstance();
      spf.setNamespaceAware(true);
      spf.setXIncludeAware(true);
      SAXParser parser = spf.newSAXParser();

      if (catalogURIs.size() > 0) {
          List<URI> catalogUriList = new ArrayList<>();

          for (String uriString : catalogURIs) {
              catalogUriList.add(URI.create(uriString));
          }

          CatalogResolver resolver = CatalogManager.catalogResolver(CatalogFeatures.defaults(),
                  catalogUriList.toArray(new URI[catalogUriList.size()]));
          parser.setProperty(CATALOG_FILE, resolver);
      }
    }
    catch (SAXException exception) {
      Logger.getInstance().error("SAXException: ", exception);
    }
    catch (ParserConfigurationException exception) {
      Logger.getInstance().error("invalid configuration: ", exception);
    }
  }

  /* public void parse(File file) throws SAXException, IOException {
      parse(new InputSource(file.toURI().toString()));
  } */
}
