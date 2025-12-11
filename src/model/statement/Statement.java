package model.statement;

import model.program_state.PrgState;

public interface Statement extends Cloneable {
    PrgState execute(PrgState ps);

    Statement clone();

}
