package cn.bittx.nws;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class BeepInSchedule {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    // ScheduledExecutorService created with 3 threads

    @SuppressWarnings("rawtypes")
    public static void beepForAnHour() {

        final Runnable beeper = new Runnable() {
            public void run() {
                System.out.println("beep :: " + LocalDateTime.now()); }
        }; // Creating a new runnable task which will be passed as an argument to scheduler

        ScheduledFuture beeperHandleAtFixedRate = scheduler.scheduleAtFixedRate(beeper, 5, 5, SECONDS);
        // Creates and executes a ScheduledFuture that becomes enabled after 5 seconds and gets executed with fixed rate of 5 seconds

        ScheduledFuture beeperHandleArFixedDelay = scheduler.scheduleWithFixedDelay(beeper, 5, 5, SECONDS);
        // Creates and executes a ScheduledFuture that becomes enabled after 5 seconds and gets executed with fixed delay of 5 seconds

        scheduler.schedule(new Runnable() {
            public void run() { beeperHandleAtFixedRate.cancel(true); } // Attempts to cancel execution of task beeperHandleAtFixedRate after one hour
        }, 60 * 60, SECONDS); // Creates and executes a one-shot action that becomes enabled after the given delay.

        scheduler.schedule(new Runnable() {
            public void run() { beeperHandleArFixedDelay.cancel(true); } // Attempts to cancel execution of task beeperHandleArFixedDelay after one hour
        }, 60 * 60, SECONDS);
    }

    public static void main(String[] args) {
        beepForAnHour();
    }
}
