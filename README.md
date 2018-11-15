
# XML Schema validator

# Building & Installing

```
$ ./configure --prefix=$HOME
$ make
$ make -f stow.mk
```

# Running

```
$ cd src/test/resources/ssgt/test01-base-test
$ xs-validate -c base-xsd/niem/xml-catalog.xml iep-sample/Person.xml
```


