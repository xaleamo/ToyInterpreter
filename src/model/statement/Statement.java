package model.statement;

import model.programState.ProgramState;

public interface Statement extends Cloneable {
    ProgramState execute(ProgramState ps);
    String toString();
    Statement clone();

}
