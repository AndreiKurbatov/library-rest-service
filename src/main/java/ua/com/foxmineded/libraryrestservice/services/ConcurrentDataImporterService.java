package ua.com.foxmineded.libraryrestservice.services;

import org.springframework.transaction.annotation.Transactional;

public interface ConcurrentDataImporterService {
	@Transactional
	void importConcurrently(Runnable... runnable);
}
