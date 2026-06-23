package io.java_core;

import io.java_core.basicconcurrencypatterns.javaconcurrencyutilities.*;
import io.java_core.basicconcurrencypatterns.javaconcurrencyutilities.BarrierDemo;
import io.java_core.basicconcurrencypatterns.javaconcurrencyutilities.ReusableBarrierDemo;
import io.java_core.basicconcurrencypatterns.semaphores.*;
import io.java_core.basicconcurrencypatterns.semaphores.QueueDemo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {

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

        QueueDemo queueDemo = new QueueDemo(3);

        for(int i = 0; i < 3; ++i) {
            new Thread(queueDemo::leader).start();
            new Thread(queueDemo::follower).start();
        }
//        ===========================================================================
//        ===========================================================================
    }
}
