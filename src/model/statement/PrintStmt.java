package model.statement;

import model.program_state.ADTs.MyIDictionary;
import model.program_state.PrgState;
import model.expression.Exp;
import model.type.Type;
import model.value.Id;
import my_exceptions.TypeException;

public class PrintStmt implements IStmt {
    Exp expr;

    public PrintStmt(Exp expr) {
        this.expr = expr;
    }
    @Override
    public PrintStmt clone() {return new PrintStmt(expr.clone());}

    @Override
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        expr.typecheck(typeEnv);
        return typeEnv;
    }

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
