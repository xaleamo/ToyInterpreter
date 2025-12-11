package model.statement;

import model.expression.Expression;
import model.program_state.ADTs.MyIDictionary;
import model.program_state.PrgState;
import model.type.StringType;
import model.type.Type;
import model.value.*;
import my_exceptions.ExpressionException;
import my_exceptions.FileException;
import my_exceptions.ProgramStateException;
import my_exceptions.TypeException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements Statement{
    Expression expr;

    public OpenRFile(Expression expr){
        this.expr = expr;
    }

    @Override
    public OpenRFile clone() {return new OpenRFile(expr.clone());}

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
        return null;
    }

    @Override
    public String toString(){
        return "OpenRFile: "+expr.toString();
    }
}
