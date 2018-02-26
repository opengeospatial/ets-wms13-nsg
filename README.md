# Test Suite for the NSG WMS 1.3 Profile

This test suite verifies that a Web Map Service (WMS) component meets the requirements of the 
NSG WMS application profile, [NGA.STND.0058_1.0_WMS](https://nsgreg.nga.mil/doc/view?i=4209&amp;month=2&amp;day=8&amp;year=2018]).

## How to run the tests

### Integrated development environment (IDE)

You can use a Java IDE such as Eclipse, NetBeans, or IntelliJ to run the test suite. 
Clone the repository and build the project. The runtime configuration is summarized below.

__Main class__: `org.opengis.cite.wms13.nsg.TestNGController`

__Arguments__: The first argument must refer to an XML properties file containing the 
required test run argument (a reference to a WMS 1.3 capabilities document). If not specified, 
the default location of the file at `${user.home}/test-run-props.xml` will be used.

You can modify the default settings in the sample [test-run-props.xml](src/main/config/test-run-props.xml) 
file, which is shown below. The value of the **wms** argument must be an absolute URI that 
conforms to the 'http' or 'file' schemes. The **tes** argument specifies the location of 
a service that provides a RESTful API for running the base WMS 1.3 test suite; by default 
the OGC teamengine installation--the 'beta' facility--will be used, but an alternative 
installation may be used instead (for example: http://localhost:8080/teamengine).

**Test run arguments** 

|Argument  |Description  |Default value  |
|:---------|:------------|:---------------|
|wms  |Location of WMS capabilities document  |
|vector  |Are any layers derived from vector data?  |false
|tes |Endpoint for test execution service  |http://cite.opengeospatial.org/te2/


```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties version="1.0">
  <comment>Test run arguments (ets-wms13-nsg)</comment>
  <entry key="wms"></entry>
  <entry key="vector">false</entry>
</properties>
```

The TestNG results file (testng-results.xml) will be written to a subdirectory 
in ${user.home}/testng/ having a UUID value as its name.


### Command shell (terminal)

One of the build artifacts is an "all-in-one" JAR file that includes the test suite 
bundled together with all of its dependencies. This makes it very easy to execute the 
test suite in a command shell like so:

`java -jar ets-wms13-nsg-${version}-aio.jar  [test-run-props.xml]`


### OGC test harness

Use [TEAM Engine](https://github.com/opengeospatial/teamengine), the official 
OGC test harness. The latest test suite release should be available at the 
[beta testing facility](http://cite.opengeospatial.org/te2/). You can also 
[build and deploy](https://github.com/opengeospatial/teamengine) the test 
harness yourself and use a local installation.


## How to contribute

If you would like to get involved, you can:

* [Report an issue](https://github.com/opengeospatial/ets-wms13-nsg/issues) such as a defect or an 
enhancement request
* Help to resolve an [open issue](https://github.com/opengeospatial/ets-wms13-nsg/issues?q=is%3Aopen)
* Fix a bug: Fork the repository, apply the fix, and create a pull request
* Add new tests: Fork the repository, implement (and verify) the tests on a new topic branch, 
and create a pull request
