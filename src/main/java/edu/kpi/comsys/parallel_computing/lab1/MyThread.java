package edu.kpi.comsys.parallel_computing.lab1;

import edu.kpi.comsys.parallel_computing.lab1.util.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static edu.kpi.comsys.parallel_computing.lab1.Main.A;
import static edu.kpi.comsys.parallel_computing.lab1.Main.B;
import static edu.kpi.comsys.parallel_computing.lab1.Main.C;
import static edu.kpi.comsys.parallel_computing.lab1.Main.D;
import static edu.kpi.comsys.parallel_computing.lab1.Main.E;
import static edu.kpi.comsys.parallel_computing.lab1.Main.H;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MA;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MB;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MC;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MD;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MF;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MG;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MH;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MK;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MM;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MT;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MX;
import static edu.kpi.comsys.parallel_computing.lab1.Main.MZ;
import static edu.kpi.comsys.parallel_computing.lab1.Main.N;
import static edu.kpi.comsys.parallel_computing.lab1.Main.a;

public class MyThread extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(MyThread.class);
    private static Operations operations = new Operations(H, N);
    private int id;

    MyThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        LOG.info("{} started... \n", id);
        LOG.info("[{}] calculating А=В*МС+D*MZ+E*MM ...\n", id);
        operations.calculateA(B, D, E, MC, MZ, MM, id, A);
        LOG.info("[{}] finished calculating A : \n{}", id, A);
        LOG.info("[{}] calculating C=В*МС-D*M ... \n", id);
        operations.calculateC(B, D, MC, MM, id, C);
        LOG.info("[{}] finished calculating C : \n{}", id, C);
        LOG.info("[{}] calculating МА=МВ*MK+МС*МХ*(MT+MM) ... \n", id);
        operations.calculateMA(MB, MK, MC, MX, MH, id, MA);
        LOG.info("[{}] finished calculating MA : \n{}", id, MA);
        LOG.info("[{}] calculating MF=min(D+C)*MD*MT+MZ*MG*a ... \n", id);
        operations.calculateMF(D, C, MD, MT, MZ, MG, a, id, MF);
        LOG.info("[{}] finished calculating MF : \n{}", id, MF);
        LOG.info("{} finished... \n", id);
    }
}