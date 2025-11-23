package model.statement;

import model.program_state.ProgramState;

public class Nop implements Statement {

    @Override
    public Nop clone() {return new Nop();}

    @Override
    public ProgramState execute(ProgramState ps) {return ps;}

    @Override
    public String toString() {
        return "NOP;";
    }

}
