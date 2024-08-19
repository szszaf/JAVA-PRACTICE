package org.example;

import org.example.comparators.ShapeComparatorByArea;
import org.example.comparators.ShapeComparatorByPerimeter;
import org.example.exceptions.NegativeDistanceException;
import org.example.exceptions.TriangleDoesNotExistException;
import org.example.shapes.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NegativeDistanceException, TriangleDoesNotExistException {
//        GeometricShape2D shape2D = new Rectangle(3, 4);
//        System.out.printf("%s, area: %.2f\n", shape2D, shape2D.getArea());
//        shape2D.draw();

        List<GeometricShape2D> figuresList = new ArrayList<>();
        figuresList.add(new Rectangle(3,5));
        figuresList.add(new Circle(5));
        figuresList.add(new Square(4));
        figuresList.add(new Triangle(4, 5, 6));
        figuresList.add(new Triangle(3, 5, 7));

        System.out.println("Shapes compared by area:");
        figuresList.stream().sorted(new ShapeComparatorByArea()).forEach(System.out::println);
        System.out.println("Shapes compared by perimeter:");
        figuresList.stream().sorted(new ShapeComparatorByPerimeter()).forEach(System.out::println);
    }
}