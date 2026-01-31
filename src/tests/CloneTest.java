package tests;

import controller.Service;
import model.expression.ValueExp;
import model.expression.VariableExp;
import model.program_state.*;
import model.statement.*;
import model.type.IntType;
import model.type.StringType;
import model.value.Id;
import model.value.StringValue;
import my_exceptions.ProgramStateException;
import repository.Repository;

public class CloneTest {
    public static void main(){
        testClone();
    }
    private static void testClone(){
        PrgState old=FileProgram1();
        Repository repoOld=new Repository(old,"files/clonetest.txt");
        Service serviceOld=new Service(repoOld);

        serviceOld.oneStepForAllPrg(repoOld.getPrgList());
        serviceOld.oneStepForAllPrg(repoOld.getPrgList());//varf in symtable
        serviceOld.oneStepForAllPrg(repoOld.getPrgList());
        serviceOld.oneStepForAllPrg(repoOld.getPrgList());//varf assignment
        serviceOld.oneStepForAllPrg(repoOld.getPrgList());
        serviceOld.oneStepForAllPrg(repoOld.getPrgList());//file opened

        repoOld.logPrgStateExec(old);
        PrgState nou=old.clone();
        Repository repoNou=new Repository(nou,"files/clonetest.txt");
        Service serviceNou=new Service(repoNou);

        PrgState othernew=nou.clone();
        Repository repoOther=new Repository(othernew,"files/clonetest.txt");
        Service serviceOther=new Service(repoOther);
        serviceOther.setDisplayFlag(true);
        serviceOther.allStep();

        int count=0;
        try{
            while(true) {
                serviceOld.oneStepForAllPrg(repoOld.getPrgList());
                count++;
            }
        }catch(ProgramStateException e){
            //everything is fine
        }
        for(int i=0;i<count;i++){//try doing the same steps
            serviceNou.oneStepForAllPrg(repoOld.getPrgList());
        }
        try{
            serviceNou.oneStepForAllPrg(repoOld.getPrgList());//must throw error, meaning nothing more to execute
        }catch(ProgramStateException e){
            //everything is fine
        }

    }
    private static PrgState FileProgram1() {
        IStmt s1 = new CompStmt(
                new VarDeclStmt(new StringType(), new Id("varf")),
                new CompStmt(
                        new AssignStmt(new Id("varf"), new ValueExp(new StringValue("Files/test.txt"))),
                        new CompStmt(
                                new OpenRFile(new VariableExp(new Id("varf"))),
                                new CompStmt(
                                        new VarDeclStmt(new IntType(), new Id("varc")),
                                        new CompStmt(
                                                new ReadFile(new VariableExp(new Id("varf")), new Id("varc")),
                                                new CompStmt(
                                                        new PrintStmt(new VariableExp(new Id("varc"))),
                                                        new CompStmt(
                                                                new ReadFile(new VariableExp(new Id("varf")), new Id("varc")),
                                                                new CompStmt(
                                                                        new PrintStmt(new VariableExp(new Id("varc"))),
                                                                        new CloseRFile(new VariableExp(new Id("varf")))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        //Statement s2= new CloseRFile(new VariableExp(new Id("varf")));

        ExecutionStack executionStack = new ExecutionStack();
        //executionStack.push(s2);
        executionStack.push(s1);
        return new PrgState(executionStack, new SymTable(), new Heap(), new Output(), new FileTable());

    }


}
