package model.expression;

import model.programState.SymTable;
import model.value.Value;

public class ValueExpr implements Expression {
    Value v;
    public ValueExpr(Value v) {
        this.v = v;
    }

    @Override
    public ValueExpr clone() {return new ValueExpr(v.clone());}
    @Override
    public Value eval(SymTable tbl){
        return v;
    }

    @Override
    public String toString(){
        return v.toString();
    }
}
