package brian.boot.example.cloud.hystrix.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RandomeNumberControllerTest {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@LocalServerPort
    private int port;
	
	/**
	 * Positive Test case. Should return the number that was given
	 */
	@Test
	public void testRandomTest_shouldReturnGivenNumber() {
		int testNumber = 8;
		 assertThat(
				 this.restTemplate.getForObject("http://localhost:" + port + "/numberChecker/"+testNumber, String.class)
				 ).isEqualTo(testNumber+"");
	}
	
	/**
	 * Negative test case. Should return -1. - Hystrix Fallback method would be called
	 */
	@Test
	public void testRandomTest_shouldReturnMinusOne() {
		int testNumber = 3;
		 assertThat(
				 this.restTemplate.getForObject("http://localhost:" + port + "/numberChecker/"+testNumber, String.class)
				 ).isEqualTo("-1");
	}
	
	/**
	 * Positive test case. Should return the number that was given
	 */
	@Test
	public void testDelayedRandomTest_shouldReturnGivenNumber() {
		int timeDelay = 200;
		 assertThat(
				 this.restTemplate.getForObject("http://localhost:" + port + "/delayedTimeTest/"+timeDelay, String.class)
				 ).isEqualTo(timeDelay+"");
	}
	
	/**
	 * Negative test case. HystrixRuntimeException will be triggered when it takes longer than Hystrix timeout in application.yml 
	 */
	@Test
	public void testDelayedRandomTest_shouldReturn() {
		int timeDelay = 10000;
		 assertThat(
				 this.restTemplate.getForObject("http://localhost:" + port + "/delayedTimeTest/"+timeDelay, String.class)
				 ).endsWith("The request uri=/delayedTimeTest/10000 has been timed out. Sorry~");
	}
	
}
