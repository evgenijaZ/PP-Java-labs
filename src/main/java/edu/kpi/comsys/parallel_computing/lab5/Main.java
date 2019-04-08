package edu.kpi.comsys.parallel_computing.lab5;

import edu.kpi.comsys.parallel_computing.lab5.callable.CallableA;
import edu.kpi.comsys.parallel_computing.lab5.callable.CallableC;
import edu.kpi.comsys.parallel_computing.lab5.callable.CallableMA;
import edu.kpi.comsys.parallel_computing.lab5.callable.CallableMF;
import edu.kpi.comsys.parallel_computing.lab5.data.Matrix;
import edu.kpi.comsys.parallel_computing.lab5.data.Vector;
import edu.kpi.comsys.parallel_computing.lab5.util.FileUtil;
import edu.kpi.comsys.parallel_computing.lab5.util.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.currentTimeMillis;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static double min = Double.MAX_VALUE;
    public static int a = 1;
    static int N = 12;
    public static Vector A = new Vector(N);
    public static Vector B = new Vector(N);
    public static Vector C = new Vector(N);
    public static Vector D = new Vector(N);
    public static Vector E = new Vector(N);
    public static Matrix MA = new Matrix(N);
    public static Matrix MB = new Matrix(N);
    public static Matrix MC = new Matrix(N);
    public static Matrix MD = new Matrix(N);
    public static Matrix ME = new Matrix(N);
    public static Matrix MF = new Matrix(N);
    public static Matrix MG = new Matrix(N);
    public static Matrix MH = new Matrix(N);
    public static Matrix MK = new Matrix(N);
    public static Matrix MM = new Matrix(N);
    public static Matrix MT = new Matrix(N);
    public static Matrix MX = new Matrix(N);
    public static Matrix MZ = new Matrix(N);
    static int p = 4;
    static int H = N / p;
    private static FileUtil fileUtil = new FileUtil();

    public static void main(String[] args) throws Exception {
        fileUtil.generateFile(N);
        readAll();
        long start;
        long stop;
        LOG.info("Main thread started");
        start = currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(p);
        BlockingDeque<Matrix> blockingQueue = new LinkedBlockingDeque<>();
        for (int i = 0; i < p; i++) {
            PreThread thread = new PreThread(i, countDownLatch, blockingQueue);
            thread.start();
        }
        countDownLatch.await();
        Operations operations = new Operations(H, N);
        ExecutorService service = Executors.newFixedThreadPool(p);
        Future<Vector> futureA;
        Future<Vector> futureC;
        Future<Matrix> futureMA;
        Future<Matrix> futureMF;

        CyclicBarrier barrier = new CyclicBarrier(4, () -> LOG.info("Barrier is broken"));

        ReentrantLock lock = new ReentrantLock();

        for (int i = 0; i < p - 1; i++) {
            service.submit(new CallableA(i, operations, barrier));
            service.submit(new CallableC(i, operations, barrier));
            service.submit(new CallableMA(i, operations, barrier, blockingQueue));
            service.submit(new CallableMF(i, operations, barrier, lock, blockingQueue));
        }
        futureA = service.submit(new CallableA(p - 1, operations, barrier));
        futureC = service.submit(new CallableC(p - 1, operations, barrier));
        futureMA = service.submit(new CallableMA(p - 1, operations, barrier, blockingQueue));
        futureMF = service.submit(new CallableMF(p - 1, operations, barrier, lock, blockingQueue));

        A = futureA.get();
        C = futureC.get();
        MA = futureMA.get();
        MF = futureMF.get();

        stop = currentTimeMillis();
        LOG.info("Time: {}", stop - start);
        LOG.info("Writing results to files...");
        writeAll();
        LOG.info("Main thread finished");
    }

    private static void readAll() throws FileNotFoundException {
        B = fileUtil.readVectorFromFile(N);
        D = fileUtil.readVectorFromFile(N);
        E = fileUtil.readVectorFromFile(N);
        MB = fileUtil.readMatrixFromFile(N);
        MC = fileUtil.readMatrixFromFile(N);
        ME = fileUtil.readMatrixFromFile(N);
        MD = fileUtil.readMatrixFromFile(N);
        MK = fileUtil.readMatrixFromFile(N);
        MM = fileUtil.readMatrixFromFile(N);
        MT = fileUtil.readMatrixFromFile(N);
        MX = fileUtil.readMatrixFromFile(N);
        MZ = fileUtil.readMatrixFromFile(N);
    }

    private static void writeAll() {
        fileUtil.printToFile("outA.txt", A);
        fileUtil.printToFile("outC.txt", C);
        fileUtil.printToFile("outMA.txt", MA);
        fileUtil.printToFile("outMF.txt", MF);
    }
}
