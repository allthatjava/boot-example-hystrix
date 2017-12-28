package brian.boot.example.cloud.hystrix.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class RandomeNumberControllerTest {
	
	TestRestTemplate testTemplate;
	
	@Before
	public void setup() {
		testTemplate = new TestRestTemplate();
	}
	
	@Test
	public void testRandomeNumber() {

		ResponseEntity<String> response;
		
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i <20; i++)
		{
			response = testTemplate.exchange("http://localhost:8080/randomTest", HttpMethod.GET, null,
					String.class);
			
			result.append(response.getBody()+",");
		}
		
		System.out.println( "Result: "+result);
	}
	
	@Test
	public void testDalyedRandomeNumber() {

		ResponseEntity<String> response;
		
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i <20; i++)
		{
			response = testTemplate.exchange("http://localhost:8080/delayedRandomTest/"+i, HttpMethod.GET, null,
					String.class);
			
//			if( HttpStatus.OK != response.getStatusCode() )
//				result.append(response.getBody())
//			else
				result.append(response.getBody()+",");
		}
		
		System.out.println( "Result: "+result);
	}
}
