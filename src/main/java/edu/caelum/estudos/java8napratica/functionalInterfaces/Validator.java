package edu.caelum.estudos.java8napratica.functionalInterfaces;

@FunctionalInterface
public interface Validator <T> {

    boolean valida(T t);
}
