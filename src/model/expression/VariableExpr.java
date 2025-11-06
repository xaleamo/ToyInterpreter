package model.expression;

import model.programState.SymTable;
import model.value.Id;
import model.value.Value;
import myExceptions.UndeclaredVariable;

public class VariableExpr implements Expression {
    private final Id id;
    public VariableExpr(Id id) {
        this.id = id;
    }

    @Override
    public VariableExpr clone() {return new VariableExpr(id.clone());}

    /**
     *Looks up the value of the <b>id</b>
     * @param tbl Symbol table
     * @throws myExceptions.ProgramStateException if <b>id</b> doesn't exist in tbl
     */
    @Override
    public Value eval(SymTable tbl){
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
