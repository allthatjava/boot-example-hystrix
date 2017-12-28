package brian.boot.example.cloud.hystrix.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class RandomNumberService {
	
	@HystrixCommand(fallbackMethod = "getRandomNumberFallback",
			threadPoolKey="randomNumberPool",
			groupKey="RandomNumberService"
			)
	public int getRandomNumber() {
		Random r = new Random();
		int number = (r.nextInt() & Integer.MAX_VALUE)%10;
		
		System.out.println("getRandomNumber got "+number);
		
		if( number < 2)
			throw new RuntimeException("Lower than 2 number has returned. Thrwoing RuntimeException");
		
		return number;
	}
	
	public int getRandomNumberFallback() {
		return -1;
	}
	
	@HystrixCommand(
			threadPoolKey="delayedRandomNumberPool",
			groupKey="RandomNumberService"
	)
	public int getDelayedRandomNumber() {
		Random r = new Random();
		int number = r.nextInt();
		int delay = (r.nextInt() & Integer.MAX_VALUE)%7;

		System.out.println("getDelayedRandomNumber will be delayed for "+(delay*1000));
		
		try {
			Thread.sleep(delay*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return number;
	}
}
