import controller.Service;
import model.programState.*;
import model.statement.*;
import model.type.*;
import model.value.*;
import model.expression.*;
import repository.*;
import view.TextMenu;
import view.command.*;

public class Interpreter {
    public static void main(String[] args) {
        IRepository repo1=new Repository(Program1(),"files/log1.txt");
        IRepository repo2=new Repository(Program2(),"files/log2.txt");
        IRepository repo3=new Repository(Program3(),"files/log3.txt");
        IRepository repo4=new Repository(FaultyProgram1(),"files/log4.txt");
        IRepository repo5=new Repository(FaultyProgram2(),"files/log5.txt");
        IRepository repo6=new Repository(FileProgram1(),"files/log6.txt");

        Service s1=new Service(repo1);
        Service s2=new Service(repo2);
        Service s3=new Service(repo3);
        Service s4=new Service(repo4);
        Service s5=new Service(repo5);
        Service s6=new Service(repo6);

        TextMenu menu=new TextMenu();

        menu.addCommand(new ExitCommand("exit","exit"));
        menu.addCommand(new RunExample("run p1","run program 1",s1));
        menu.addCommand(new RunExample("run p2","run program 2",s2));
        menu.addCommand(new RunExample("run p3","run program 3",s3));
        menu.addCommand(new RunExample("run p4","run (faulty) program 4",s4));
        menu.addCommand(new RunExample("run p5","run (faulty) program 5",s5));
        menu.addCommand(new RunExample("run p6","run file program 1",s6));

        menu.run();

    }



    private static ProgramState Program1() {   //int v; v=2;Print(v)
        Statement s1 = new CompStatement(
                new VarDeclaration(new IntType(), new Id("v")),
                new CompStatement(
                        new AssignStatement(new Id("v"), new ValueExpr(new IntValue(2))),
                        new PrintStatement(new VariableExpr(new Id("v")))
                )
        );


        ExecutionStack executionStack = new ExecutionStack();
        executionStack.push(s1);

        return new ProgramState(executionStack, new SymTable(), new Output());
    }

    private static ProgramState Program2() {  //int a;int b; a=2+3*5;b=a+1;Print(b)
        Statement s2 = new CompStatement(
                new VarDeclaration(new IntType(), new Id("a")),
                new CompStatement(
                        new VarDeclaration(new IntType(), new Id("b")),
                        new CompStatement(
                                new AssignStatement(
                                        new Id("a"),
                                        new ArithExpr(
                                                new ValueExpr(new IntValue(2)),
                                                new ArithExpr(
                                                        new ValueExpr(new IntValue(3)),
                                                        new ValueExpr(new IntValue(5)),
                                                        ArithExpr.Operator.MULTIPLY
                                                ),
                                                ArithExpr.Operator.ADD
                                        )
                                ),
                                new CompStatement(
                                        new AssignStatement(
                                                new Id("b"),
                                                new ArithExpr(
                                                        new VariableExpr(new Id("a")),
                                                        new ValueExpr(new IntValue(1)),
                                                        ArithExpr.Operator.ADD
                                                )
                                        ),
                                        new PrintStatement(new VariableExpr(new Id("b")))
                                )
                        )
                )
        );
        ExecutionStack executionStack = new ExecutionStack();
        executionStack.push(s2);
        return new ProgramState(executionStack, new SymTable(), new Output());
    }

    private static ProgramState Program3() {   //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        Statement s3 = new CompStatement(
                new VarDeclaration(new BoolType(), new Id("a")),
                new CompStatement(
                        new VarDeclaration(new IntType(), new Id("v")),
                        new CompStatement(
                                new AssignStatement(
                                        new Id("a"),
                                        new ValueExpr(new BoolValue(true))
                                ),
                                new CompStatement(
                                        new IfStatement(
                                                new VariableExpr(new Id("a")),
                                                new AssignStatement(
                                                        new Id("v"),
                                                        new ValueExpr(new IntValue(2))
                                                ),
                                                new AssignStatement(
                                                        new Id("v"),
                                                        new ValueExpr(new IntValue(3))
                                                )
                                        ),
                                        new PrintStatement(new VariableExpr(new Id("v")))
                                )
                        )
                )
        );
        ExecutionStack executionStack = new ExecutionStack();
        executionStack.push(s3);
        return new ProgramState(executionStack, new SymTable(), new Output());
    }

    /////assignment before declaration //a=10;print(a);
    private static ProgramState FaultyProgram1() {
        Statement ex1 = new CompStatement(
                new AssignStatement(
                        new Id("a"),
                        new ValueExpr(new IntValue(10))
                ),
                new PrintStatement(new VariableExpr(new Id("a")))
        );
        ExecutionStack executionStack = new ExecutionStack();
        executionStack.push(ex1);
        return new ProgramState(executionStack, new SymTable(), new Output());
    }

    /////type mismatch in assignment //int v; v= true;
    private static ProgramState FaultyProgram2() {
        Statement ex2 = new CompStatement(
                new VarDeclaration(new IntType(), new Id("v")),
                new AssignStatement(
                        new Id("v"),
                        new ValueExpr(new BoolValue(true))
                )
        );
        ExecutionStack executionStack = new ExecutionStack();
        executionStack.push(ex2);
        return new ProgramState(executionStack, new SymTable(), new Output());
    }

    ///////open, read twice and close file
    /////string varf;
    ///// varf="test.in";
    ///// openRFile(varf);
    ///// int varc;
    ///// readFile(varf,varc);print(varc);
    ///// readFile(varf,varc);print(varc)
    ///// closeRFile(varf)
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
