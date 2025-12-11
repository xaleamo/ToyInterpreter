package model.statement;

import model.program_state.ADTs.MyIDictionary;
import model.type.Type;
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
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        Type typevar = typeEnv.lookUp(id);
        Type typexpr= expr.typecheck(typeEnv);
        if(typevar==null) throw new TypeException("Variable not declared: "+id);
        if(typexpr.equals(typevar)){//order matters in case typevar is null
            return typeEnv;
        }
        else throw new TypeException("RHS and LHS of AssignStmt have different types: " +typevar+" != "+typexpr);
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
