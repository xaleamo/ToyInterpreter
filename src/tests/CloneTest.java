package tests;

import controller.Service;
import model.expression.ValueExpr;
import model.expression.VariableExpr;
import model.program_state.ExecutionStack;
import model.program_state.Output;
import model.program_state.ProgramState;
import model.program_state.SymTable;
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
        ProgramState old=FileProgram1();
        Repository repoOld=new Repository(old,"files/clonetest.txt");
        Service serviceOld=new Service(repoOld);

        serviceOld.executeOneStep();
        serviceOld.executeOneStep();//varf in symtable
        serviceOld.executeOneStep();
        serviceOld.executeOneStep();//varf assignment
        serviceOld.executeOneStep();
        serviceOld.executeOneStep();//file opened

        repoOld.logPrgStateExec();
        ProgramState nou=old.clone();
        Repository repoNou=new Repository(nou,"files/clonetest.txt");
        Service serviceNou=new Service(repoNou);

        ProgramState othernew=nou.clone();
        Repository repoOther=new Repository(othernew,"files/clonetest.txt");
        Service serviceOther=new Service(repoOther);
        serviceOther.setDisplayFlag(true);
        serviceOther.executeUntilEnd();

        int count=0;
        try{
            while(true) {
                serviceOld.executeOneStep();
                count++;
            }
        }catch(ProgramStateException e){
            //everything is fine
        }
        for(int i=0;i<count;i++){//try doing the same steps
            serviceNou.executeOneStep();
        }
        try{
            serviceNou.executeOneStep();//must throw error, meaning nothing more to execute
        }catch(ProgramStateException e){
            //everything is fine
        }

    }
    private static ProgramState FileProgram1() {
        Statement s1 = new CompStatement(
                new VarDeclaration(new StringType(), new Id("varf")),
                new CompStatement(
                        new AssignStatement(new Id("varf"), new ValueExpr(new StringValue("Files/test.txt"))),
                        new CompStatement(
                                new OpenRFile(new VariableExpr(new Id("varf"))),
                                new CompStatement(
                                        new VarDeclaration(new IntType(), new Id("varc")),
                                        new CompStatement(
                                                new ReadFile(new VariableExpr(new Id("varf")), new Id("varc")),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpr(new Id("varc"))),
                                                        new CompStatement(
                                                                new ReadFile(new VariableExpr(new Id("varf")), new Id("varc")),
                                                                new CompStatement(
                                                                        new PrintStatement(new VariableExpr(new Id("varc"))),
                                                                        new CloseRFile(new VariableExpr(new Id("varf")))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        //Statement s2= new CloseRFile(new VariableExpr(new Id("varf")));

        ExecutionStack executionStack = new ExecutionStack();
        //executionStack.push(s2);
        executionStack.push(s1);
        return new ProgramState(executionStack, new SymTable(), new Output());

    }


}
