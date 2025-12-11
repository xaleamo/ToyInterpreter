package model.statement;

import my_exceptions.ProgramStateException;
import model.program_state.PrgState;
import model.program_state.SymTable;
import model.type.*;
import model.value.*;

public class VarDeclaration implements Statement {
    Type type;
    Id id;
    public VarDeclaration(Type type, Id id) {
        this.type = type;
        this.id = id;
    }
    @Override
    public VarDeclaration clone() {return new VarDeclaration(type.clone(),id.clone());}

    @Override
    public PrgState execute(PrgState ps) {
        SymTable symTable = ps.getSymTable();
        if(symTable.lookUp(id)==null){
            symTable.add(id, type.defaultValue());
        }
        else{
            throw new ProgramStateException("Variable already exists");
        }
        return null;
    }
    @Override
    public String toString(){
        return type.toString() + " " + id.toString()+";";
    }
}
