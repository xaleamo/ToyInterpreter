package Model.Expression;

import Model.ProgramState.ProgramState;
import Model.ProgramState.SymTable;
import Model.Value.Value;

public class ValueExpr implements Expression {
    Value v;
    public ValueExpr(Value v) {
        this.v = v;
    }
    @Override
    public Value eval(SymTable tbl){
        return v;
    }

    @Override
    public String toString(){
        return v.toString();
    }
}
