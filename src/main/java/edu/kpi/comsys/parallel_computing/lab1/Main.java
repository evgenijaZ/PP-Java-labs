package edu.kpi.comsys.parallel_computing.lab1;

import edu.kpi.comsys.parallel_computing.lab1.data.Matrix;
import edu.kpi.comsys.parallel_computing.lab1.data.Vector;
import edu.kpi.comsys.parallel_computing.lab1.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.concurrent.CountDownLatch;

import static java.lang.System.currentTimeMillis;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static double min = Double.MAX_VALUE;
    static int N = 12;
    static int p = 4;
    static int H = N / p;
    static int a = 1;
    static Vector A = new Vector(N);
    static Vector B = new Vector(N);
    static Vector C = new Vector(N);
    static Vector D = new Vector(N);
    static Vector E = new Vector(N);
    static Matrix MA = new Matrix(N);
    static Matrix MB = new Matrix(N);
    static Matrix MC = new Matrix(N);
    static Matrix MD = new Matrix(N);
    static Matrix ME = new Matrix(N);
    static Matrix MF = new Matrix(N);
    static Matrix MG = new Matrix(N);
    static Matrix MH = new Matrix(N);
    static Matrix MK = new Matrix(N);
    static Matrix MM = new Matrix(N);
    static Matrix MT = new Matrix(N);
    static Matrix MX = new Matrix(N);
    static Matrix MZ = new Matrix(N);
    private static FileUtil fileUtil = new FileUtil();

    public static void main(String[] args) throws Exception {
//        fileUtil.generateFile();
        readAll();
        long start;
        long stop;
        LOG.info("Main thread started");
        start = currentTimeMillis();
        CountDownLatch barrier = new CountDownLatch(p);
        for (int i = 0; i < p; i++) {
            PreThread thread = new PreThread(i, barrier);
            thread.start();
//            runThread(i, thread);
        }
        barrier.await();

        for (int i = 0; i < p; i++) {
            MyThread thread = new MyThread(i);
            runThread(i, thread);
        }
        stop = currentTimeMillis();
        LOG.info("Time: {}", stop - start);
        LOG.info("Writing results to files...");
        writeAll();
        LOG.info("Main thread finished");
    }

    private static void runThread(int i, Thread thread) {
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            LOG.info("{} has been interrupted", i, e);
            Thread.currentThread().interrupt();
        }
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
