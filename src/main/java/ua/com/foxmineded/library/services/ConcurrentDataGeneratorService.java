package ua.com.foxmineded.library.services;

public interface ConcurrentDataGeneratorService {
	void generateConcurrently(Runnable ... runnable);
}
