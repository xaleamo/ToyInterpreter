package model.statement;

import model.expression.Expression;
import model.program_state.ADTs.MyIDictionary;
import model.program_state.PrgState;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Id;
import model.value.Value;
import my_exceptions.ExpressionException;
import my_exceptions.TypeException;

public class WhileStatement implements Statement{

    private Expression condition;
    private Statement statement;//should be a compound statement if I want more

    public WhileStatement(Expression condition, Statement statement){
        this.condition=condition;
        this.statement=statement;
    }

    @Override
    public PrgState execute(PrgState ps) {
        Value val=condition.eval(ps.getSymTable(),ps.getHeap());
        if(!val.getType().equals(new BoolType()))
            throw new ExpressionException("Expression "+condition.toString()+" is not a boolean");
        if(((BoolValue) val).getValue()){
            ps.getExecutionStack().push(this);
            ps.getExecutionStack().push(statement);
        }
        //else do nothing

        return null;
    }

    @Override
    public WhileStatement clone() {
        return new WhileStatement(condition.clone(),statement.clone());
    }

    @Override
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        Type typ= condition.typecheck(typeEnv);
        if(!typ.equals(new BoolType())) throw new TypeException("Conditional expression is not a boolean: "+condition.toString());
        statement.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "[while(\n"+ condition.toString()+")   "+statement.toString()+"]";
    }
}
