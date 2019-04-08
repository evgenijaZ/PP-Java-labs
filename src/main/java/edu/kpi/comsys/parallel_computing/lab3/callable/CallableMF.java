package edu.kpi.comsys.parallel_computing.lab3.callable;

import edu.kpi.comsys.parallel_computing.lab3.data.Matrix;
import edu.kpi.comsys.parallel_computing.lab3.util.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

import static edu.kpi.comsys.parallel_computing.lab3.Main.C;
import static edu.kpi.comsys.parallel_computing.lab3.Main.D;
import static edu.kpi.comsys.parallel_computing.lab3.Main.MD;
import static edu.kpi.comsys.parallel_computing.lab3.Main.MF;
import static edu.kpi.comsys.parallel_computing.lab3.Main.MG;
import static edu.kpi.comsys.parallel_computing.lab3.Main.MT;
import static edu.kpi.comsys.parallel_computing.lab3.Main.MZ;
import static edu.kpi.comsys.parallel_computing.lab3.Main.a;

public class CallableMF implements Callable<Matrix> {
    private static final Logger LOG = LoggerFactory.getLogger(CallableMF.class);
    private int id;
    private Operations operations;
    private CyclicBarrier barrier;

    public CallableMF(int id, Operations operations, CyclicBarrier barrier) {
        this.id = id;
        this.operations = operations;
        this.barrier = barrier;
    }

    @Override
    public Matrix call() throws BrokenBarrierException, InterruptedException {
        LOG.info("[MF-{}] started... \n", id);
        LOG.info("[MF-{}] calculating MF=min(D+C)*MD*MT+MZ*MG*a ... \n", id);
        barrier.await();
        MF = operations.calculateMF(D, C, MD, MT, MZ, MG, a, id, MF);
        LOG.info("[MF-{}] finished calculating MF : \n{}", id, MF);
        LOG.info("[MF-{}] finished... \n", id);
        return MF;
    }
}