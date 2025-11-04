package Model.Statement;

import Model.ProgramState.ProgramState;
import Model.ProgramState.SymTable;

public interface Statement {
    public ProgramState execute(ProgramState ps);
    @Override
    public String toString();
}
