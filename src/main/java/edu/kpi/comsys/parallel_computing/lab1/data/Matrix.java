package edu.kpi.comsys.parallel_computing.lab1.data;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.range;

public class Matrix {
    private List<Vector> values;

    public Matrix(int N) {
        this.values = new ArrayList<>(N);
        range(0, N).forEach(i -> values.add(new Vector(N)));
    }

    public int getLength() {
        return this.values.size();
    }

    public double getElement(int i, int j) {
        return this.values.get(i).getElement(j);
    }

    public void setElement(int i, int j, double el) {
        Vector vector = this.values.get(i);
        synchronized (this) {
            vector.setElement(j, el);
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        values.forEach(v -> str.append(v).append("\n\r"));
        return str.toString();
    }
}
