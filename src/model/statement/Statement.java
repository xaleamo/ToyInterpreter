package model.statement;

import model.program_state.ProgramState;

public interface Statement extends Cloneable {
    ProgramState execute(ProgramState ps);
    //String toString();
    Statement clone();

}
