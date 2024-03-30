package ua.com.foxmineded.library.services;

import org.springframework.transaction.annotation.Transactional;

public interface ConcurrentDataImporterService {
	@Transactional
	void importConcurrently(Runnable ... runnable);
}
