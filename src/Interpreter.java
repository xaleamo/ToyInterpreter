import model.statement.*;
import model.type.*;
import model.value.*;
import model.expression.*;
import view.TextMenu;
import view.command.*;

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
        menu.addCommand(new RunExample("run p7","run (alloc) heap program 1",HeapProgram1()));
        menu.addCommand(new RunExample("run p8","run (read) heap program 2",HeapProgram2()));
        menu.addCommand(new RunExample("run p9","run (write) heap program 3",HeapProgram3()));
        menu.addCommand(new RunExample("run p10","run garbage collector heap program 4",HeapProgram4()));//safe version
        menu.addCommand(new RunExample("run p11","run (invalid addr overwrite) heap program 5",HeapProgram5()));
        menu.addCommand(new RunExample("run p12","run (invalid addr overwrite) heap program 6",HeapProgram6()));
        menu.addCommand(new RunExample("run p13","run while program 1",WhileProgram1()));
        menu.addCommand(new RunExample("run p14","run read heap invalid",HeapReadInvalidProgram()));
        menu.addCommand(new RunExample("run p15","run fork program 1",ForkProgram1()));
        menu.addCommand(new RunExample("run p16","run fork program 2",ForkProgram2()));
        menu.addCommand(new RunExample("run p17","run fork program 3",ForkProgram3()));
        menu.addCommand(new RunExample("run p18","run faulty fork program 1",FaultyForkProgram1()));

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
                                                ArithExpr.Operator.ADD, new ArithExpr(
                                                        new ValueExpr(new IntValue(3)),
                                                ArithExpr.Operator.MULTIPLY, new ValueExpr(new IntValue(5))
                                        )
                                        )
                                ),
                                new CompStatement(
                                        new AssignStatement(
                                                new Id("b"),
                                                new ArithExpr(
                                                        new VariableExpr(new Id("a")),
                                                        ArithExpr.Operator.ADD, new ValueExpr(new IntValue(1))
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

    ///ALLOC TEST
    /// Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
    private static Statement HeapProgram1(){
        return new CompStatement(
                new VarDeclaration(new RefType(new IntType()),new Id("v")),
                new CompStatement(
                        new NewStatement(new Id("v"),new ValueExpr(new IntValue(20))),
                        new CompStatement(
                                new VarDeclaration(new RefType(new RefType(new IntType())),new Id("a")),
                                new CompStatement(
                                        new NewStatement(new Id("a"),new VariableExpr(new Id("v"))),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpr(new Id("v"))),
                                                new PrintStatement(new VariableExpr(new Id("a")))
                                        )
                                )
                        )
                )
        );
    }
    /// READ HEAP TEST
    ////Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
    private static Statement HeapProgram2(){
        return new CompStatement(
                new VarDeclaration(new RefType(new IntType()), new Id("v")),
                new CompStatement(
                        new NewStatement(
                                new Id("v"),
                                new ValueExpr(new IntValue(20))
                        ),
                        new CompStatement(
                                new VarDeclaration(new RefType(new RefType(new IntType())), new Id("a")),
                                new CompStatement(
                                        new NewStatement(
                                                new Id("a"),
                                                new VariableExpr(new Id("v"))
                                        ),
                                        new CompStatement(
                                                new PrintStatement(
                                                        new RHExp(new VariableExpr(new Id("v")))
                                                ),
                                                new PrintStatement(
                                                        new ArithExpr(
                                                                new RHExp(
                                                                        new RHExp(new VariableExpr(new Id("a")))
                                                                ),
                                                                ArithExpr.Operator.ADD, new ValueExpr(new IntValue(5))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

    }

    /// WRITE HEAP TEST
    /// Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
    public static Statement HeapProgram3(){
        return new CompStatement(
                new VarDeclaration(new RefType(new IntType()), new Id("v")),
                new CompStatement(
                        new NewStatement(
                                new Id("v"),
                                new ValueExpr(new IntValue(20))
                        ),
                        new CompStatement(
                                new PrintStatement(
                                        new RHExp(new VariableExpr(new Id("v")))
                                ),
                                new CompStatement(
                                        new WriteHeap(
                                                new Id("v"),
                                                new ValueExpr(new IntValue(30))
                                        ),
                                        new PrintStatement(
                                                new ArithExpr(
                                                        new RHExp(new VariableExpr(new Id("v"))),
                                                        ArithExpr.Operator.ADD, new ValueExpr(new IntValue(5))
                                                )
                                        )
                                )
                        )
                )
        );

    }

    /// Should throw error when executing with unsafe garbage collector
    /// Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
    private static Statement HeapProgram4() {
        return new CompStatement(
                new VarDeclaration(new RefType(new IntType()), new Id("v")),
                new CompStatement(
                        new NewStatement(
                                new Id("v"),
                                new ValueExpr(new IntValue(20))
                        ),
                        new CompStatement(
                                new VarDeclaration(new RefType(new RefType(new IntType())), new Id("a")),
                                new CompStatement(
                                        new NewStatement(
                                                new Id("a"),
                                                new VariableExpr(new Id("v"))
                                        ),
                                        new CompStatement(
                                                new NewStatement(      // new(v,30)
                                                        new Id("v"),
                                                        new ValueExpr(new IntValue(30))
                                                ),
                                                new PrintStatement(
                                                        new RHExp(
                                                                new RHExp(new VariableExpr(new Id("a")))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

    }

    /**
    Ref int v;
    new(v, 20);
    Ref Ref int a;
    new(a, v);
    new(v, 999);      // breaks the chain (v no longer points to the old cell)
    writeHeap(a, (2 IntType));
    print(rH(rH(a)));
     */
    private static Statement HeapProgram5() {
        return new CompStatement(
                new VarDeclaration(new RefType(new IntType()), new Id("v")),
                new CompStatement(
                        new NewStatement(new Id("v"), new ValueExpr(new IntValue(20))),   // v -> cell1
                        new CompStatement(
                                new VarDeclaration(new RefType(new RefType(new IntType())), new Id("a")),
                                new CompStatement(
                                        new NewStatement(new Id("a"), new VariableExpr(new Id("v"))), // a -> cell2, storing address1
                                        new CompStatement(
                                                new NewStatement(new Id("v"), new ValueExpr(new IntValue(999))), // v -> NEW cell3
                                                new CompStatement(
                                                        new WriteHeap(
                                                                new Id("a"),
                                                                new ValueExpr(
                                                                        new RefValue(
                                                                                2,               // BAD address (not in heap)
                                                                                new IntType()
                                                                        )
                                                                )
                                                        ),
                                                        new PrintStatement(
                                                                new RHExp(
                                                                        new RHExp(
                                                                                new VariableExpr(new Id("a"))
                                                                        )
                                                                )   // this crashes: trying to read heap[999]
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );


    }

    /**with error
     * Ref Int aux;
     * Ref Ref Int a,v;
     * new(aux, 100);
     * new(a,aux);
     * new(v,a);
     * new(a,v);
     */
    private static Statement HeapProgram6() {
        return new CompStatement(
            new VarDeclaration(
                new RefType(new IntType()),
                new Id("aux")
            ),
            new CompStatement(
                new VarDeclaration(
                    new RefType(new RefType(new IntType())),
                    new Id("a")
                ),
                new CompStatement(
                    new VarDeclaration(
                        new RefType(new RefType(new IntType())),
                        new Id("v")
                    ),
                    new CompStatement(
                        new NewStatement(new Id("aux"),new ValueExpr(new IntValue(100))),
                        new CompStatement(
                            new NewStatement(new Id("a"),new VariableExpr(new Id("aux"))),
                            new CompStatement(
                                new NewStatement(new Id("v"),new ValueExpr(new RefValue(2,new IntType()))),
                                new CompStatement(
                                    new NewStatement(new Id("a"),new VariableExpr(new Id("v"))),
                                    new PrintStatement(new RHExp(new VariableExpr(new Id("v"))))
                                )
                            )
                        )
                    )
                )
            )
        );
    }

    private static Statement WhileProgram1() {
        return new CompStatement(
                new VarDeclaration(new IntType(),new Id("v")),
                new CompStatement(
                        new AssignStatement(
                                new Id("v"),
                                new ValueExpr(new IntValue(4))
                        ),
                        new CompStatement(
                                new WhileStatement(
                                        new RelationalExpr(
                                                new VariableExpr(new Id("v")),
                                                RelationalExpr.Operator.GREATER,
                                                new ValueExpr(new IntValue(0))

                                        ),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpr(new Id("v"))),
                                                new AssignStatement(
                                                        new Id("v"),
                                                        new ArithExpr(
                                                                new VariableExpr(new Id("v")),
                                                                ArithExpr.Operator.SUBTRACT,
                                                                new ValueExpr(new IntValue(1))
                                                        )
                                                )
                                        )
                                ),
                                new PrintStatement(new VariableExpr(new Id("v")))
                        )
                )
        );

    }

    private static Statement HeapReadInvalidProgram() {
        return new CompStatement(
                new VarDeclaration(new RefType(new IntType()), new Id("v")),
                new CompStatement(
                        new NewStatement(
                                new Id("v"),
                                new ValueExpr(new IntValue(20))
                        ),
                        new CompStatement(
                                new VarDeclaration(new RefType(new RefType(new IntType())), new Id("a")),
                                new CompStatement(
                                        new NewStatement(
                                                new Id("a"),
                                                new VariableExpr(new Id("v"))
                                        ),
                                        new CompStatement(
                                                new PrintStatement(
                                                        new RHExp(new VariableExpr(new Id("v")))
                                                ),
                                                new PrintStatement(
                                                        new ArithExpr(
                                                                new RHExp(
                                                                        new RHExp(new ValueExpr(new RefValue(1,new RefType(new IntType()))))
                                                                ),
                                                                ArithExpr.Operator.ADD, new ValueExpr(new IntValue(5))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    //normal
    private static Statement ForkProgram1() {
        return
                new CompStatement(
                        new VarDeclaration(new IntType(),new Id("v")),
                        new CompStatement(
                                new VarDeclaration(new RefType(new IntType()),new Id("a")),
                                new CompStatement(
                                        new AssignStatement(
                                                new Id("v"),
                                                new ValueExpr(new IntValue(10))
                                        ),
                                        new CompStatement(
                                                new NewStatement(
                                                        new Id("a"),
                                                        new ValueExpr(new IntValue(22))
                                                ),
                                                new CompStatement(
                                                        new ForkStatement(
                                                                new CompStatement(
                                                                        new WriteHeap(
                                                                                new Id("a"),
                                                                                new ValueExpr(new IntValue(30))
                                                                        ),
                                                                        new CompStatement(
                                                                                new AssignStatement(
                                                                                        new Id("v"),
                                                                                        new ValueExpr(new IntValue(32))
                                                                                ),
                                                                                new CompStatement(
                                                                                        new PrintStatement(
                                                                                                new VariableExpr(new Id("v"))
                                                                                        ),
                                                                                        new PrintStatement(
                                                                                                new RHExp(
                                                                                                        new VariableExpr(new Id("a"))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStatement(
                                                                new PrintStatement(new VariableExpr(new Id("v"))),
                                                                new PrintStatement(
                                                                        new RHExp(new VariableExpr(new Id("a")))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );

    }

    //...22...
    private static Statement ForkProgram2() {
        return
                new CompStatement(
                        new VarDeclaration(new IntType(),new Id("v")),
                        new CompStatement(
                                new VarDeclaration(new RefType(new IntType()),new Id("a")),
                                new CompStatement(
                                        new AssignStatement(
                                                new Id("v"),
                                                new ValueExpr(new IntValue(10))
                                        ),
                                        new CompStatement(
                                                new NewStatement(
                                                        new Id("a"),
                                                        new ValueExpr(new IntValue(22))
                                                ),
                                                new CompStatement(
                                                        new ForkStatement(
                                                                new CompStatement(
                                                                        new CompStatement(
                                                                                new PrintStatement(new VariableExpr(new Id("a"))),
                                                                                new WriteHeap(
                                                                                        new Id("a"),
                                                                                        new ValueExpr(new IntValue(30))
                                                                                )
                                                                        ),
                                                                        new CompStatement(
                                                                                new AssignStatement(
                                                                                        new Id("v"),
                                                                                        new ValueExpr(new IntValue(32))
                                                                                ),
                                                                                new CompStatement(
                                                                                        new PrintStatement(
                                                                                                new VariableExpr(new Id("v"))
                                                                                        ),
                                                                                        new PrintStatement(
                                                                                                new RHExp(
                                                                                                        new VariableExpr(new Id("a"))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStatement(
                                                                new PrintStatement(new VariableExpr(new Id("v"))),
                                                                new PrintStatement(
                                                                        new RHExp(new VariableExpr(new Id("a")))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );

    }

    //10...10
    private static Statement ForkProgram3() {
        return
                new CompStatement(
                        new VarDeclaration(new IntType(),new Id("v")),
                        new CompStatement(
                                new VarDeclaration(new RefType(new IntType()),new Id("a")),
                                new CompStatement(
                                        new CompStatement(
                                                new AssignStatement(
                                                        new Id("v"),
                                                        new ValueExpr(new IntValue(10))
                                                ),
                                                new PrintStatement(
                                                        new VariableExpr(new Id("v"))
                                                )
                                        ),
                                        new CompStatement(
                                                new NewStatement(
                                                        new Id("a"),
                                                        new ValueExpr(new IntValue(22))
                                                ),
                                                new CompStatement(
                                                        new ForkStatement(
                                                                new CompStatement(
                                                                        new WriteHeap(
                                                                                new Id("a"),
                                                                                new ValueExpr(new IntValue(30))
                                                                        ),
                                                                        new CompStatement(
                                                                                new AssignStatement(
                                                                                        new Id("v"),
                                                                                        new ValueExpr(new IntValue(32))
                                                                                ),
                                                                                new CompStatement(
                                                                                        new PrintStatement(
                                                                                                new VariableExpr(new Id("v"))
                                                                                        ),
                                                                                        new PrintStatement(
                                                                                                new RHExp(
                                                                                                        new VariableExpr(new Id("a"))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStatement(
                                                                new CompStatement(
                                                                        new CompStatement(new CompStatement(new Nop(),new Nop()),new Nop()),
                                                                        new PrintStatement(new VariableExpr(new Id("v")))
                                                                ),
                                                                new PrintStatement(
                                                                        new RHExp(new VariableExpr(new Id("a")))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );

    }

    private static Statement FaultyForkProgram1() {
        return
                new CompStatement(
                        new VarDeclaration(new IntType(),new Id("a")),
                        new CompStatement(
                                new VarDeclaration(new IntType(),new Id("b")),
                                new CompStatement(
                                        new ForkStatement(
                                                new CompStatement(
                                                        new AssignStatement(
                                                                new Id("a"),
                                                                new ValueExpr(new IntValue(30))
                                                        ),
                                                        new CompStatement(
                                                                new AssignStatement(
                                                                        new Id("b"),
                                                                        new ValueExpr(new IntValue(32))
                                                                ),
                                                                new CompStatement(
                                                                        new PrintStatement(
                                                                                new VariableExpr(new Id("a"))
                                                                        ),
                                                                        new VarDeclaration(new IntType(),new Id("hehe"))
                                                                )
                                                        )
                                                )
                                        ),
                                        new CompStatement(
                                                new CompStatement(
                                                        new CompStatement(new CompStatement(new Nop(),new Nop()),new Nop()),
                                                        new PrintStatement(new VariableExpr(new Id("hehe")))
                                                ),
                                                new PrintStatement(
                                                        new VariableExpr(new Id("a"))
                                                )
                                        )

                                )
                        )
                );

    }
}
