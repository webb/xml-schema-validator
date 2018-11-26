
package org.gtri.niem.xml_schema_validator;

import java.io.File;
import java.net.URI;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import java.io.IOException;
import org.xml.sax.InputSource;

public class XMLHandler extends DefaultHandler2
{
  private XMLHandler() {

  }

  private static final XMLHandler instance = new XMLHandler();

  public static XMLHandler getInstance() {
    return instance;
  }

  //////////////////////////////////////////////////////////////////
  // content handling
    
  private boolean isNewDocument = true;

  public void startDocument()
    throws SAXException {
    isNewDocument = true;
  }

  public void startElement(String uri,
                           String localName,
                           String qName,
                           org.xml.sax.Attributes atts)
    throws SAXException {
    if (isNewDocument) {
      Logger.getInstance().info("document element: {} = {{}}{}", qName, uri, localName);
      isNewDocument = false;
    }
  }

  //////////////////////////////////////////////////////////////////
  // error handling

  private void printError(String type, SAXParseException exception) {
    String fileString = null;
    try {
      URI uri = new URI(exception.getSystemId());
      if (uri.getScheme().equals("file")) {
        fileString = (new File(uri)).getPath();
      } else {
        fileString = uri.toString();
      }
    } catch (java.net.URISyntaxException e) {
      Logger.getInstance().error("bad URI syntax in {}", exception.getSystemId(), e);
    }
    assert fileString != null;
    System.out.println(fileString
                       + ":" + exception.getLineNumber() + "." + exception.getColumnNumber()
                       + ": " + type
                       + ": " + exception.getMessage());
    if (exception.getPublicId() != null) {
      System.out.println("  public ID = \"" + exception.getPublicId() + "\"");
    }
    if (exception.getCause() != null) {
      System.out.println("  cause: " + exception.getCause());
    }
  }
    
  public void warning(SAXParseException exception)
    throws SAXException {
    Logger.getInstance().trace("XML parse warning", exception);
    printError("warning", exception);
  }

  public void error(SAXParseException exception)
    throws SAXException {
    Logger.getInstance().trace("XML parse error", exception);
    Logger.getInstance().setExitStatus(1);
    printError("error", exception);
  }

  public void fatalError(SAXParseException exception)
    throws SAXException {
    Logger.getInstance().trace("XML parse fatal error", exception);
    Logger.getInstance().setExitStatus(1);
    printError("fatal error", exception);
    System.exit(1);
  }

  //////////////////////////////////////////////////////////////////
  // entity resolver

  public InputSource getExternalSubset(String name,
                                       String baseURI)
    throws SAXException,
           IOException {
    Logger.getInstance().error("illegal call of {}.{}", XMLHandler.class, "getExternalSubset(...)");
    Logger.getInstance().trace("getExternalSubset({}, {})", name, baseURI);
    InputSource returnValue = super.getExternalSubset(name, baseURI);
    Logger.getInstance().trace("getExternalSubset yields {}", returnValue);
    return returnValue;
  }

  public InputSource resolveEntity(String name,
                                   String publicId,
                                   String baseURI,
                                   String systemId)
    throws SAXException,
           IOException {
    Logger.getInstance().error("illegal call of {}.{}", XMLHandler.class, "resolveEntity(...)");
    Logger.getInstance().trace("resolveEntity({}, {}, {}, {})", name, publicId, baseURI, systemId);
    InputSource returnValue = super.resolveEntity(name, publicId, baseURI, systemId);
    Logger.getInstance().trace("resolveEntity yields {}", returnValue);
    return returnValue;
  }
    
}
