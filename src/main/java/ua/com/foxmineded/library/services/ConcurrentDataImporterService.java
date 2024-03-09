package ua.com.foxmineded.library.services;

public interface ConcurrentDataImporterService {
	void importConcurrently(Runnable ... runnable);
}
