package model.statement;

import model.program_state.PrgState;
import model.expression.Expression;

public class PrintStatement implements Statement {
    Expression expr;

    public PrintStatement(Expression expr) {
        this.expr = expr;
    }
    @Override
    public PrintStatement clone() {return new PrintStatement(expr.clone());}
    @Override
    public String toString(){
        return "print(" + expr.toString() + ");";
    }

    @Override
    public PrgState execute(PrgState ps) {
        ps.getOutput().add(expr.eval(ps.getSymTable(), ps.getHeap()));
        return null;
    }
}
