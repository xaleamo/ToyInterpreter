package model.expression;

import model.program_state.ADTs.MyIDictionary;
import model.program_state.Heap;
import model.program_state.SymTable;
import model.type.Type;
import model.value.Id;
import model.value.Value;
import my_exceptions.MyException;
import my_exceptions.UndeclaredVariable;

public class VariableExpr implements Expression {
    private final Id id;
    public VariableExpr(Id id) {
        this.id = id;
    }

    @Override
    public VariableExpr clone() {return new VariableExpr(id.clone());}

    @Override
    public Type typecheck(MyIDictionary<Id, Type> typeEnv) throws MyException {
        return typeEnv.lookUp(id);
    }

    /**
     * Looks up the value of the <b>id</b>
     *
     * @param tbl  Symbol table
     * @param heap
     * @throws my_exceptions.ProgramStateException if <b>id</b> doesn't exist in tbl
     */
    @Override
    public Value eval(SymTable tbl, Heap heap){
        if(tbl.isDefined(id))
            return tbl.lookUp(id);
        else
            throw new UndeclaredVariable("Error: Variable "+id.toString()+" is not defined");
    }

    @Override
    public String toString(){
        return id.toString();
    }

}
