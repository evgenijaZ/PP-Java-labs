package edu.kpi.comsys.parallel_computing.lab5.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Vector {
    private List<Double> values;

    public Vector(int N) {
        this.values = new ArrayList<>(Collections.nCopies(N, 0d));
    }

    public int getLength() {
        return this.values.size();
    }

    public double getElement(int i) {
        return this.values.get(i);
    }

    public void setElement(int i, double el) {
        synchronized (this) {
            this.values.set(i, el);
        }
    }

    @Override
    public String toString() {
        return values.stream().map(v -> " " + v).collect(Collectors.joining());
    }
}
