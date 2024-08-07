package main.java.org.example.interfaces;


@FunctionalInterface
public interface BigNumberCalculatorConsumer<T, V> {
    void accept(T t, V v);
}

