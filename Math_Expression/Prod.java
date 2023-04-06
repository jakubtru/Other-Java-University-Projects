package pl.agh.lab2;

import java.util.ArrayList;
import java.util.List;

public class Prod extends Node {
    List<Node> args = new ArrayList<>();

    Prod() {
    }

    Prod(Node n1) {
        args.add(n1);
    }

    Prod(double c) {
        this(new Constant(c));
    }

    Prod(Node n1, Node n2) {
        args.add(n1);
        args.add(n2);
    }

    Prod(double c, Node n) {
        this(new Constant(c), n);
    }

    Prod mul(Node n) {
        args.add(n);
        return this;
    }

    Prod mul(double c) {
        return this.mul(new Constant(c));
    }


    @Override
    boolean isZero() {
        return false;
    }

    @Override
    Node diff(Variable var) {
        boolean flag = true;
        Sum r = new Sum();
        for (int i = 0; i < args.size(); i++) {
            Prod m = new Prod();
            for (int j = 0; j < args.size(); j++) {
                Node f = args.get(j);
                if (j == i) m.mul(f.diff(var));
                else if (f.isZero()) {//optymalizacja: nie mnożę przez 0
                } else m.mul(f);
            }
            r.add(m);
        }
        return r;
    }

    @Override
    double evaluate() {
        double result = 1;
        for (var arg : args) {
            result *= arg.evaluate();
        }
        return sign * result;
    }

    int getArgumentsCount() {
        return args.size();
    }

    public void simplify() {
        if (args.get(0) instanceof Constant && args.get(1) instanceof Constant) {
            args.set(0, new Constant(((Constant) args.get(0)).value * ((Constant) args.get(1)).value));
            args.remove(1);
        } else if (args.size() > 2 && args.get(0) instanceof Constant && args.get(2) instanceof Constant) {
            args.set(0, new Constant(((Constant) args.get(0)).value * ((Constant) args.get(2)).value));
            args.remove(2);
        }
        if (args.get(0).toString().equals("0")) {
            args.clear();
        }

    }


    public String toString() {
        StringBuilder b = new StringBuilder();
        if (sign < 0) b.append("-");
        if (args.size() > 1) {
            simplify();
        }
        for (var arg : args) {
            try {
                //try catch jest tu konieczny, bo jeśli instancja Variable nie ma
                if (arg.evaluate() < 0) {           //ustalonego value pojawia się NPE
                    b.append("(");
                }

                b.append(arg.toString());
                if (arg.evaluate() < 0) {
                    b.append(")");
                }
            } catch (NullPointerException e) {
                b.append(arg.toString());
            }
            b.append("*");

        }
        try {
            b.delete(b.length() - 1, b.length());
        } catch (Exception e) {
        }
        return b.toString();
    }
}