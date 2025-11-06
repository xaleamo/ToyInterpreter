package model.statement;

import model.programState.ProgramState;

public interface Statement {
    public ProgramState execute(ProgramState ps);
    @Override
    public String toString();
}
