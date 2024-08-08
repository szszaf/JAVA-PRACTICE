package main.java.org.example.bignumbercalculator.interfaces;


@FunctionalInterface
public interface BigNumberCalculatorConsumer<T, V> {
    void accept(T t, V v);
}

