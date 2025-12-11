package model.statement;

import model.program_state.ADTs.MyIDictionary;
import model.program_state.Heap;
import model.program_state.PrgState;
import model.program_state.SymTable;
import model.type.RefType;
import model.type.Type;
import model.value.*;
import model.expression.Expression;
import my_exceptions.StatementException;
import my_exceptions.TypeException;

public class WriteHeap implements Statement{
    Id var_name;
    Expression expr;
    public WriteHeap(Id var_name, Expression expr){
        this.var_name = var_name;
        this.expr = expr;
    }

    @Override
    public WriteHeap clone(){
        return new WriteHeap(var_name.clone(),expr.clone());
    }

    @Override
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        Type typeV=typeEnv.lookUp(var_name);//even if typeV is null,  it should enter an error branch regardless!=
        if(typeV==null)throw new TypeException("Variable "+var_name+" not found");
        Type typeE= expr.typecheck(typeEnv);
        if(!typeV.equals(new RefType(typeE))) throw new TypeException("WH stmt: type referenced by LHS and type of RHS have different types: "+typeV+" != "+new RefType(typeE));

        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState ps){
        SymTable tbl = ps.getSymTable();
        Heap heap = ps.getHeap();

        Value val=tbl.lookUp(var_name);
        if(val==null)throw new StatementException("Variable "+var_name+" not found.");
        if(!(val instanceof RefValue))throw new StatementException("Variable "+var_name+" is not a ref value.");
        Value heapVal=heap.lookUp(((RefValue) val).getAddress());
        if(heapVal==null)throw new StatementException("Variable "+var_name+" not allocated.");

        Value exprVal=expr.eval(tbl,heap);
        if(!exprVal.getType().equals(((RefType)val.getType()).getInner()))
            throw new StatementException("Expression "+exprVal+" does not evaluate to (same) ref value (as "+((RefType)val.getType()).getInner()+").");
        //add inner check and assume rest are ok
        if(exprVal instanceof RefValue){
            //check if valid address
            Value v=heap.lookUp(((RefValue) exprVal).getAddress());
            if(v==null || v.getType()!=((RefType)exprVal.getType()).getInner())throw new StatementException("Inner var of "+exprVal+" not valid address.");
        }

        heap.update(((RefValue) val).getAddress(),exprVal);
        return null;
    }

    @Override
    public String toString(){
        return "wH("+var_name.toString()+","+expr.toString()+")";
    }
}
