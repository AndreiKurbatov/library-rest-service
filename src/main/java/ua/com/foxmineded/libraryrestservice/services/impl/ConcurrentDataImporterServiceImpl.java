package ua.com.foxmineded.libraryrestservice.services.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.libraryrestservice.services.ConcurrentDataImporterService;

@Slf4j
@Service
public class ConcurrentDataImporterServiceImpl implements ConcurrentDataImporterService {

	@SneakyThrows
	@Override
	public void importConcurrently(Runnable... runnable) {
		int size = runnable.length;
		ExecutorService executorService = Executors.newFixedThreadPool(size);
		for (Runnable task : runnable) {
			executorService.execute(task);
		}
		executorService.shutdown();
		try {
			while (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
