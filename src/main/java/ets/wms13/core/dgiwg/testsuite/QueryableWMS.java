package ets.wms13.core.dgiwg.testsuite;

import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesRequestParameterTest;
import ets.wms13.core.dgiwg.testsuite.getfeatureinfo.GetFeatureInfoFeatureCountTest;
import ets.wms13.core.dgiwg.testsuite.getmap.GetMapRequestParametersTest;


public class QueryableWMS 
{
	
	 @Test(description = "DGIWG - Web Map Service 1.3 Profile, 6.4, Requirement 1")
	public void testQueryableWMS( ITestContext testContext ) 
			throws XPathFactoryConfigurationException, XPathExpressionException, SOAPException
	{
		 GetFeatureInfoFeatureCountTest sampleGFI = new GetFeatureInfoFeatureCountTest();
		 sampleGFI.initBaseFixture(testContext);
		 sampleGFI.initParser();
		 sampleGFI.buildGetMapRequest();
		 sampleGFI.wmsGetFeatureInfoFeatureCountWithValueOfOne();		
	}	
	
	 
	 @Test(description = "DGIWG - Web Map Service 1.3 Profile, 6.5, Requirement 2")
	public void testQueryableWMSBindings( ITestContext testContext ) 
			throws SOAPException
	{
		 GetCapabilitiesRequestParameterTest sampleGC = new GetCapabilitiesRequestParameterTest();
		 sampleGC.initBaseFixture(testContext);
		 sampleGC.initParser();
		 sampleGC.buildGetCapabilitiesRequest();
		 sampleGC.wmsCapabilitiesOutputFormatParameterSupported();
		 
		GetMapRequestParametersTest sampleGM = new GetMapRequestParametersTest();
		sampleGM.initBaseFixture(testContext);
		sampleGM.initParser();
 		sampleGM.buildGetMapRequest();
		sampleGM.setResultDirectory(testContext);
		sampleGM.wmsGetMapRequestParametersSupported(testContext);
	}	
	
}