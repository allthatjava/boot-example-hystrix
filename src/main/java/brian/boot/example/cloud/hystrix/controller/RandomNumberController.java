package brian.boot.example.cloud.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import brian.boot.example.cloud.hystrix.service.RandomNumberService;

@RestController
public class RandomNumberController {
	@Autowired
	RandomNumberService service;

	@RequestMapping(value="/randomTest")
	public String getRandomNumber() {
		return service.getRandomNumber()+"";
	}
	
	@RequestMapping(value="/delayedRandomTest/{counter}")
	public String getDelayedRandomNumber(@PathVariable("counter") int counter) {
		
		return service.getDelayedRandomNumber()+"";
	}
	
	@ExceptionHandler({HystrixRuntimeException.class})
	protected ResponseEntity<Object> handleConflict(HystrixRuntimeException ex, WebRequest req) {
        String bodyOfResponse = "The request "+req.getContextPath()+" has been timed out. Sorry~";
        
		return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
