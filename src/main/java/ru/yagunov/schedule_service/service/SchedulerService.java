package ru.yagunov.schedule_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SchedulerService {
    @Scheduled(cron = "${app.scheduler.user-events-update.process.cron}")
    public void updateUserEventsInfo() {
        log.info("------------Running every minute------------");
    }
}
