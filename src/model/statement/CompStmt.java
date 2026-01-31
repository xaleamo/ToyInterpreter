package model.statement;

import model.program_state.ADTs.MyIDictionary;
import model.program_state.ExecutionStack;
import model.program_state.PrgState;
import model.type.Type;
import model.value.Id;
import my_exceptions.TypeException;

public class CompStmt implements IStmt {
    IStmt stmt1,stmt2;
    public CompStmt(IStmt stmt1, IStmt stmt2) {
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    @Override
    public CompStmt clone() {return new CompStmt(this.stmt1.clone(), this.stmt2.clone());}

    @Override
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        return stmt2.typecheck(stmt1.typecheck(typeEnv));
    }

    @Override
    public PrgState execute(PrgState ps) {
        ExecutionStack execStack=ps.getExecutionStack();

        execStack.push(stmt2);
        execStack.push(stmt1);

        return null;
    }

    @Override
    public String toString(){
        return stmt1.toString()+"\n"+stmt2.toString()+';';//DELETE ';' for 'normal' printing.
    }
}
