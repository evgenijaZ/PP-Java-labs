package edu.kpi.comsys.parallel_computing.lab5.callable;

import edu.kpi.comsys.parallel_computing.lab5.data.Matrix;
import edu.kpi.comsys.parallel_computing.lab5.util.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

import static edu.kpi.comsys.parallel_computing.lab5.Main.C;
import static edu.kpi.comsys.parallel_computing.lab5.Main.D;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MD;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MF;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MT;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MZ;
import static edu.kpi.comsys.parallel_computing.lab5.Main.a;

public class CallableMF implements Callable<Matrix> {
    private static final Logger LOG = LoggerFactory.getLogger(CallableMF.class);
    private int id;
    private Operations operations;
    private CyclicBarrier barrier;
    private ReentrantLock lock;
    private BlockingDeque<Matrix> blockingQueue;

    public CallableMF(int id, Operations operations, CyclicBarrier barrier, ReentrantLock lock, BlockingDeque<Matrix> blockingQueue) {
        this.id = id;
        this.operations = operations;
        this.barrier = barrier;
        this.lock = lock;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public Matrix call() throws BrokenBarrierException, InterruptedException {
        LOG.info("[MF-{}] started... \n", id);
        LOG.info("[MF-{}] calculating MF=min(D+C)*MD*MT+MZ*MG*a ... \n", id);
        lock.lock();
        barrier.await();
        Matrix MG = blockingQueue.peekFirst();
        MF = operations.calculateMF(D, C, MD, MT, MZ, MG, a, id, MF);
        lock.unlock();
        LOG.info("[MF-{}] finished calculating MF : \n{}", id, MF);
        LOG.info("[MF-{}] finished... \n", id);
        return MF;
    }
}