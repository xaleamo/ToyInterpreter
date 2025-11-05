package Model.Statement;

import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.StringType;
import Model.Value.*;
import MyExceptions.ExpressionException;
import MyExceptions.FileException;
import MyExceptions.MyException;
import MyExceptions.ProgramStateException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements Statement{
    Expression expr;

    public OpenRFile(Expression expr){
        this.expr = expr;
    }

    @Override
    public ProgramState execute(ProgramState ps) throws ProgramStateException, ExpressionException {
        Value val=expr.eval(ps.getSymTable());
        if(!val.getType().equals(new StringType())) throw new ExpressionException("Type Mismatch");
        StringValue value=(StringValue)val;
        if(ps.getFileTable().isDefined(value)) throw new FileException("File is already open.");

        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(value.getValue()));
        }
        catch (FileNotFoundException e){
            throw new FileException("File Not Found");
        }
        ps.getFileTable().add(value,br);
        return ps;
    }
}
