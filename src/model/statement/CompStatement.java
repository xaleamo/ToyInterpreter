package model.statement;

import model.program_state.ExecutionStack;
import model.program_state.ProgramState;

public class CompStatement implements Statement {
    Statement stmt1,stmt2;
    public CompStatement(Statement stmt1, Statement stmt2) {
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    @Override
    public CompStatement clone() {return new CompStatement(this.stmt1.clone(), this.stmt2.clone());}

    @Override
    public ProgramState execute(ProgramState ps) {
        ExecutionStack execStack=ps.getExecutionStack();

        execStack.push(stmt2);
        execStack.push(stmt1);

        return ps;
    }

    @Override
    public String toString(){
        return stmt1.toString()+"\n"+stmt2.toString()+';';//DELETE ';' for 'normal' printing.
    }
}
