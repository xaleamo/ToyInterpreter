package model.statement;

import model.expression.Expression;
import model.programState.ProgramState;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;
import myExceptions.ExpressionException;
import myExceptions.FileException;
import myExceptions.ProgramStateException;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements Statement{
    Expression expr;
    public CloseRFile(Expression expr){
        this.expr=expr;
    }

    @Override
    public CloseRFile clone() {return new CloseRFile(expr.clone());}

    @Override
    public ProgramState execute(ProgramState ps) throws ProgramStateException, ExpressionException {
        Value val=expr.eval(ps.getSymTable());
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
        return ps;
    }

    @Override
    public String toString(){
        return "CloseRFile: "+expr.toString();
    }
}
