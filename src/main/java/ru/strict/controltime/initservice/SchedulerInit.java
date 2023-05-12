package ru.strict.controltime.initservice;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.strict.controltime.timemanager.adapter.scheduler.manager.ProcessTasksScheduler;
import ru.strict.controltime.view.manager.scheduler.ComputerWorkDurationScheduler;
import ru.strict.controltime.view.manager.scheduler.RefreshTimeManagerScheduler;
import ru.strict.ioc.annotation.Configuration;

@Slf4j
public class SchedulerInit {

    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class Start {
        ComputerWorkDurationScheduler computerWorkDurationScheduler;
        RefreshTimeManagerScheduler refreshTimeManagerScheduler;
        ProcessTasksScheduler processTasksScheduler;

        // TODO: заменить нна worker pool
        @Configuration
        public void startComputerWorkDurationScheduler() {
            new Thread(() -> {
                while(true) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException ex) {
                        log.error("ComputerWorkDurationScheduler error", ex);
                        throw new RuntimeException(ex);
                    }

                    computerWorkDurationScheduler.refreshWorkDuration();
                }
            }).start();
        }

        // TODO: заменить нна worker pool
        @Configuration
        public void startRefreshTimeManagerScheduler() {
            new Thread(() -> {
                while(true) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException ex) {
                        log.error("RefreshTimeManagerScheduler error", ex);
                        throw new RuntimeException(ex);
                    }

                    refreshTimeManagerScheduler.refreshTimeManager();
                }
            }).start();
        }

        // TODO: заменить нна worker pool
        @Configuration
        public void startProcessTasksScheduler() {
            new Thread(() -> {
                while(true) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException ex) {
                        log.error("ProcessTasksScheduler error", ex);
                        throw new RuntimeException(ex);
                    }

                    processTasksScheduler.processTasks();
                }
            }).start();
        }
    }
}
