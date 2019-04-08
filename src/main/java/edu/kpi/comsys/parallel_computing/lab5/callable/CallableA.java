package edu.kpi.comsys.parallel_computing.lab5.callable;

import edu.kpi.comsys.parallel_computing.lab5.data.Vector;
import edu.kpi.comsys.parallel_computing.lab5.util.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

import static edu.kpi.comsys.parallel_computing.lab5.Main.A;
import static edu.kpi.comsys.parallel_computing.lab5.Main.B;
import static edu.kpi.comsys.parallel_computing.lab5.Main.D;
import static edu.kpi.comsys.parallel_computing.lab5.Main.E;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MC;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MM;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MZ;

public class CallableA implements Callable<Vector> {
    private static final Logger LOG = LoggerFactory.getLogger(CallableMF.class);
    private int id;
    private Operations operations;
    private CyclicBarrier barrier;

    public CallableA(int id, Operations operations, CyclicBarrier barrier) {
        this.id = id;
        this.operations = operations;
        this.barrier = barrier;
    }

    @Override
    public Vector call() throws BrokenBarrierException, InterruptedException {
        LOG.info("[A-{}] started... \n", id);
        LOG.info("[A-{}] calculating А=В*МС+D*MZ+E*MM ...\n", id);
        A = operations.calculateA(B, D, E, MC, MZ, MM, id, A);
        LOG.info("[A-{}] finished calculating A : \n{}", id, A);

        barrier.await();

        LOG.info("[A-{}] finished... \n", id);
        return A;
    }
}
