package model.statement;

import model.program_state.Heap;
import model.program_state.ProgramState;
import model.program_state.SymTable;
import model.type.IntType;
import model.type.RefType;
import model.value.*;
import model.expression.Expression;
import my_exceptions.StatementException;

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
    public ProgramState execute(ProgramState ps){
        SymTable tbl = ps.getSymTable();
        Heap heap = ps.getHeap();

        Value val=tbl.lookUp(var_name);
        if(val==null)throw new StatementException("Variable "+var_name+" not found.");
        if(!(val instanceof RefValue))throw new StatementException("Variable "+var_name+" is not a ref value.");
        Value heapVal=heap.lookUp(((RefValue) val).getAddress());
        if(heapVal==null)throw new StatementException("Variable "+var_name+" not allocated.");

        Value exprVal=expr.eval(tbl,heap);
        if(!exprVal.getType().equals(((RefType)val.getType()).getInner()))
            throw new StatementException("Expression "+exprVal+" does not evaluate to (same) ref value (as "+val+").");

        heap.update(((RefValue) val).getAddress(),exprVal);
        return ps;
    }

    @Override
    public String toString(){
        return "wH("+var_name.toString()+","+expr.toString()+")";
    }
}
