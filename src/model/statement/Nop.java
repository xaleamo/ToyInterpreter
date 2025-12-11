package model.statement;

import model.program_state.PrgState;

public class Nop implements Statement {

    @Override
    public Nop clone() {return new Nop();}

    @Override
    public PrgState execute(PrgState ps) {return null;}

    @Override
    public String toString() {
        return "NOP;";
    }

}
