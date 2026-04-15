package com.example.kaf.notify.service;

import com.example.kaf.notify.model.Log;
import com.example.kaf.notify.model.LogEventRequest;
import com.example.kaf.notify.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public void logEvent(LogEventRequest request) {
        // Create log entity
        Log log = new Log();
        log.setType(request.getType());
        log.setEntityId(request.getId());
        log.setCustomerId(request.getCustomerId());
        log.setAmount(request.getAmount());
        log.setDetails(request.getDetails());

        // Save to database
        Log savedLog = logRepository.save(log);

    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }
}
