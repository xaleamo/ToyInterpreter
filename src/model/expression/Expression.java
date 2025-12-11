package model.expression;

import model.program_state.ADTs.MyIDictionary;
import model.program_state.Heap;
import model.type.Type;
import model.value.Id;
import model.value.Value;
import model.program_state.SymTable;
import my_exceptions.MyException;

public interface Expression extends Cloneable {
    Value eval(SymTable tbl, Heap heap);
    Expression clone();
    Type typecheck(MyIDictionary<Id, Type> typeEnv) throws MyException;
}
