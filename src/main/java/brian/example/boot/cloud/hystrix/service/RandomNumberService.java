package brian.example.boot.cloud.hystrix.service;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class RandomNumberService {
	
	/**
	 * Returns the number that is given if it is greater or equal to 5.
	 * If it is less than 5, it will throws the exception and FallbackMethod will be called
	 * 
	 * @param number
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "getRandomNumberFallback",
					threadPoolKey="randomNumberPool",
					groupKey="RandomNumberService"
					)
	public int checkNumber(int number) {
		
		System.out.println("checkNumber got "+number);
		
		if( number < 5)
			throw new RuntimeException("Lower than 5 number has returned. Thrwoing RuntimeException");

		System.out.println("Number is greater than 5");
		
		return number;
	}
	
	/**
	 * Fallback method for checkNumber(...). If checkNumber(...) throws any exception, this method will be called.
	 * 
	 * @param number
	 * @return
	 */
	public int getRandomNumberFallback(int number) {

		System.out.println("Something went wrong - Fallback method getRandomNumberFallback("+number+") is called");
		
		return -1;
	}
	
	/**
	 * Returns the given timeDelay if it is less than 500 (500 milliseconds).
	 * Otherwise it will trigger the HystrixRuntimeException
	 * 
	 * @param timeDelay
	 * @return
	 */
	@HystrixCommand(
					threadPoolKey="delayedRandomNumberPool",
					groupKey="RandomNumberService"
					)
	public int getDelayedRandomNumber(int timeDelay) {
		
		try {
			System.out.println("Delaying starts...");
			Thread.sleep(timeDelay); // The execution will be delayed this long
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Delaying ended...");
		}
		
		return timeDelay;
	}
}
