package edu.kpi.comsys.parallel_computing.lab5.callable;

import edu.kpi.comsys.parallel_computing.lab5.data.Matrix;
import edu.kpi.comsys.parallel_computing.lab5.util.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

import static edu.kpi.comsys.parallel_computing.lab5.Main.MA;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MB;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MC;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MK;
import static edu.kpi.comsys.parallel_computing.lab5.Main.MX;

public class CallableMA implements Callable<Matrix> {
    private static final Logger LOG = LoggerFactory.getLogger(CallableMF.class);
    private int id;
    private Operations operations;
    private CyclicBarrier barrier;
    private BlockingDeque<Matrix> blockingQueue;

    public CallableMA(int id, Operations operations, CyclicBarrier barrier, BlockingDeque<Matrix> blockingQueue) {
        this.id = id;
        this.operations = operations;
        this.barrier = barrier;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public Matrix call() throws BrokenBarrierException, InterruptedException {
        LOG.info("[MA-{}] started... \n", id);
        LOG.info("[MA-{}] calculating МА=МВ*MK+МС*МХ*(MT+MM) ... \n", id);
        Matrix MH = blockingQueue.peekLast();
        MA = operations.calculateMA(MB, MK, MC, MX, MH, id, MA);
        LOG.info("[MA-{}] finished calculating MA : \n{}", id, MA);
        barrier.await();
        LOG.info("[MA-{}] finished... \n", id);
        return MA;
    }
}