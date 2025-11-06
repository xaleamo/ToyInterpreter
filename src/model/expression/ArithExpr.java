package model.expression;

import myExceptions.DivisionByZero;
import myExceptions.ExpressionException;

import model.programState.SymTable;
import model.value.*;
import model.type.IntType;


public class ArithExpr implements Expression {
    public enum Operator{ //static by default
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE
    }
    Expression op1,op2;
    Operator operator;

    public ArithExpr(Expression op1, Expression op2,Operator operator) {
        this.op1 = op1;
        this.op2 = op2;
        this.operator = operator;
    }

    @Override
    public ArithExpr clone() {return new ArithExpr(op1.clone(), op2.clone(), operator);}

    @Override
    public Value eval(SymTable tbl){
        Value v1,v2;
        v1=op1.eval(tbl);
        if(v1.getType().equals(new IntType())){
            v2=op2.eval(tbl);
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
