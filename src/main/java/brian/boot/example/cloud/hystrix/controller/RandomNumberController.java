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

	/**
	 * Returns the number that is given if it is less than 5.
	 * Otherwise it will return -1
	 * @param number
	 * @return
	 */
	@RequestMapping(value="/numberChecker/{number}")
	public String getNumberChecker(@PathVariable("number") int number) {
		return service.checkNumber(number)+"";
	}
	
	/**
	 * Returns the timeDelay that is give if it is less than 5000 (5 seconds).
	 * 										---> Check application.yml for this setup.
	 * Otherwise, it will return error message
	 * 
	 * @param timeDelay
	 * @return
	 */
	@RequestMapping(value="/delayedTimeTest/{timeDelay}")
	public String getDelayedTimeCheck(@PathVariable("timeDelay") int timeDelay) {
		
		return service.getDelayedRandomNumber(timeDelay)+"";
	}
	
	/**
	 * Handles the Hystrix Time out exception.
	 * 
	 * @param ex
	 * @param req
	 * @return
	 */
	@ExceptionHandler({HystrixRuntimeException.class})
	protected ResponseEntity<Object> handleConflict(HystrixRuntimeException ex, WebRequest req) {
        String bodyOfResponse = "The request "+req.getDescription(false)+" has been timed out. Sorry~";
        System.out.println(bodyOfResponse);
        
		return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
