package model.statement;

import model.expression.Expression;
import model.program_state.*;
import model.type.RefType;
import model.value.Id;
import model.value.RefValue;
import model.value.Value;
import my_exceptions.UndeclaredVariable;

public class HeapAlloc implements Statement, Cloneable{
    private Id var_name;
    private Expression expr;

    public HeapAlloc(Id var_name, Expression expr) {
        this.var_name=var_name;
        this.expr=expr;
    }

    @Override
    public Statement clone(){
        return new HeapAlloc(var_name,expr);
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        SymTable symTable=ps.getSymTable();
        //prelim checks
        if(!(symTable.isDefined(var_name) && symTable.lookUp(var_name).getType() instanceof RefType))//can't use equals bcs it goes in depth
            throw new UndeclaredVariable("Variable "+var_name+" is not defined or of type RefType.");
        Value val=expr.eval(symTable, ps.getHeap());
        if(!((RefType) symTable.lookUp(var_name).getType()).getInner().equals(val.getType()))//compare in depth (locationType)
            throw new UndeclaredVariable("Variable "+var_name+" is not of same type RefValue as value from expression .");

        //ok, add onto heap
        Heap heap=ps.getHeap();
        Integer addr=heap.add(val);
        symTable.add(var_name,new RefValue(addr,val.getType()));//update


        return ps;
    }

    @Override
    public String toString() {
        return "new("+var_name.toString()+","+expr.toString()+")";
    }
}
