package model.statement;

import model.expression.Exp;
import model.program_state.ADTs.MyIDictionary;
import model.program_state.PrgState;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.Id;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import my_exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    Exp expr;
    Id id;

    public ReadFile(Exp expr, Id id){
        this.expr=expr;
        this.id=id;
    }
    @Override
    public ReadFile clone() {return new ReadFile(expr.clone(),id.clone());}

    @Override
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        Type typeV=typeEnv.lookUp(id);
        Type typeExp=expr.typecheck(typeEnv);
        if(typeV==null) throw new TypeException("Variable "+id+" not found");
        if(!typeV.equals(new IntType())) throw new TypeException("Variable not of type int.");
        if(!typeExp.equals(new StringType()))throw new TypeException("Type mismatch: Expression not of StringType.");
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState ps) {
        //check expr value and type
        Value val=expr.eval(ps.getSymTable(), ps.getHeap());
        if(!val.getType().equals(new StringType())) throw new ExpressionException("Type Mismatch");
        StringValue value=(StringValue)val;

        //check id value and type
        if(!ps.getSymTable().isDefined(id)) throw new UndeclaredVariable("Variable is undeclared.");
        Value var=ps.getSymTable().lookUp(id);
        if(!var.getType().equals(new IntType())) throw new ProgramStateException("Given variable is not IntType.");

        //check entry in FileTable
        BufferedReader br=null;
        if(!ps.getFileTable().isDefined(value)) throw new FileException("File is not open.");
        br=ps.getFileTable().lookUp(value);

        //read line from br
        try {
            String s = br.readLine();
            int i=0;
            if(s!=null) i=Integer.parseInt(s);
            ps.getSymTable().add(id,new IntValue(i));
        }
        catch(IOException e){
            throw new FileException("Error reading file.");
        }
        catch(NumberFormatException e){
            throw new MyException("File contents are wrong, this should never happen!! ");
        }

        return null;
    }

    @Override
    public String toString(){
        return "ReadFile: "+expr.toString()+" -> "+id.toString();
    }
}
