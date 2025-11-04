package Model.Statement;

import Model.ProgramState.ProgramState;
import Model.Expression.Expression;

public class PrintStatement implements Statement {
    Expression expr;

    public PrintStatement(Expression expr) {
        this.expr = expr;
    }

    @Override
    public String toString(){
        return "print(" + expr.toString() + ");";
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        ps.getOutput().add(expr.eval(ps.getSymTable()));
        return ps;
    }
}
