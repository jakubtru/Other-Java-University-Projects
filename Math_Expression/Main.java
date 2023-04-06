package pl.agh.lab2;

import java.util.ArrayList;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        //buildAndPrint();
        //buildAndEvaluate();
        //defineCircle();
        //diffPoly();
        //diffCircle();
    }

    static void buildAndEvaluate() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(new Power(x, 3))
                .add(-2, new Power(x, 2))
                .add(-1, x)
                .add(2);
        System.out.println(exp.toString());
        for (double v = -5; v < 5; v += 0.1) {
            x.setValue(v);
            System.out.printf(Locale.US, "f(%f)=%f\n", v, exp.evaluate());
        }
    }

    static void buildAndPrint() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2.1, new Power(x, 3))
                .add(new Power(x, 2))
                .add(-2, x)
                .add(0)
                .add(7);
        System.out.println(exp.toString());
        x.setValue(10);  //2187.0 - zgadza się
        System.out.println(exp.evaluate());
        //powinno być 2.1*x^3 + x^2 + (-2)*x + 7
        // jest       2.1*x^3 + x^2 + (-2)*x + 7
    }

    static void defineCircle() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x, 2))
                .add(new Power(y, 2))
                .add(8, x)
                .add(4, y)
                .add(16);
        //System.out.println(circle.toString());
        double xv = 100 * (Math.random() - .5);
        double yv = 100 * (Math.random() - .5);
        x.setValue(xv);
        y.setValue(yv);
        double fv = circle.evaluate();
        System.out.print(String.format("Punkt (%f,%f) leży %s koła %s", xv, yv, (fv < 0 ? "wewnątrz" : "na zewnątrz"), circle.toString()));
        //program wypisujący 100 punktów leżących wewnątrz okręgu
        int pointCounter = 0;
        ArrayList<String> pointsInside = new ArrayList<>();
        while (pointCounter < 100) {
            x.setValue(100 * (Math.random() - .5));
            y.setValue(100 * (Math.random() - .5));
            double result = circle.evaluate();
            if (result < 0) {
                pointCounter++;
                pointsInside.add("(" + x.value + ", " + y.value + ")");
            }
        }
        System.out.println("\n100 points inside the circle: " + circle.toString());
        for (int i = 0; i < 100; i++) {
            System.out.println(pointsInside.get(i));
        }
    }

    static void diffPoly() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2, new Power(x, 3))
                .add(new Power(x, 2))
                .add(-2, x)
                .add(7);
        System.out.print("exp=");
        System.out.println(exp.toString());

        Node d = exp.diff(x);
        System.out.print("d(exp)/dx=");
        System.out.println(d.toString());
    }

    static void diffCircle() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x, 2))
                .add(new Power(y, 2))
                .add(8, x)
                .add(4, y)
                .add(16);
        System.out.print("f(x,y) = ");
        System.out.println(circle.toString());

        Node dx = circle.diff(x);
        System.out.print("d f(x,y)/dx = ");
        System.out.println(dx.toString());
        System.out.print("d f(x,y)/dy = ");
        Node dy = circle.diff(y);
        System.out.println(dy.toString());
    }
}
