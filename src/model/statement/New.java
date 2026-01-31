package model.statement;

import model.expression.Exp;
import model.program_state.*;
import model.program_state.ADTs.MyIDictionary;
import model.type.RefType;
import model.type.Type;
import model.value.Id;
import model.value.RefValue;
import model.value.Value;
import my_exceptions.TypeException;
import my_exceptions.UndeclaredVariable;

public class New implements IStmt, Cloneable{
    private Id var_name;
    private Exp expr;

    public New(Id var_name, Exp expr) {
        this.var_name=var_name;
        this.expr=expr;
    }

    @Override
    public IStmt clone(){
        return new New(var_name,expr);
    }

    @Override
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        Type typeVar=typeEnv.lookUp(var_name);
        Type typeExp=expr.typecheck(typeEnv);
        if(typeVar==null) throw new TypeException("Variable "+var_name+" not found");
        if(typeVar.equals(new RefType(typeExp))) return typeEnv;
        else throw new TypeException("NEW stmt RHS and LHS have different types: "+typeVar+" != "+new RefType(typeExp));
    }

    @Override
    public PrgState execute(PrgState ps) {
        SymTable symTable=ps.getSymTable();
        //prelim checks
        if(!(symTable.isDefined(var_name) && symTable.lookUp(var_name).getType() instanceof RefType))//can't use equals bcs it goes in depth
            throw new UndeclaredVariable("Variable "+var_name+" is not defined or of type RefType.");
        Value val=expr.eval(symTable, ps.getHeap());
        if(!((RefType) symTable.lookUp(var_name).getType()).getInner().equals(val.getType()))//compare in depth (locationType)
            throw new UndeclaredVariable("Variable "+var_name+" is not of same type RefValue as value from expression "+expr.toString());

        //ok, add onto heap
        Heap heap=ps.getHeap();
        Integer addr=heap.add(val);
        symTable.add(var_name,new RefValue(addr,val.getType()));//update


        return null;
    }

    @Override
    public String toString() {
        return "new("+var_name.toString()+","+expr.toString()+")";
    }
}
