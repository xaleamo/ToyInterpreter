package model.expression;

import model.program_state.Heap;
import model.value.Value;
import model.program_state.SymTable;

public interface Expression extends Cloneable {
    Value eval(SymTable tbl, Heap heap);
    Expression clone();
}
