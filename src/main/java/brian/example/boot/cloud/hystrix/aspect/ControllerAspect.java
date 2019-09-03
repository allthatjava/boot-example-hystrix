package brian.example.boot.cloud.hystrix.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ControllerAspect {

	Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
	
	@Before("execution(* brian.example.boot.cloud.hystrix.controller.*.*(..))")
	public void before(JoinPoint point) {
		
		logger.info("Before triggered ##########");
	}
}
