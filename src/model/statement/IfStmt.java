package model.statement;

import model.program_state.ADTs.MyIDictionary;
import my_exceptions.StatementException;
import model.expression.Exp;
import model.program_state.PrgState;
import model.value.*;
import model.type.*;
import my_exceptions.TypeException;

public class IfStmt implements IStmt {
    Exp condExpr;
    IStmt thenStatement, elseStatement;

    public IfStmt(Exp condExpr, IStmt thenStatement, IStmt elseStatement) {
        this.condExpr = condExpr;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public IfStmt clone() {return new IfStmt(condExpr.clone(), thenStatement.clone(), this.elseStatement.clone());}

    @Override
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        Type typeExp=condExpr.typecheck(typeEnv);
        if(!typeExp.equals(new BoolType())) throw new TypeException("Conditional expr is not of BoolType.");
        thenStatement.typecheck(typeEnv.clone());
        elseStatement.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState ps) {
        Value exprVal= condExpr.eval(ps.getSymTable(), ps.getHeap());
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

        return null;
    }

    @Override
    public String toString() {
        return "IF("+condExpr.toString()+") THEN( "+ thenStatement.toString()+" ) ELSE ("+elseStatement.toString()+");";
    }
}
