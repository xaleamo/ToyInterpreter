package model.statement;

import model.program_state.ADTs.MyIDictionary;
import model.program_state.PrgState;
import model.type.Type;
import model.value.Id;
import my_exceptions.TypeException;

public class NopStmt implements IStmt {

    @Override
    public NopStmt clone() {return new NopStmt();}

    @Override
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState ps) {return null;}

    @Override
    public String toString() {
        return "NOP;";
    }

}
