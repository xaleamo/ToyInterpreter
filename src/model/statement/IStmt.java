package model.statement;

import model.program_state.ADTs.MyIDictionary;
import model.program_state.PrgState;
import model.type.Type;
import model.value.Id;
import my_exceptions.TypeException;

public interface IStmt extends Cloneable {
    PrgState execute(PrgState ps);

    IStmt clone();
    MyIDictionary<Id, Type> typecheck(MyIDictionary<Id,Type> typeEnv) throws TypeException;
}
