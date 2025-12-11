package model.statement;

import my_exceptions.*;
import model.expression.Expression;
import model.program_state.PrgState;
import model.program_state.SymTable;
import model.value.Id;
import model.value.Value;

public class AssignStatement implements Statement {
    Id id;
    Expression expr;
    public AssignStatement(Id id, Expression expr) {
        this.id = id;
        this.expr = expr;
    }
    @Override
    public AssignStatement clone(){
        return new AssignStatement(id.clone(), expr.clone());
    }

    @Override
    public PrgState execute(PrgState ps) {
        SymTable tbl = ps.getSymTable();
        Value val=expr.eval(tbl, ps.getHeap());


        if(tbl.isDefined(id)) {
            Value lookup = tbl.lookUp(id);//won't return null now
            //update value
            if (val.getType().equals(lookup.getType())) {
                tbl.add(id, val);
            } else throw new StatementException("Lookup mismatch type");

        }
        else throw new UndeclaredVariable("Error: variable "+id.toString()+" is not declared");
        return null;
    }

    @Override
    public String toString() {
        return id.toString() + "=" + expr.toString()+';';
    }
}
