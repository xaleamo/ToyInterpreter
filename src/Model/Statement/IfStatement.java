package Model.Statement;

import MyExceptions.StatementException;
import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Value.*;
import Model.Type.*;

public class IfStatement implements Statement {
    Expression condExpr;
    Statement thenStatement, elseStatement;

    public IfStatement(Expression condExpr, Statement thenStatement, Statement elseStatement) {
        this.condExpr = condExpr;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

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
