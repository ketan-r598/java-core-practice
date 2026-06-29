package io.java_core;

import io.java_core.taskschedulerwithratelimiting.model.Task;
import io.java_core.taskschedulerwithratelimiting.model.TaskStatus;
import io.java_core.taskschedulerwithratelimiting.service.RateLimiter;
import io.java_core.taskschedulerwithratelimiting.service.TaskExecutor;
import io.java_core.taskschedulerwithratelimiting.service.TaskScheduler;

import java.util.concurrent.Callable;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        TaskScheduler taskScheduler = new TaskScheduler();
        RateLimiter rateLimiter = new RateLimiter();
        TaskExecutor taskExecutor = new TaskExecutor(taskScheduler, rateLimiter);

        taskExecutor.start();

        for (int i = 1; i <= 50; ++i) {
            int finalI = i;
            Callable<String> payload = () -> {
                Thread.sleep(500);
                return "Thread - [ " + Thread.currentThread().getName() + " ] has completed the task - " + finalI + "th with owner - " + (finalI % 2);
            };
            Task<String> task00 = new Task(i * 10, i % 2, TaskStatus.PENDING, payload);

            if (!taskScheduler.addTask(task00)) {
                System.out.println("Task - " + i + " Queue is full, retry again....");
            }
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            taskExecutor.shutdown();
            Thread.currentThread().interrupt();
        }

        taskExecutor.shutdown();

//        ========================================================================

//        Signalling Pattern Demo (using semaphore)

//        SingnallingDemo singnallingDemo = new SingnallingDemo();
//
//        Thread aTask = new Thread(singnallingDemo::taskA);
//        Thread bTask = new Thread(singnallingDemo::taskB);
//
//        aTask.start();
//        bTask.start();

//        SignallingDemo signallingDemoJava = new SignallingDemo();
//        Thread aTask = new Thread(signallingDemoJava::taskA);
//        Thread bTask = new Thread(signallingDemoJava::taskB);
//
//        aTask.start();
//        bTask.start();

//        =========================================================================
//        =========================================================================

//        Rendezvous Pattern Demo

//        RendezvousDemo rendezvousDemo = new RendezvousDemo();
//
//        Thread aTask = new Thread(rendezvousDemo::taskA);
//        Thread bTask = new Thread(rendezvousDemo::taskB);
//
//        aTask.start();
//        bTask.start();

//        RendezvousDemo rendezvousDemoJava = new RendezvousDemo();
//
//        Thread t1 = new Thread(rendezvousDemoJava::taskA);
//        Thread t2 = new Thread(rendezvousDemoJava::taskB);
//
//        t1.start();
//        t2.start();

//        ==========================================================================
//        ==========================================================================

//        Mutex Pattern Demo

//        MutexDemo mutexDemo = new MutexDemo();
//
//        Thread t1 = new Thread(mutexDemo::increment);
//        Thread t2 = new Thread(mutexDemo::decrement);
//
//        t1.start();
//        t2.start();
//
//        t1.join();
//        t2.join();
//
//        mutexDemo.displayCount();
//
//        MutexDemo mutexDemoJava = new MutexDemo();
//
//        Thread t1 = new Thread(mutexDemoJava::increment);
//        Thread t2 = new Thread(mutexDemoJava::decrement);
//
//        t1.start();
//        t2.start();
//
//        t1.join();
//        t2.join();
//
//        mutexDemoJava.display();
//        ===========================================================================
//        ===========================================================================

//        Multiplex Pattern Demo

//        MultiplexDemo multiplexDemo = new MultiplexDemo();
//
//        for(int i = 0; i < 5; ++i) {
//            new Thread(multiplexDemo::task).start();
//        }

//        ===========================================================================
//        ===========================================================================
//        Barrier Pattern Demo

//        BarrierDemo barrierDemo = new BarrierDemo();
//
//        for(int i = 0; i < 3; ++i) {
//            new Thread(barrierDemo::task).start();
//        }

//        BarrierDemo barrierDemoJava = new BarrierDemo(5);
//
//        for(int i = 0; i < 5; ++i) {
//            new Thread(barrierDemoJava::task).start();
//        }
//        ===========================================================================
//        ===========================================================================
//        Reusable Barrier Pattern Demo

//        ReusableBarrierDemo reusableBarrierDemo = new ReusableBarrierDemo(3);
//
//        for(int i = 0; i < 3; ++i) {
//            new Thread(reusableBarrierDemo::task).start();
//        }
//
//        ReusableBarrierDemo reusableBarrierDemoJava = new ReusableBarrierDemo(5);
//        for(int i = 0; i < 5; ++i) {
//            new Thread(reusableBarrierDemoJava::task).start();
//        }

//        ===========================================================================
//        ===========================================================================
//        Queue Pattern Demo

//        QueueDemo queueDemo = new QueueDemo(3);
//
//        for(int i = 0; i < 3; ++i) {
//            new Thread(queueDemo::leader).start();
//            new Thread(queueDemo::follower).start();
//        }
//        ===========================================================================
//        ===========================================================================
    }
}
