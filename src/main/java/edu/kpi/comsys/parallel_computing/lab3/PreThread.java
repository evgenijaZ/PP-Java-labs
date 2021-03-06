package edu.kpi.comsys.parallel_computing.lab3;

import edu.kpi.comsys.parallel_computing.lab3.util.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

import static edu.kpi.comsys.parallel_computing.lab3.Main.H;
import static edu.kpi.comsys.parallel_computing.lab3.Main.ME;
import static edu.kpi.comsys.parallel_computing.lab3.Main.MG;
import static edu.kpi.comsys.parallel_computing.lab3.Main.MH;
import static edu.kpi.comsys.parallel_computing.lab3.Main.MM;
import static edu.kpi.comsys.parallel_computing.lab3.Main.MT;
import static edu.kpi.comsys.parallel_computing.lab3.Main.N;

public class PreThread extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(PreThread.class);
    private static Operations operations = new Operations(H, N);
    private final CountDownLatch barrier;
    private int id;

    PreThread(int id, CountDownLatch barrier) {
        this.id = id;
        this.barrier = barrier;
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
        barrier.countDown();
        LOG.info("{} finished... \n", id);
    }
}