package model.expression;

import model.program_state.*;
import model.value.*;
import my_exceptions.ExpressionException;

public class RHExp implements Expression {
    private Expression expr;
    public RHExp(Expression expr){this.expr=expr;}

    @Override
    public RHExp clone(){return new RHExp(expr.clone());}

    @Override
    public Value eval(SymTable tbl, Heap heap) {
        Value val=expr.eval(tbl,heap);
        if(!(val instanceof RefValue)) throw new ExpressionException("Expression does not evaluate to a ref value:"+expr.toString());

        int addr=((RefValue) val).getAddress();
        Value heapVal=heap.lookUp(addr);
        if (heapVal==null) throw new ExpressionException("Value not allocated on heap.");

        return heapVal;
    }

    @Override
    public String toString(){
        return "rH("+expr.toString()+")";
    }

}
