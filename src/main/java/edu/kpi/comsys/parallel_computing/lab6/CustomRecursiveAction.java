package edu.kpi.comsys.parallel_computing.lab6;

import edu.kpi.comsys.parallel_computing.lab6.util.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import static edu.kpi.comsys.parallel_computing.lab6.Main.A;
import static edu.kpi.comsys.parallel_computing.lab6.Main.B;
import static edu.kpi.comsys.parallel_computing.lab6.Main.C;
import static edu.kpi.comsys.parallel_computing.lab6.Main.D;
import static edu.kpi.comsys.parallel_computing.lab6.Main.E;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MA;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MB;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MC;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MD;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MF;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MG;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MH;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MK;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MM;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MT;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MX;
import static edu.kpi.comsys.parallel_computing.lab6.Main.MZ;
import static edu.kpi.comsys.parallel_computing.lab6.Main.N;
import static edu.kpi.comsys.parallel_computing.lab6.Main.a;

public class CustomRecursiveAction extends RecursiveAction {
    private static Operations operations = new Operations(N);

    private static final int THRESHOLD = 2;
    private final int workload;
    private int from;
    private int to;
    private static final Logger LOG = LoggerFactory.getLogger(CustomRecursiveAction.class);

    public CustomRecursiveAction(int from, int to) {
        this.from = from;
        this.to = to;
        this.workload = to - from;
    }

    @Override
    protected void compute() {
        if (workload > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            processing(from, to);
        }
    }

    private List<CustomRecursiveAction> createSubtasks() {
        List<CustomRecursiveAction> subtasks = new ArrayList<>();
        int diff = (to - from) / 2;

        subtasks.add(new CustomRecursiveAction(from, from + diff));
        subtasks.add(new CustomRecursiveAction(from + diff, to));

        return subtasks;
    }

    private void processing(int from, int to) {
        LOG.info("{}-{} started... \n", from, to);
        LOG.info("[{}-{}] calculating А=В*МС+D*MZ+E*MM ...\n", from, to);
        operations.calculateA(B, D, E, MC, MZ, MM, from, to, A);
        LOG.info("[{}-{}] finished calculating A : \n{}", from, to, A);
        LOG.info("[{}-{}] calculating C=В*МС-D*M ... \n", from, to);
        operations.calculateC(B, D, MC, MM, from, to, C);
        LOG.info("[{}-{}] finished calculating C : \n{}", from, to, C);
        LOG.info("[{}-{}] calculating МА=МВ*MK+МС*МХ*(MT+MM) ... \n", from, to);
        operations.calculateMA(MB, MK, MC, MX, MH, from, to, MA);
        LOG.info("[{}-{}] finished calculating MA : \n{}", from, to, MA);
        LOG.info("[{}-{}] calculating MF=min(D+C)*MD*MT+MZ*MG*a ... \n", from, to);
        operations.calculateMF(D, C, MD, MT, MZ, MG, a, from, to, MF);
        LOG.info("[{}-{}] finished calculating MF : \n{}", from, to, MF);
        LOG.info("{}-{} finished... \n", from, to);
    }
}