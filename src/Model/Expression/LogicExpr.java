package Model.Expression;

import MyExceptions.ExpressionException;
import Model.ProgramState.SymTable;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.Value;

public class LogicExpr implements Expression {

    public enum Operator {
        AND,
        OR
    }

    private Expression e1,e2;
    private Operator op;
    public LogicExpr(Expression e1,Operator op,Expression e2) {
        this.e1 = e1;
        this.op = op;
        this.e2 = e2;
    }
    @Override
    public Value eval(SymTable tbl){
        Value v1 = e1.eval(tbl);
        if(v1.getType().equals(new BoolType())){//is instance of BoolValue
            Value v2 = e2.eval(tbl);
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
