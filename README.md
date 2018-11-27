
# XML Schema validator

# Building & Installing

```
$ ./configure --prefix=$HOME
$ make
$ make -f stow.mk
```

# Running - Test Cases

```
$ cd src/test/resources/ssgt/test01-base-test
$ xs-validate -c base-xsd/niem/xml-catalog.xml iep-sample/Person.xml

$ cd src/test/resources/ssgt/test02-no-catalog
$ xs-validate iep-sample/Person.xml

$ cd src/test/resources/ssgt/test03-extension
$ xs-validate -c base-xsd/xml-catalog.xml iep-sample/exchange-sample.xml

$ cd src/test/resources/ssgt/test04-message-spec
$ xs-validate -c base-xsd/xml-catalog.xml --schema-location=http://www.example.org/VehicleReport-extension=../base-xsd/extension/VehicleReport-extension.xsd message-sample/VehicleReport.xml

$ cd src/test/resources/ssgt/test05-schema-location
$ xs-validate --schema-location=http://release.niem.gov/niem/niem-core/4.0/=../base-xsd/niem/niem-core/4.0/niem-core.xsd --catalog=base-xsd/niem/xml-catalog.xml iep-sample/Person.xml

```


