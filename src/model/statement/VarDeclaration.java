package model.statement;

import model.program_state.ADTs.MyIDictionary;
import my_exceptions.ProgramStateException;
import model.program_state.PrgState;
import model.program_state.SymTable;
import model.type.*;
import model.value.*;
import my_exceptions.TypeException;

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
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        typeEnv.add(id,type);
        return typeEnv;
    }

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
