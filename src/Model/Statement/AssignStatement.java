package Model.Statement;

import MyExceptions.*;
import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.ProgramState.SymTable;
import Model.Value.Id;
import Model.Value.Value;

public class AssignStatement implements Statement {
    Id id;
    Expression expr;
    public AssignStatement(Id id, Expression expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        SymTable tbl = ps.getSymTable();
        Value val=expr.eval(tbl);


        if(tbl.isDefined(id)) {
            Value lookup = tbl.LookUp(id);//won't return null now
            //update value
            if (val.getType().equals(lookup.getType())) {
                tbl.Add(id, val);
            } else throw new StatementException("Lookup mismatch type");

        }
        else throw new UndeclaredVariable("Error: variable"+id.toString()+"is not declared");
        return ps;
    }

    @Override
    public String toString() {
        return id.toString() + "=" + expr.toString()+';';
    }
}
