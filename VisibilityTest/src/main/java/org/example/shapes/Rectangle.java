package org.example.shapes;

import org.example.exceptions.NegativeDistanceException;

public class Rectangle extends GeometricShape2D {
    private double width;
    private double height;

    public Rectangle(double width, double height) throws NegativeDistanceException {
        if (width <= 0 || height <= 0) throw new NegativeDistanceException();
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return this.width * this.height;
    }

    @Override
    public double getPerimeter() {
        return 2 * this.width + 2 * this.height;
    }

    @Override
    public void scale(double scaleRatio) {
        this.width *= scaleRatio;
        this.height *= scaleRatio;
    }

    @Override
    public void draw() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Rectangle [width=" + width + ", height=" + height + "]";
    }
}
