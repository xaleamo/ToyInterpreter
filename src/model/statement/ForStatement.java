package model.statement;

import model.expression.*;
import model.program_state.ADTs.MyIDictionary;
import model.program_state.PrgState;
import model.type.IntType;
import model.type.Type;
import model.value.Id;
import my_exceptions.MyException;
import my_exceptions.TypeException;

public class ForStatement implements IStmt {
    Id v;
    Exp ini,upper,upd;//upper bound
    IStmt stmt;

    public ForStatement(Id v, Exp ini, Exp upper, Exp upd, IStmt stmt) {
        this.v=v;
        this.ini=ini;
        this.upper=upper;
        this.upd=upd;
        this.stmt=stmt;
    }

    @Override
    public IStmt clone() {return new ForStatement(v.clone(),ini.clone(),upper.clone(),upd.clone(),stmt.clone());}

    @Override
    public PrgState execute(PrgState state) {
        IStmt comp;
        comp=new CompStmt(
                new VarDeclStmt(new IntType(),v),
                new CompStmt(
                        new AssignStmt(v,ini),
                        new WhileStmt(
                                new RelationalExp(new VariableExp(v), RelationalExp.Operator.LESS,upper),
                                new CompStmt(
                                        stmt,
                                        new AssignStmt(v,upd)
                                )
                        )
                )
        );
        state.getExecutionStack().push(comp);
        return null;
    }

    @Override
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        Type type_ini=ini.typecheck(typeEnv);
        if(!type_ini.equals(new IntType())) throw new TypeException("Type mismatch: Initial expression not of IntType");

        typeEnv.add(v,type_ini);

        Type type_upper=upper.typecheck(typeEnv);
        if(!type_upper.equals(new IntType())) throw new TypeException("Type mismatch: Upper expression not of IntType");

        Type type_upd=upd.typecheck(typeEnv);
        if(!type_upd.equals(new IntType())) throw new TypeException("Type mismatch: Update expression not of IntType");






        return typeEnv;
    }

    @Override
    public String toString(){
        return "for("+v+"="+ini+";"+v+"<"+upper+";"+v+"="+upd+")"+"\n\t{"+stmt+"};\n";
    }
}
