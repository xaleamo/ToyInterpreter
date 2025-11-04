package Model.Expression;

import Model.Value.Value;
import Model.ProgramState.SymTable;

public interface Expression {
    public Value eval(SymTable tbl);
}
