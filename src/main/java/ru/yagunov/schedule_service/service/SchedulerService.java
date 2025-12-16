package ru.yagunov.schedule_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final ru.yagunov.data_service.api.UserEventApi dataServiceFeignClient;
    private final ru.yagunov.report_service.api.UserEventApi reportServiceFeignClient;

    @Scheduled(cron = "${app.scheduler.user-events-update.process.cron}")
    public void updateUserEventsInfo() {
        log.info("------------Running cron every minute------------");

        try {
            List<ru.yagunov.data_service.model.UserEvent> userEvents = dataServiceFeignClient.eventsGet();
            log.info("Successfully receiving {} events from data_service", userEvents.size());
            List<ru.yagunov.report_service.model.UserEvent> reportServiceUserEvents = new ArrayList<>();

            for (ru.yagunov.data_service.model.UserEvent event : userEvents) {
                ru.yagunov.report_service.model.UserEvent reportServiceEvent = new ru.yagunov.report_service.model.UserEvent();
                reportServiceEvent.setEventTime(event.getEventTime());
                reportServiceEvent.setEventType(event.getEventType());

                reportServiceUserEvents.add(reportServiceEvent);
            }

            reportServiceFeignClient.eventsPost(reportServiceUserEvents);
            log.info("Successfully recorded {} events to report_service", reportServiceUserEvents.size());
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
