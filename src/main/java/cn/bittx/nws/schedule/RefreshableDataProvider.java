package cn.bittx.nws.schedule;

import cn.bittx.nws.session.GroupedSession;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class RefreshableDataProvider {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    // ScheduledExecutorService created with 3 threads

    @SuppressWarnings("rawtypes")
    public static void refreshForAnHour() {

        final Runnable refreshAndPush = new Runnable() {
            public void run() {
                String newData = "dataFromRedis";
                GroupedSession.getGroupNames().forEach(o->{
                    GroupedSession.push(o," Topic:"+ o +" "+ newData + " ts:"+ LocalDateTime.now());
                });
                System.out.println("Refreshed: :: " + LocalDateTime.now()); }
        }; // Creating a new runnable task which will be passed as an argument to scheduler

        ScheduledFuture refreshHandleAtFixedRate = scheduler.scheduleAtFixedRate(refreshAndPush, 5, 5, SECONDS);
        // Creates and executes a ScheduledFuture that becomes enabled after 5 seconds and gets executed with fixed rate of 5 seconds

        //ScheduledFuture refreshHandleArFixedDelay = scheduler.scheduleWithFixedDelay(refreshAndPush, 5, 5, SECONDS);
        // Creates and executes a ScheduledFuture that becomes enabled after 5 seconds and gets executed with fixed delay of 5 seconds

        scheduler.schedule(new Runnable() {
            public void run() { refreshHandleAtFixedRate.cancel(true); } // Attempts to cancel execution of task beeperHandleAtFixedRate after one hour
        }, 60 * 60, SECONDS); // Creates and executes a one-shot action that becomes enabled after the given delay.

//        scheduler.schedule(new Runnable() {
//            public void run() { refreshHandleArFixedDelay.cancel(true); } // Attempts to cancel execution of task beeperHandleArFixedDelay after one hour
//        }, 60 * 60, SECONDS);
    }
}
