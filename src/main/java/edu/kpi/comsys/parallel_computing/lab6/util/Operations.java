package edu.kpi.comsys.parallel_computing.lab6.util;

import edu.kpi.comsys.parallel_computing.lab6.Main;
import edu.kpi.comsys.parallel_computing.lab6.data.Matrix;
import edu.kpi.comsys.parallel_computing.lab6.data.Vector;

import java.util.ArrayList;
import java.util.List;


public class Operations {
    private final int N;

    public Operations(int n) {
        N = n;
    }

    private Matrix multiplication(Matrix matrix1, Matrix matrix2, int from, int to, Matrix result) {
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

    private Matrix multiplication(Matrix matrix, double scalar, int from, int to, Matrix result) {
        for (int i = from; i < to; i++) {
            for (int j = 0; j < N; j++) {
                result.setElement(i, j, scalar * matrix.getElement(i, j));
            }
        }
        return result;
    }

    private Matrix addition(Matrix matrix1, Matrix matrix2, int from, int to, Matrix result) {
        for (int i = from; i < to; i++) {
            for (int j = 0; j < N; j++) {
                result.setElement(i, j, matrix1.getElement(i, j) + matrix2.getElement(i, j));
            }
        }
        return result;
    }

    private Vector addition(Vector vector1, Vector vector2, int from, int to, Vector result) {
        for (int i = from; i < to; i++) {
            result.setElement(i, vector1.getElement(i) + vector2.getElement(i));
        }
        return result;
    }

    private Vector substraction(Vector vector1, Vector vector2, int from, int to, Vector result) {
        for (int i = from; i < to; i++) {
            result.setElement(i, vector1.getElement(i) - vector2.getElement(i));
        }
        return result;
    }

    private Vector multiplication(Vector vector, Matrix matrix, int from, int to, Vector result) {
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

    private double min(Vector vector, int from, int to) {
        double min = Main.min;
        for (int i = from; i < to; i++) {
            min = Math.min(vector.getElement(i), min);
        }
        return min;
    }

    public void calculateA(Vector B, Vector D, Vector E, Matrix MC, Matrix MZ, Matrix MM, int from, int to, Vector A) {
        addition(addition(multiplication(B, MC, from, to, new Vector(N)), multiplication(D, MZ, from, to, new Vector(N)), from, to, A), multiplication(E, MM, from, to, new Vector(N)), from, to, A);
    }


    public void calculateC(Vector B, Vector D, Matrix MC, Matrix MM, int from, int to, Vector C) {
        substraction(multiplication(B, MC, from, to, new Vector(N)), multiplication(D, MM, from, to, new Vector(N)), from, to, C);
    }

    public void calculateMA(Matrix MB, Matrix MK, Matrix MC, Matrix MX, Matrix MH, int from, int to, Matrix MA) {
        addition(multiplication(MB, MK, from, to, new Matrix(N)), multiplication(multiplication(MC, MX, from, to, new Matrix(N)), MH, from, to, new Matrix(N)), from, to, MA);
    }

    public void calculateMF(Vector D, Vector C, Matrix MD, Matrix MT, Matrix MZ, Matrix MG, int a, int from, int to, Matrix MF) {
        addition(multiplication(multiplication(MD, MT, from, to, new Matrix(N)), min(addition(D, C, from, to, new Vector(N)), from, to), from, to, new Matrix(N)), multiplication(multiplication(MZ, MG, from, to, new Matrix(N)), a, from, to, new Matrix(N)), from, to, MF);
    }

    public void calculateMG(Matrix ME, Matrix MM, int from, int to, Matrix MG) {
        addition(ME, MM, from, to, MG);
    }

    public void calculateMH(Matrix MT, Matrix MM, int from, int to, Matrix MH) {
        addition(MT, MM, from, to, MH);
    }

    private double sortAndAdd(List<Double> array) {
        return array.parallelStream().sorted().reduce(0d, Double::sum);
//      return array.stream().sorted().mapToDouble(v -> v).sum();
    }
}
