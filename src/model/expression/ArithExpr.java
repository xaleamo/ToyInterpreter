package model.expression;

import model.program_state.ADTs.MyIDictionary;
import model.program_state.Heap;
import model.type.Type;
import my_exceptions.DivisionByZero;
import my_exceptions.ExpressionException;

import model.program_state.SymTable;
import model.value.*;
import model.type.IntType;
import my_exceptions.TypeException;


public class ArithExpr implements Expression {
    public enum Operator{ //static by default
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE
    }
    Expression op1,op2;
    Operator operator;

    public ArithExpr(Expression op1, Operator operator, Expression op2) {
        this.op1 = op1;
        this.op2 = op2;
        this.operator = operator;
    }

    @Override
    public Type typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        Type typ1 = op1.typecheck(typeEnv);
        Type typ2 = op2.typecheck(typeEnv);
        if(typ1.equals(new IntType())){
            if(typ2.equals(new IntType())){
                return new IntType();
            }
            else throw new TypeException("Second operand is not an integer: "+op2);
        }
        else throw new TypeException("First operand is not an integer: "+op1);
    }

    @Override
    public ArithExpr clone() {return new ArithExpr(op1.clone(), operator, op2.clone());}

    @Override
    public Value eval(SymTable tbl, Heap heap){
        //not sure if I can remove the checks here? What about dynamical type-checker, runtime exceptions and refVal??
        Value v1,v2;
        v1=op1.eval(tbl, heap);
        if(v1.getType().equals(new IntType())){
            v2=op2.eval(tbl, heap);
            if(v2.getType().equals(new IntType())){
                int n1,n2;
                n1=((IntValue)v1).getValue();
                n2=((IntValue)v2).getValue();
                switch(operator){
                    case ADD:
                        return new IntValue(n1+n2);
                    case SUBTRACT:
                        return new IntValue(n1-n2);
                    case MULTIPLY:
                        return new IntValue(n1*n2);
                    case DIVIDE:
                        if(n2==0)
                            throw new DivisionByZero("Division by zero");
                        else
                            return new IntValue(n1/n2);
                }

            }
            else throw new ExpressionException("Second operand is not an integer.");
        }
        //else
        throw new ExpressionException("First operand is not an integer.");

    }

    @Override
    public String toString(){
        char op = switch (operator) {
            case ADD -> '+';
            case SUBTRACT -> '-';
            case MULTIPLY -> '*';
            case DIVIDE -> '/';
        };
        return  op1.toString() + op+ op2.toString() ;
    }
}
