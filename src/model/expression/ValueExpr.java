package model.expression;

import model.program_state.ADTs.MyIDictionary;
import model.program_state.Heap;
import model.program_state.SymTable;
import model.type.Type;
import model.value.Id;
import model.value.Value;
import my_exceptions.MyException;

public class ValueExpr implements Expression {
    Value v;
    public ValueExpr(Value v) {
        this.v = v;
    }

    @Override
    public ValueExpr clone() {return new ValueExpr(v.clone());}

    @Override
    public Type typecheck(MyIDictionary<Id, Type> typeEnv) throws MyException {
        return v.getType();
    }

    @Override
    public Value eval(SymTable tbl, Heap heap){
        return v;
    }

    @Override
    public String toString(){
        return v.toString();
    }
}
