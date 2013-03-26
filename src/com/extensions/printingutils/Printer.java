package com.extensions.printingutils;

public interface Printer<T> {
    String print(T obj);
}