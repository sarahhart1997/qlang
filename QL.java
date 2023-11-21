import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.Stack;

public class QL {
    public static void main(String[] args) throws Exception {
        CharStream input = CharStreams.fromFileName("infile");
        QLexer lexer = new QLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        QParser parser = new QParser(tokens);

        ParseTreeWalker walker = new ParseTreeWalker();
        CustomQLListener listener = new CustomQLListener();
        walker.walk(listener, parser.qlist());
    }
}

class CustomQLListener extends QBaseListener {
    private Stack<Double> coordinatesStack = new Stack<>();

    @Override
    public void enterQexpr(QParser.QexprContext ctx) {
        String quadName = ctx.VAR().getText();

        for (int i = 0; i < 8; i++) {
            double coordinate = Double.parseDouble(ctx.num(i).getText());
            coordinatesStack.push(coordinate);
        }

        double[] coordinates = new double[8];
        for (int i = 7; i >= 0; i--) {
            coordinates[i] = coordinatesStack.pop();
        }

        Quad quad = new Quad(coordinates);
        System.out.println("#- create " + quadName + " = " + quad);
        System.out.println("#- plot quad " + quadName);
        System.out.println(quad.toR());
    }

    private double popCoordinate() {
        if (!coordinatesStack.isEmpty()) {
            return coordinatesStack.pop();
        } else {
            throw new IllegalStateException("No coordinates left on stack");
        }
    }
}

class Quad {
    private double[] coordinates;

    public Quad(double[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < 7; i++) {
            sb.append(coordinates[i]);
            sb.append(", ");
        }
        sb.append(coordinates[7]);
        sb.append(")");
        return sb.toString();
    }

    public String toR() {
        return "plot(0:10, 0:10, 'n', xlab='', ylab='');\n" +
                "polygon(c(" + coordinates[0] + ", " + coordinates[2] + ", " + coordinates[4] + ", " + coordinates[6] + "), " +
                "c(" + coordinates[1] + ", " + coordinates[3] + ", " + coordinates[5] + ", " + coordinates[7] + "));";
    }
}
