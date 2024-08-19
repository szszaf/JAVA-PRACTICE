package org.example.shapes;

import org.example.exceptions.TriangleDoesNotExistException;

public class Triangle extends GeometricShape2D {
    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(double sideA, double sideB, double sideC) throws TriangleDoesNotExistException {
        if (sideA + sideB < sideC ||
                sideB + sideC < sideA || sideC + sideA < sideB) throw new TriangleDoesNotExistException();

        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    @Override
    public double getArea() {
        double halfOfPerimeter = 0.5 * getPerimeter();
        return Math.sqrt(
                        halfOfPerimeter *
                       (halfOfPerimeter - sideA) *
                       (halfOfPerimeter - sideB) *
                       (halfOfPerimeter - sideC)
        );
    }

    @Override
    public double getPerimeter() {
        return this.sideA + this.sideB + this.sideC;
    }

    @Override
    public void scale(double scaleRatio) {
        this.sideA *= scaleRatio;
        this.sideB *= scaleRatio;
        this.sideC *= scaleRatio;
    }


    @Override
    public void draw() {

    }

    @Override
    public String toString() {
        return "Triangle [sideA=" + sideA + ", sideB=" + sideB + ", sideC=" + sideC + "]";
    }
}
