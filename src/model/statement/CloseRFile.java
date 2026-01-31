package model.statement;

import model.expression.Exp;
import model.program_state.ADTs.MyIDictionary;
import model.program_state.PrgState;
import model.type.StringType;
import model.type.Type;
import model.value.Id;
import model.value.StringValue;
import model.value.Value;
import my_exceptions.ExpressionException;
import my_exceptions.FileException;
import my_exceptions.ProgramStateException;
import my_exceptions.TypeException;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    Exp expr;
    public CloseRFile(Exp expr){
        this.expr=expr;
    }

    @Override
    public CloseRFile clone() {return new CloseRFile(expr.clone());}

    @Override
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        Type typeExp=expr.typecheck(typeEnv);
        if(typeExp.equals(new StringType())) return typeEnv;
        else throw new TypeException("Type mismatch: Expression not of StringType.");
    }

    @Override
    public PrgState execute(PrgState ps) throws ProgramStateException, ExpressionException {
        Value val=expr.eval(ps.getSymTable(), ps.getHeap());
        if(!val.getType().equals(new StringType())) throw new ExpressionException("Type Mismatch");
        StringValue key=(StringValue)val;
        if(!ps.getFileTable().isDefined(key)) throw new FileException("File is not open.");

        BufferedReader br=ps.getFileTable().lookUp(key);
        try {
            br.close();
        }catch (IOException e){
            throw new FileException("Something went wrong while closing the file.");
        }
        ps.getFileTable().remove(key);
        return null;
    }

    @Override
    public String toString(){
        return "CloseRFile: "+expr.toString();
    }
}
