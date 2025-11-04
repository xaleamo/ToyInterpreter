package Model.Expression;

import Model.ProgramState.SymTable;
import Model.Value.Id;
import Model.Value.Value;
import MyExceptions.ProgramStateException;
import MyExceptions.UndeclaredVariable;

public class VariableExpr implements Expression {
    private final Id id;
    public VariableExpr(Id id) {
        this.id = id;
    }

    /**
     *Looks up the value of the <b>id</b>
     * @param tbl Symbol table
     * @throws MyExceptions.ProgramStateException if <b>id</b> doesn't exist in tbl
     */
    @Override
    public Value eval(SymTable tbl){
        if(tbl.isDefined(id))
            return tbl.LookUp(id);
        else
            throw new UndeclaredVariable("Error: Variable "+id.toString()+" is not defined");
    }

    @Override
    public String toString(){
        return id.toString();
    }

}
