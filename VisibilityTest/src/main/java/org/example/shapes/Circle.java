package org.example.shapes;

public class Circle extends GeometricShape2D {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void scale(double scaleRatio) {
        this.radius *= scaleRatio;
    }

    @Override
    public void draw() {

    }

    @Override
    public String toString() {
        return "Circle [radius=" + radius + "]";
    }
}
