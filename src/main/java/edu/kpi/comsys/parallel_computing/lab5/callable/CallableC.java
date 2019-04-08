package edu.kpi.comsys.parallel_computing.lab5.callable;

import edu.kpi.comsys.parallel_computing.lab5.data.Vector;
import edu.kpi.comsys.parallel_computing.lab5.util.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

import static edu.kpi.comsys.parallel_computing.lab5.Main.B;
import static edu.kpi.comsys.parallel_computing.lab5.Main.C;
import static edu.kpi.comsys.parallel_computing.lab5.Main.D;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MC;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MM;

public class CallableC implements Callable<Vector> {
    private static final Logger LOG = LoggerFactory.getLogger(CallableC.class);
    private int id;
    private Operations operations;
    private CyclicBarrier barrier;

    public CallableC(int id, Operations operations, CyclicBarrier barrier) {
        this.id = id;
        this.operations = operations;
        this.barrier = barrier;
    }

    @Override
    public Vector call() throws BrokenBarrierException, InterruptedException {
        LOG.info("[C-{}] calculating C=В*МС-D*M ... \n", id);
        C = operations.calculateC(B, D, MC, MM, id, C);
        LOG.info("[C-{}] finished calculating C : \n{}", id, C);
        barrier.await();
        LOG.info("[C-{}] finished... \n", id);
        return C;
    }
}
