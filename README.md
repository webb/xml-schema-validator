
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
$ xs-validate -c base-xsd/xml-catalog.xml message-sample/VehicleReport.xml

$ cd src/test/resources/ssgt/test05-message-spec
$ xs-validate --schema-location=px=http://www.example.org/PersonExample -c base-xsd/niem/xml-catalog.xml iep-sample/Person.xml

```


