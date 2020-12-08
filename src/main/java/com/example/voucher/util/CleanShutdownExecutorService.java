package com.example.voucher.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Phaser;

public class CleanShutdownExecutorService implements Executor {

    protected ExecutorService executorService;
    protected final List<Future> futureList = new LinkedList<Future>();
    protected Phaser phaser;

    public CleanShutdownExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        phaser = new Phaser();
        phaser.register();
    }

    @Override
    public void execute(final Runnable command) {
        Future future = executorService.submit(
                new Runnable() {
                    @Override
                    public void run() {
                        //add new unarrived party to the phaser
                        phaser.register();
                        try {
                            command.run();
                        } finally {
                            // allow threads to start and deregister self
                            phaser.arriveAndDeregister();
                        }
                    }
                }
        );
        synchronized (futureList) {
            futureList.add(future);
        }
    }

    public void cancelRemainingTasks() {
        synchronized (futureList) {
            for (Future future : futureList) {
                //all threads not assigned to runner WILL NOT be triggered
                future.cancel(true);
            }

            // await all completion
            if (!futureList.isEmpty()) {
                phaser.arriveAndAwaitAdvance();
                futureList.clear();
            }
        }
    }
}
