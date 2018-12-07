
package org.gtri.niem.xml_schema_validator;

import org.apache.commons.cli.CommandLine;
// import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
// import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
// import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
// import org.apache.commons.cli.UnrecognizedOptionException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
    
public class CLI
{
  private List<String> catalogURIs = new ArrayList<>();
  private List <File> subjectXMLFiles = new ArrayList<>();
  private String schemaLocations = null;
  static private final CLI cli = new CLI();
  static public CLI getInstance () {
    return cli;
  }

  private CLI() {
    //
  }

  public static void main(String[] args) {
    try {
      CLI cli = CLI.getInstance();
      cli.processArgs(args);

      XMLCatalogParser parser = new XMLCatalogParser(XMLCatalogParser.SchemaFullCheckingIndicator.SchemaFullChecking,
              cli.catalogURIs,
              cli.schemaLocations);

      for (File subjectXMLFile : cli.subjectXMLFiles) {
        Logger.getInstance().info("parsing subject XML file \"{}\" using XML Catalog API", subjectXMLFile);
        parser.parse(subjectXMLFile, XMLHandler.getInstance());
      }
    }
    catch (java.lang.Throwable throwable) {
      Logger.getInstance().error("unknown error", throwable);
    }
    System.exit(Logger.getInstance().getExitStatus());
  }

  private void processArgs(String[] args) {
    Options options = new Options();

    options.addOption(new Option("h", "help", false, "Print this help"));
    options.addOption(new Option(null, "catalog", true, "An XML Catalog document to direct schema assembly"));
    options.addOption(new Option(null, "schema-location", true, "A schema location string (like @xsi:schemaLocation)"));

    CommandLine cl = null;
    try {
      cl = new GnuParser().parse(options, args);
    }
    catch (ParseException exception) {
      Logger.getInstance().error("command line parse exception", exception);
    }

    assert cl != null;

    if (cl.hasOption("help")) {
      HelpFormatter help = new HelpFormatter();
      help.printHelp(Integer.MAX_VALUE, "this is command line syntax", "this is a header", options, "this is a footer", true);
      System.exit(0);
    }

    for (Option option : cl.getOptions() ) {
      switch (option.getLongOpt()) {
      case "catalog" :
        catalogURIs.add(asReadableFile(new File(option.getValue()), "XML Catalog file").toURI().toString());
        break;
      case "schema-location" :
        schemaLocations = option.getValue();
        break;
      }
    }
    for (String name : cl.getArgs())
      subjectXMLFiles.add(asReadableFile(new File(name), "subject XML file"));
  }

  public File asReadableFile(File file, String what) {
    if (! file.exists()) {
      Logger.getInstance().error("{} \"{}\" must exist", what, file);
    }
    if (! file.isFile()) {
      Logger.getInstance().error("{} \"{}\" must be a file", what, file);
    }
    if (! file.canRead()) {
      Logger.getInstance().error("{} \"{}\" must be readable", what, file);
    }
    return file;
  }

}
