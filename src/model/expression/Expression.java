package model.expression;

import model.value.Value;
import model.programState.SymTable;

public interface Expression extends Cloneable {
    Value eval(SymTable tbl);
    Expression clone();
}
