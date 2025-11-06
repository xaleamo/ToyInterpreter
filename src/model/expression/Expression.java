package model.expression;

import model.value.Value;
import model.programState.SymTable;

public interface Expression {
    public Value eval(SymTable tbl);
}
