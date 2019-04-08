package edu.kpi.comsys.parallel_computing.lab4.util;

import edu.kpi.comsys.parallel_computing.lab4.data.Matrix;
import edu.kpi.comsys.parallel_computing.lab4.data.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class FileUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);
    private static final String VECTOR_FILE_NAME = "inputvector.txt";
    private static final String MATRIX_FILE_NAME = "inputmatrix.txt";


    private Random r = new Random();

    private Vector generateRandomVector(int N) {
        Vector vector = new Vector(N);
        for (int i = 0; i < N; i++) {
            vector.setElement(i, r.nextDouble() * 10);
        }
        return vector;
    }

    private Matrix generateRandomMatrix(int N) {
        Matrix matrix = new Matrix(N);
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                matrix.setElement(i, j, r.nextDouble() * 10);
            }
        }
        return matrix;
    }

    public void printToFile(String filename, Vector v) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            for (int i = 0; i < v.getLength(); i++) {
                fileWriter.write(v.getElement(i) + " ");
            }
        } catch (IOException e) {
            LOG.info("Error during printing vector to file", e);
        }
    }

    public void printToFile(String filename, Matrix m) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            for (int i = 0; i < m.getLength(); i++, fileWriter.write("\r\n")) {
                for (int j = 0; j < m.getLength(); j++) {
                    fileWriter.write(m.getElement(i, j) + " ");
                }
            }
        } catch (IOException e) {
            LOG.info("Error during printing matrix to file", e);
        }
    }

    public Vector readVectorFromFile(int N) throws FileNotFoundException {
        Vector vector = new Vector(N);
        try (Scanner input = new Scanner(new BufferedReader(new FileReader(VECTOR_FILE_NAME)))) {
            while (input.hasNextLine()) {
                String[] line = input.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    vector.setElement(j, Double.parseDouble(line[j]));
                }
            }
        }
        return vector;
    }

    public Matrix readMatrixFromFile(int N) throws FileNotFoundException {
        Matrix matrix = new Matrix(N);
        try (Scanner input = new Scanner(new BufferedReader(new FileReader(MATRIX_FILE_NAME)))) {
            while (input.hasNextLine()) {
                for (int i = 0; i < N; i++) {
                    String[] line = input.nextLine().trim().split(" ");
                    for (int j = 0; j < line.length; j++) {
                        matrix.setElement(i, j, Double.parseDouble(line[j]));
                    }
                }
            }
        }
        return matrix;
    }


    public void generateFile(int N) {
        printToFile(MATRIX_FILE_NAME, generateRandomMatrix(N));
        printToFile(VECTOR_FILE_NAME, generateRandomVector(N));
    }
}