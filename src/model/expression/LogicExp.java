package model.expression;

import model.program_state.ADTs.MyIDictionary;
import model.program_state.Heap;
import model.type.Type;
import model.value.Id;
import my_exceptions.ExpressionException;
import model.program_state.SymTable;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;
import my_exceptions.TypeException;

public class LogicExp implements Exp {

    public enum Operator {
        AND,
        OR
    }

    private Exp e1,e2;
    private Operator op;
    public LogicExp(Exp e1, Operator op, Exp e2) {
        this.e1 = e1;
        this.op = op;
        this.e2 = e2;
    }

    @Override
    public Type typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException{
        Type typ1=e1.typecheck(typeEnv);
        Type typ2=e2.typecheck(typeEnv);
        if(typ1.equals(new BoolType())){
            if(typ2.equals(new BoolType())){
                return new BoolType();
            }
            else throw new TypeException("Second operand is not of BoolType: "+e2);
        }
        throw new TypeException("First operand is not of BoolType: "+e1);
    }

    @Override
    public LogicExp clone() {return new LogicExp(e1.clone(),op,e2.clone());}

    @Override
    public Value eval(SymTable tbl, Heap heap){
        Value v1 = e1.eval(tbl,heap );
        if(v1.getType().equals(new BoolType())){//is instance of BoolValue
            Value v2 = e2.eval(tbl,heap );
            if(v2.getType().equals(new BoolType())){
                boolean bv1 = ((BoolValue)v1).getValue();
                boolean bv2 = ((BoolValue)v2).getValue();
                switch(op){
                    case AND:
                        return new BoolValue(bv1 && bv2);
                    case OR:
                        return new BoolValue(bv1 || bv2);
                }
            }
            else
                throw new ExpressionException("Operand2 is not a boolean");

        }
        throw new ExpressionException("Operand1 is not a boolean");
    }

    @Override
    public String toString(){
        return e1.toString() + " " + op + " " + e2.toString();
    }
}
