package pl.agh.lab2;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Constant extends Node {
    double value;

    Constant(double value) {
        this.sign = value < 0 ? -1 : 1;
        this.value = value < 0 ? -value : value;
    }

    @Override
    boolean isZero() {
        return this.value == 0;
    }

    @Override
    Node diff(Variable var) {
        return new Constant(0);
    }

    @Override
    double evaluate() {
        return sign * value;
    }

    @Override
    public String toString() {
        String sgn = sign < 0 ? "-" : "";
        DecimalFormat format = new DecimalFormat("0.#####", new DecimalFormatSymbols(Locale.US));
        return sgn + format.format(value);
    }
}

