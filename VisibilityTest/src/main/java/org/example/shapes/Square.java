package org.example.shapes;

import org.example.exceptions.NegativeDistanceException;

public class Square extends Rectangle {

    public Square(double sideLength) throws NegativeDistanceException {
        super(sideLength, sideLength);
    }

}
