package edu.caelum.estudos.java8napratica.calculator;

import java.util.function.IntSupplier;

public class Fibonacci implements IntSupplier {

    private int anterior = 0;
    private int proximo = 1;

    @Override public int getAsInt() {
        proximo = proximo + anterior;
        anterior = proximo - anterior;
	return anterior;
    }
}
