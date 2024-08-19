package org.example.shapes;

import org.example.interfaces.Figure;

public abstract class GeometricShape2D implements
        Comparable<GeometricShape2D>, Figure {
    public abstract double getArea();
    public abstract double getPerimeter();

    @Override
    public int compareTo(GeometricShape2D o) {
        return Double.compare(this.getArea(), o.getArea());
    }
}
