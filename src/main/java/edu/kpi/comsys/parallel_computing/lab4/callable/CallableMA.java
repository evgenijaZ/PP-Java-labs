package edu.kpi.comsys.parallel_computing.lab4.callable;

import edu.kpi.comsys.parallel_computing.lab4.data.Matrix;
import edu.kpi.comsys.parallel_computing.lab4.util.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

import static edu.kpi.comsys.parallel_computing.lab4.Main.MA;
import static edu.kpi.comsys.parallel_computing.lab4.Main.MB;
import static edu.kpi.comsys.parallel_computing.lab4.Main.MC;
import static edu.kpi.comsys.parallel_computing.lab4.Main.MH;
import static edu.kpi.comsys.parallel_computing.lab4.Main.MK;
import static edu.kpi.comsys.parallel_computing.lab4.Main.MX;

public class CallableMA implements Callable<Matrix> {
    private static final Logger LOG = LoggerFactory.getLogger(CallableMF.class);
    private int id;
    private Operations operations;
    private CyclicBarrier barrier;

    public CallableMA(int id, Operations operations, CyclicBarrier barrier) {
        this.id = id;
        this.operations = operations;
        this.barrier = barrier;
    }

    @Override
    public Matrix call() throws BrokenBarrierException, InterruptedException {
        LOG.info("[MA-{}] started... \n", id);
        LOG.info("[MA-{}] calculating МА=МВ*MK+МС*МХ*(MT+MM) ... \n", id);
        MA = operations.calculateMA(MB, MK, MC, MX, MH, id, MA);
        LOG.info("[MA-{}] finished calculating MA : \n{}", id, MA);
        barrier.await();
        LOG.info("[MA-{}] finished... \n", id);
        return MA;
    }
}