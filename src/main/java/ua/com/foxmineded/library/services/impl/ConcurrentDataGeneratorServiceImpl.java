package ua.com.foxmineded.library.services.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.universitycms.services.ConcurrentDataGeneratorService;

@Slf4j
@Service
public class ConcurrentDataGeneratorServiceImpl implements ConcurrentDataGeneratorService{

	@Override
	public void generateConcurrently(Runnable... runnable) {
		int size = runnable.length;
		ExecutorService executorService = Executors.newFixedThreadPool(size);
		for (Runnable task : runnable) {
			executorService.execute(task);
		}
		executorService.shutdown();
	    try {
	        while (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {}
	    } catch (InterruptedException e) {
	    	log.error(e.getMessage());
	    }
	}
}
