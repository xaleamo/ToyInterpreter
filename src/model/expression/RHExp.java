package model.expression;

import model.program_state.*;
import model.program_state.ADTs.MyIDictionary;
import model.type.RefType;
import model.type.Type;
import model.value.*;
import my_exceptions.ExpressionException;
import my_exceptions.MyException;
import my_exceptions.TypeException;

public class RHExp implements Exp {
    private Exp expr;
    public RHExp(Exp expr){this.expr=expr;}

    @Override
    public RHExp clone(){return new RHExp(expr.clone());}

    @Override
    public Type typecheck(MyIDictionary<Id, Type> typeEnv) throws MyException {
        Type typ=expr.typecheck(typeEnv);
        if(typ instanceof RefType){
            return ((RefType)typ).getInner();
        }
        else throw new TypeException("The RH argument is not a RefType");
    }


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
