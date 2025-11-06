package model.statement;

import myExceptions.ProgramStateException;
import model.programState.ProgramState;
import model.programState.SymTable;
import model.type.*;
import model.value.*;

public class VarDeclaration implements Statement {
    Type type;
    Id id;
    @Override
    public ProgramState execute(ProgramState ps) {
        SymTable symTable = ps.getSymTable();
        if(symTable.lookUp(id)==null){
            symTable.add(id, type.defaultValue());
        }
        else{
            throw new ProgramStateException("Variable already exists");
        }
        return ps;
    }
    public VarDeclaration(Type type, Id id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString(){
        return type.toString() + " " + id.toString()+";";
    }
}
