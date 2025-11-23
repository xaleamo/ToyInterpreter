import controller.Service;
import model.program_state.*;
import model.statement.*;
import model.type.*;
import model.value.*;
import model.expression.*;
import repository.*;
import view.TextMenu;
import view.command.*;

import javax.swing.plaf.nimbus.State;

public class Interpreter {
    public static void main(String[] args) {


        TextMenu menu=new TextMenu();

        menu.addCommand(new ExitCommand("exit","exit"));
        menu.addCommand(new RunExample("run p1","run program 1",Program1()));
        menu.addCommand(new RunExample("run p2","run program 2",Program2()));
        menu.addCommand(new RunExample("run p3","run program 3",Program3()));
        menu.addCommand(new RunExample("run p4","run (faulty) program 4",FaultyProgram1()));
        menu.addCommand(new RunExample("run p5","run (faulty) program 5",FaultyProgram2()));
        menu.addCommand(new RunExample("run p6","run file program 1",FileProgram1()));

        menu.run();

    }



    private static Statement Program1() {   //int v; v=2;Print(v)
        return new CompStatement(
                new VarDeclaration(new IntType(), new Id("v")),
                new CompStatement(
                        new AssignStatement(new Id("v"), new ValueExpr(new IntValue(2))),
                        new PrintStatement(new VariableExpr(new Id("v")))
                )
        );
    }

    private static Statement Program2() {  //int a;int b; a=2+3*5;b=a+1;Print(b)
        return new CompStatement(
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

    }

    private static Statement Program3() {   //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        return new CompStatement(
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
    }

    /////assignment before declaration //a=10;print(a);
    private static Statement FaultyProgram1() {
        return new CompStatement(
                new AssignStatement(
                        new Id("a"),
                        new ValueExpr(new IntValue(10))
                ),
                new PrintStatement(new VariableExpr(new Id("a")))
        );

    }

    /////type mismatch in assignment //int v; v= true;
    private static Statement FaultyProgram2() {
        return new CompStatement(
                new VarDeclaration(new IntType(), new Id("v")),
                new AssignStatement(
                        new Id("v"),
                        new ValueExpr(new BoolValue(true))
                )
        );
        
    }

    ///////open, read twice and close file
    /////string varf;
    ///// varf="test.in";
    ///// openRFile(varf);
    ///// int varc;
    ///// readFile(varf,varc);print(varc);
    ///// readFile(varf,varc);print(varc)
    ///// closeRFile(varf)
    private static Statement FileProgram1() {
    return new CompStatement(
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
}

}
