package Model.Statement;

import Model.ProgramState.ProgramState;

public class Nop implements Statement {
    @Override
    public ProgramState execute(ProgramState ps) {return ps;}

    @Override
    public String toString() {
        return "NOP;";
    }

}
