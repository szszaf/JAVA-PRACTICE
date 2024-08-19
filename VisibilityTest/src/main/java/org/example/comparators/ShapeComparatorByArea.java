package org.example.comparators;

import org.example.shapes.GeometricShape2D;

import java.util.Comparator;

public class ShapeComparatorByArea implements Comparator<GeometricShape2D> {
    @Override
    public int compare(GeometricShape2D o1, GeometricShape2D o2) {
        return Double.compare(o1.getArea(), o2.getArea());
    }
}
