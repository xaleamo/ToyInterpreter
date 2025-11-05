package Model.Statement;

import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.StringType;
import Model.Value.StringValue;
import Model.Value.Value;
import MyExceptions.ExpressionException;
import MyExceptions.FileException;
import MyExceptions.ProgramStateException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CloseRFile implements Statement{
    Expression expr;
    public CloseRFile(Expression expr){
        this.expr=expr;
    }


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
}
