package model.expression;

import model.programState.SymTable;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;
import myExceptions.ExpressionException;

public class RelationalExpr implements Expression {
    public enum Operator{
        LESS, LESS_OR_EQUAL, GREATER, GREATER_OR_EQUAL,
        EQUAL, NOT_EQUAL
    }
    Expression expr1,expr2;
    Operator op;


    public RelationalExpr(Expression expr1,Operator op, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.op = op;
    }

    @Override
    public RelationalExpr clone() {return new RelationalExpr(expr1.clone(), op, expr2.clone());}

    @Override
    public Value eval(SymTable tbl) {
        Value v1 = expr1.eval(tbl);
        Value v2 = expr2.eval(tbl);
        //if one of them is not IntType
        if(!v1.getType().equals(new IntType())) throw new ExpressionException("First operand is not an integer.");
        if(!v2.getType().equals(new IntType())) throw new ExpressionException("Second operand is not an integer.");

        int n1=((IntValue)v1).getValue();
        int n2=((IntValue)v2).getValue();
        return switch (op) {
            case EQUAL -> new BoolValue(n1 == n2);
            case NOT_EQUAL -> new BoolValue(n1 != n2);
            case LESS -> new BoolValue(n1 < n2);
            case LESS_OR_EQUAL -> new BoolValue(n1 <= n2);
            case GREATER -> new BoolValue(n1 > n2);
            case GREATER_OR_EQUAL -> new BoolValue(n1 >= n2);
        };


    }

    @Override
    public String toString(){
        String sign=switch (op){
            case EQUAL -> "=";
            case LESS -> "<";
            case LESS_OR_EQUAL -> "<=";
            case GREATER -> ">";
            case GREATER_OR_EQUAL -> ">=";
            case NOT_EQUAL -> "!=";
        };
        return expr1.toString() + sign + expr2.toString();
    }
}
