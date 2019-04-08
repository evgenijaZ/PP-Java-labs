package edu.kpi.comsys.parallel_computing.lab1.util;

import edu.kpi.comsys.parallel_computing.lab1.Main;
import edu.kpi.comsys.parallel_computing.lab1.data.Matrix;
import edu.kpi.comsys.parallel_computing.lab1.data.Vector;

import java.util.ArrayList;
import java.util.List;


public class Operations {
    private final int H;
    private final int N;

    public Operations(int h, int n) {
        H = h;
        N = n;
    }

    private Matrix multiplication(Matrix matrix1, Matrix matrix2, int id, Matrix result) {
        int from = id * H;
        int to = (id + 1) * H;
        List<Double> values = new ArrayList<>();
        for (int i = from; i < to; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; ++k) {
                    values.add(matrix1.getElement(i, k) * matrix2.getElement(k, j));
                }
                result.setElement(i, j, sortAndAdd(values));
                values.clear();
            }
        }
        return result;
    }

    private Matrix multiplication(Matrix matrix, double scalar, int id, Matrix result) {
        int from = id * H;
        int to = (id + 1) * H;
        for (int i = from; i < to; i++) {
            for (int j = 0; j < N; j++) {
                result.setElement(i, j, scalar * matrix.getElement(i, j));
            }
        }
        return result;
    }

    private Matrix addition(Matrix matrix1, Matrix matrix2, int id, Matrix result) {
        int from = id * H;
        int to = (id + 1) * H;
        for (int i = from; i < to; i++) {
            for (int j = 0; j < N; j++) {
                result.setElement(i, j, matrix1.getElement(i, j) + matrix2.getElement(i, j));
            }
        }
        return result;
    }

    private Vector addition(Vector vector1, Vector vector2, int id, Vector result) {
        int from = id * H;
        int to = (id + 1) * H;
        for (int i = from; i < to; i++) {
            result.setElement(i, vector1.getElement(i) + vector2.getElement(i));
        }
        return result;
    }

    private Vector substraction(Vector vector1, Vector vector2, int id, Vector result) {
        int from = id * H;
        int to = (id + 1) * H;
        for (int i = from; i < to; i++) {
            result.setElement(i, vector1.getElement(i) - vector2.getElement(i));
        }
        return result;
    }

    private Vector multiplication(Vector vector, Matrix matrix, int id, Vector result) {
        int from = id * H;
        int to = (id + 1) * H;
        List<Double> values = new ArrayList<>();
        for (int i = from; i < to; i++) {
            for (int j = 0; j < N; j++) {
                values.add(vector.getElement(j) * matrix.getElement(j, i));
            }
            result.setElement(i, sortAndAdd(values));
            values.clear();
        }
        return result;
    }

    private double min(Vector vector, int id) {
        int from = id * H;
        int to = (id + 1) * H;
        double min = Main.min;
        for (int i = from; i < to; i++) {
            min = Math.min(vector.getElement(i), min);
        }
        return min;
    }

    public void calculateA(Vector B, Vector D, Vector E, Matrix MC, Matrix MZ, Matrix MM, int id, Vector A) {
        addition(addition(multiplication(B, MC, id, new Vector(N)), multiplication(D, MZ, id, new Vector(N)), id, A), multiplication(E, MM, id, new Vector(N)), id, A);
    }


    public void calculateC(Vector B, Vector D, Matrix MC, Matrix MM, int id, Vector C) {
        substraction(multiplication(B, MC, id, new Vector(N)), multiplication(D, MM, id, new Vector(N)), id, C);
    }

    public void calculateMA(Matrix MB, Matrix MK, Matrix MC, Matrix MX, Matrix MH, int id, Matrix MA) {
        addition(multiplication(MB, MK, id, new Matrix(N)), multiplication(multiplication(MC, MX, id, new Matrix(N)), MH, id, new Matrix(N)), id, MA);
    }

    public void calculateMF(Vector D, Vector C, Matrix MD, Matrix MT, Matrix MZ, Matrix MG, int a, int id, Matrix MF) {
        addition(multiplication(multiplication(MD, MT, id, new Matrix(N)), min(addition(D, C, id, new Vector(N)), id), id, new Matrix(N)), multiplication(multiplication(MZ, MG, id, new Matrix(N)), a, id, new Matrix(N)), id, MF);
    }

    public void calculateMG(Matrix ME, Matrix MM, int id, Matrix MG) {
        addition(ME, MM, id, MG);
    }

    public void calculateMH(Matrix MT, Matrix MM, int id, Matrix MH) {
        addition(MT, MM, id, MH);
    }

    private double sortAndAdd(List<Double> array) {
        return array.parallelStream().sorted().reduce(0d, Double::sum);
//      return array.stream().sorted().mapToDouble(v -> v).sum();
    }
}
