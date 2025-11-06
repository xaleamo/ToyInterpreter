package model.statement;

import model.programState.ExecutionStack;
import model.programState.ProgramState;

public class CompStatement implements Statement {
    Statement stmt1,stmt2;
    public CompStatement(Statement stmt1, Statement stmt2) {
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }
    @Override
    public ProgramState execute(ProgramState ps) {
        ExecutionStack execStack=ps.getExecutionStack();

        execStack.push(stmt2);
        execStack.push(stmt1);

        return ps;
    }

    @Override
    public String toString(){
        return stmt1.toString()+"\n"+stmt2.toString();
    }
}
