package pl.agh.lab2;

import java.util.ArrayList;
import java.util.List;

public class Sum extends Node {

    List<Node> args = new ArrayList<>();

    Sum() {
    }

    Sum(Node n1, Node n2) {
        args.add(n1);
        args.add(n2);
    }

    Sum add(Node n) {
        args.add(n);
        return this;
    }

    Sum add(double c) {
        args.add(new Constant(c));
        return this;
    }

    Sum add(double c, Node n) {
        Node mul = new Prod(c, n);
        args.add(mul);
        return this;
    }

    @Override
    boolean isZero() {
        return false;
    }

    @Override
    Node diff(Variable var) {
        //dodaję elementy do listy i sprawdzam czy czynnik jest równy zero
        ArrayList<Node> arguments = new ArrayList<>();
        for (Node n : args) {
            arguments.add(n.diff(var));
        }
        arguments.removeIf(arg -> arg.toString().equals("0"));
        arguments.removeIf(arg -> arg.toString().contains("*0"));

        Sum r = new Sum();
        for (Node n : arguments) {
            r.add(n);
        }
        return r;
    }

    @Override
    double evaluate() {
        args.removeIf(Node::isZero);
        double result = 0;
        for (var arg : args) {
            result += arg.evaluate();
        }
        return sign * result;
    }

    int getArgumentsCount() {
        return args.size();
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        if (sign < 0) b.append("-(");
        for (var arg : args) {
            if (arg.toString().equals("0") || arg.toString().equals("")) {
                continue;
            }
            b.append(arg.toString());
            b.append(" + ");
        }
        try {
            b.delete(b.length() - 3, b.length());
        } catch (Exception e) {
        }
        if (sign < 0) b.append(")");
        return b.toString();
    }


}
