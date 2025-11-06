package model.statement;

import myExceptions.StatementException;
import model.expression.Expression;
import model.programState.ProgramState;
import model.value.*;
import model.type.*;

public class IfStatement implements Statement {
    Expression condExpr;
    Statement thenStatement, elseStatement;

    public IfStatement(Expression condExpr, Statement thenStatement, Statement elseStatement) {
        this.condExpr = condExpr;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public IfStatement clone() {return new IfStatement(condExpr.clone(), thenStatement.clone(), this.elseStatement.clone());}

    @Override
    public ProgramState execute(ProgramState ps) {
        Value exprVal= condExpr.eval(ps.getSymTable());
        if(exprVal.getType().equals(new BoolType()))//vs is  instance of
        {
            BoolValue exprBVal=(BoolValue)exprVal;
            if(exprBVal.getValue()){
                ps.getExecutionStack().push(thenStatement);
            }
            else ps.getExecutionStack().push(elseStatement);
        }
        else{
            throw new StatementException("Conditional expr is not a boolean value");
        }

        return ps;
    }

    @Override
    public String toString() {
        return "IF("+condExpr.toString()+") THEN( "+ thenStatement.toString()+" ) ELSE ("+elseStatement.toString()+");";
    }
}
