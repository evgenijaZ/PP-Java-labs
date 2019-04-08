package edu.kpi.comsys.parallel_computing.lab5;

import edu.kpi.comsys.parallel_computing.lab5.data.Matrix;
import edu.kpi.comsys.parallel_computing.lab5.util.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import static edu.kpi.comsys.parallel_computing.lab5.Main.H;
import static edu.kpi.comsys.parallel_computing.lab5.Main.ME;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MG;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MH;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MM;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MT;
import static edu.kpi.comsys.parallel_computing.lab5.Main.N;

public class PreThread extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(PreThread.class);
    private static Operations operations = new Operations(H, N);
    private final CountDownLatch barrier;
    private BlockingQueue<Matrix> blockingQueue;
    private int id;

    PreThread(int id, CountDownLatch barrier, BlockingQueue<Matrix> blockingQueue) {
        this.id = id;
        this.barrier = barrier;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        LOG.info("{} started... \n", id);
        LOG.info("[{}] calculating MG=ME+MM ... \n", id);
        operations.calculateMG(ME, MM, id, MG);
        LOG.info("[{}] finished calculating MG : \n{}", id, MG);
        LOG.info("[{}] calculating MH=MT+MM ... \n", id);
        operations.calculateMH(MT, MM, id, MH);
        LOG.info("[{}] finished calculating MH : \n{}", id, MH);
        blockingQueue.add(MG);
        blockingQueue.add(MH);
        barrier.countDown();
        LOG.info("{} finished... \n", id);
    }
}