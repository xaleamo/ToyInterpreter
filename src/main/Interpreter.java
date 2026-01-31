package main;

import model.statement.*;
import model.type.*;
import model.value.*;
import model.expression.*;
import view.TextMenu;
import view.command.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Interpreter {
    public static void createCommandsAndRunTextMenu() {


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

    public static Collection<IStmt> getAllPrograms(){
        Set<IStmt> allPrograms = new HashSet<>();

//        allPrograms.add(Program1());
//        allPrograms.add(Program2());
//        allPrograms.add(Program3());
        allPrograms.add(FaultyProgram1());
        allPrograms.add(FaultyProgram2());
//        allPrograms.add(FileProgram1());
//        allPrograms.add(HeapProgram1());
//        allPrograms.add(HeapProgram2());
//        allPrograms.add(HeapProgram3());
//        allPrograms.add(HeapProgram4());
//        allPrograms.add(HeapProgram5());
//        allPrograms.add(HeapProgram6());
//        allPrograms.add(WhileProgram1());
//        allPrograms.add(HeapReadInvalidProgram());
//        allPrograms.add(ForkProgram1());
//        allPrograms.add(ForkProgram2());
//        allPrograms.add(ForkProgram3());
//        allPrograms.add(FaultyForkProgram1());


        allPrograms.add(ForProgram1());

        return allPrograms;
    }

    ///
    ///// Ref int a; new(a,20);
    /////(for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a)));
    ///// print(rh(a))
    private static IStmt ForProgram1(){
        return
           new CompStmt(
                   new VarDeclStmt(new RefType(new IntType()),new Id("a")),
                   new CompStmt(
                           new New(new Id("a"),new ValueExp(new IntValue(20))),
                           new CompStmt(
                                   new ForStatement(new Id("v"), new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)),
                                           new ArithExp(new VariableExp(new Id("v")), ArithExp.Operator.ADD,new ValueExp(new IntValue(1))),
                                           new ForkStmt(
                                                   new CompStmt(
                                                           new PrintStmt(new VariableExp(new Id("v"))),
                                                           new AssignStmt(
                                                                   new Id("v"),
                                                                   new ArithExp(new VariableExp(new Id("v")), ArithExp.Operator.MULTIPLY, new RHExp(new VariableExp(new Id("a"))) )
                                                           )
                                                   )
                                           )
                                   ),
                                   new PrintStmt(new RHExp(new VariableExp(new Id("a"))))
                           )
                   )
           )
        ;
    }











    private static IStmt Program1() {   //int v; v=2;Print(v)
        return new CompStmt(
                new VarDeclStmt(new IntType(), new Id("v")),
                new CompStmt(
                        new AssignStmt(new Id("v"), new ValueExp(new IntValue(2))),
                        new PrintStmt(new VariableExp(new Id("v")))
                )
        );
    }

    private static IStmt Program2() {  //int a;int b; a=2+3*5;b=a+1;Print(b)
        return new CompStmt(
                new VarDeclStmt(new IntType(), new Id("a")),
                new CompStmt(
                        new VarDeclStmt(new IntType(), new Id("b")),
                        new CompStmt(
                                new AssignStmt(
                                        new Id("a"),
                                        new ArithExp(
                                                new ValueExp(new IntValue(2)),
                                                ArithExp.Operator.ADD, new ArithExp(
                                                        new ValueExp(new IntValue(3)),
                                                ArithExp.Operator.MULTIPLY, new ValueExp(new IntValue(5))
                                        )
                                        )
                                ),
                                new CompStmt(
                                        new AssignStmt(
                                                new Id("b"),
                                                new ArithExp(
                                                        new VariableExp(new Id("a")),
                                                        ArithExp.Operator.ADD, new ValueExp(new IntValue(1))
                                                )
                                        ),
                                        new PrintStmt(new VariableExp(new Id("b")))
                                )
                        )
                )
        );

    }

    private static IStmt Program3() {   //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        return new CompStmt(
                new VarDeclStmt(new BoolType(), new Id("a")),
                new CompStmt(
                        new VarDeclStmt(new IntType(), new Id("v")),
                        new CompStmt(
                                new AssignStmt(
                                        new Id("a"),
                                        new ValueExp(new BoolValue(true))
                                ),
                                new CompStmt(
                                        new IfStmt(
                                                new VariableExp(new Id("a")),
                                                new AssignStmt(
                                                        new Id("v"),
                                                        new ValueExp(new IntValue(2))
                                                ),
                                                new AssignStmt(
                                                        new Id("v"),
                                                        new ValueExp(new IntValue(3))
                                                )
                                        ),
                                        new PrintStmt(new VariableExp(new Id("v")))
                                )
                        )
                )
        );
    }

    /////assignment before declaration //a=10;print(a);
    private static IStmt FaultyProgram1() {
        return new CompStmt(
                new AssignStmt(
                        new Id("a"),
                        new ValueExp(new IntValue(10))
                ),
                new PrintStmt(new VariableExp(new Id("a")))
        );

    }

    /////type mismatch in assignment //int v; v= true;
    private static IStmt FaultyProgram2() {
        return new CompStmt(
                new VarDeclStmt(new IntType(), new Id("v")),
                new AssignStmt(
                        new Id("v"),
                        new ValueExp(new BoolValue(true))
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
    private static IStmt FileProgram1() {
    return new CompStmt(
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
}

    ///ALLOC TEST
    /// Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
    private static IStmt HeapProgram1(){
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()),new Id("v")),
                new CompStmt(
                        new New(new Id("v"),new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new RefType(new IntType())),new Id("a")),
                                new CompStmt(
                                        new New(new Id("a"),new VariableExp(new Id("v"))),
                                        new CompStmt(
                                                new PrintStmt(new VariableExp(new Id("v"))),
                                                new PrintStmt(new VariableExp(new Id("a")))
                                        )
                                )
                        )
                )
        );
    }
    /// READ HEAP TEST
    ////Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
    private static IStmt HeapProgram2(){
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), new Id("v")),
                new CompStmt(
                        new New(
                                new Id("v"),
                                new ValueExp(new IntValue(20))
                        ),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new RefType(new IntType())), new Id("a")),
                                new CompStmt(
                                        new New(
                                                new Id("a"),
                                                new VariableExp(new Id("v"))
                                        ),
                                        new CompStmt(
                                                new PrintStmt(
                                                        new RHExp(new VariableExp(new Id("v")))
                                                ),
                                                new PrintStmt(
                                                        new ArithExp(
                                                                new RHExp(
                                                                        new RHExp(new VariableExp(new Id("a")))
                                                                ),
                                                                ArithExp.Operator.ADD, new ValueExp(new IntValue(5))
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
    public static IStmt HeapProgram3(){
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), new Id("v")),
                new CompStmt(
                        new New(
                                new Id("v"),
                                new ValueExp(new IntValue(20))
                        ),
                        new CompStmt(
                                new PrintStmt(
                                        new RHExp(new VariableExp(new Id("v")))
                                ),
                                new CompStmt(
                                        new WHStmt(
                                                new Id("v"),
                                                new ValueExp(new IntValue(30))
                                        ),
                                        new PrintStmt(
                                                new ArithExp(
                                                        new RHExp(new VariableExp(new Id("v"))),
                                                        ArithExp.Operator.ADD, new ValueExp(new IntValue(5))
                                                )
                                        )
                                )
                        )
                )
        );

    }

    /// Should throw error when executing with unsafe garbage collector
    /// Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
    private static IStmt HeapProgram4() {
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), new Id("v")),
                new CompStmt(
                        new New(
                                new Id("v"),
                                new ValueExp(new IntValue(20))
                        ),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new RefType(new IntType())), new Id("a")),
                                new CompStmt(
                                        new New(
                                                new Id("a"),
                                                new VariableExp(new Id("v"))
                                        ),
                                        new CompStmt(
                                                new New(      // new(v,30)
                                                        new Id("v"),
                                                        new ValueExp(new IntValue(30))
                                                ),
                                                new PrintStmt(
                                                        new RHExp(
                                                                new RHExp(new VariableExp(new Id("a")))
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
    private static IStmt HeapProgram5() {
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), new Id("v")),
                new CompStmt(
                        new New(new Id("v"), new ValueExp(new IntValue(20))),   // v -> cell1
                        new CompStmt(
                                new VarDeclStmt(new RefType(new RefType(new IntType())), new Id("a")),
                                new CompStmt(
                                        new New(new Id("a"), new VariableExp(new Id("v"))), // a -> cell2, storing address1
                                        new CompStmt(
                                                new New(new Id("v"), new ValueExp(new IntValue(999))), // v -> NEW cell3
                                                new CompStmt(
                                                        new WHStmt(
                                                                new Id("a"),
                                                                new ValueExp(
                                                                        new RefValue(
                                                                                2,               // BAD address (not in heap)
                                                                                new IntType()
                                                                        )
                                                                )
                                                        ),
                                                        new PrintStmt(
                                                                new RHExp(
                                                                        new RHExp(
                                                                                new VariableExp(new Id("a"))
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
    private static IStmt HeapProgram6() {
        return new CompStmt(
            new VarDeclStmt(
                new RefType(new IntType()),
                new Id("aux")
            ),
            new CompStmt(
                new VarDeclStmt(
                    new RefType(new RefType(new IntType())),
                    new Id("a")
                ),
                new CompStmt(
                    new VarDeclStmt(
                        new RefType(new RefType(new IntType())),
                        new Id("v")
                    ),
                    new CompStmt(
                        new New(new Id("aux"),new ValueExp(new IntValue(100))),
                        new CompStmt(
                            new New(new Id("a"),new VariableExp(new Id("aux"))),
                            new CompStmt(
                                new New(new Id("v"),new ValueExp(new RefValue(2,new IntType()))),
                                new CompStmt(
                                    new New(new Id("a"),new VariableExp(new Id("v"))),
                                    new PrintStmt(new RHExp(new VariableExp(new Id("v"))))
                                )
                            )
                        )
                    )
                )
            )
        );
    }

    private static IStmt WhileProgram1() {
        return new CompStmt(
                new VarDeclStmt(new IntType(),new Id("v")),
                new CompStmt(
                        new AssignStmt(
                                new Id("v"),
                                new ValueExp(new IntValue(4))
                        ),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExp(
                                                new VariableExp(new Id("v")),
                                                RelationalExp.Operator.GREATER,
                                                new ValueExp(new IntValue(0))

                                        ),
                                        new CompStmt(
                                                new PrintStmt(new VariableExp(new Id("v"))),
                                                new AssignStmt(
                                                        new Id("v"),
                                                        new ArithExp(
                                                                new VariableExp(new Id("v")),
                                                                ArithExp.Operator.SUBTRACT,
                                                                new ValueExp(new IntValue(1))
                                                        )
                                                )
                                        )
                                ),
                                new PrintStmt(new VariableExp(new Id("v")))
                        )
                )
        );

    }

    private static IStmt HeapReadInvalidProgram() {
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), new Id("v")),
                new CompStmt(
                        new New(
                                new Id("v"),
                                new ValueExp(new IntValue(20))
                        ),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new RefType(new IntType())), new Id("a")),
                                new CompStmt(
                                        new New(
                                                new Id("a"),
                                                new VariableExp(new Id("v"))
                                        ),
                                        new CompStmt(
                                                new PrintStmt(
                                                        new RHExp(new VariableExp(new Id("v")))
                                                ),
                                                new PrintStmt(
                                                        new ArithExp(
                                                                new RHExp(
                                                                        new RHExp(new ValueExp(new RefValue(1,new RefType(new IntType()))))
                                                                ),
                                                                ArithExp.Operator.ADD, new ValueExp(new IntValue(5))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    //normal
    private static IStmt ForkProgram1() {
        return
                new CompStmt(
                        new VarDeclStmt(new IntType(),new Id("v")),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new IntType()),new Id("a")),
                                new CompStmt(
                                        new AssignStmt(
                                                new Id("v"),
                                                new ValueExp(new IntValue(10))
                                        ),
                                        new CompStmt(
                                                new New(
                                                        new Id("a"),
                                                        new ValueExp(new IntValue(22))
                                                ),
                                                new CompStmt(
                                                        new ForkStmt(
                                                                new CompStmt(
                                                                        new WHStmt(
                                                                                new Id("a"),
                                                                                new ValueExp(new IntValue(30))
                                                                        ),
                                                                        new CompStmt(
                                                                                new AssignStmt(
                                                                                        new Id("v"),
                                                                                        new ValueExp(new IntValue(32))
                                                                                ),
                                                                                new CompStmt(
                                                                                        new PrintStmt(
                                                                                                new VariableExp(new Id("v"))
                                                                                        ),
                                                                                        new PrintStmt(
                                                                                                new RHExp(
                                                                                                        new VariableExp(new Id("a"))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStmt(
                                                                new PrintStmt(new VariableExp(new Id("v"))),
                                                                new PrintStmt(
                                                                        new RHExp(new VariableExp(new Id("a")))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );

    }

    //...22...
    private static IStmt ForkProgram2() {
        return
                new CompStmt(
                        new VarDeclStmt(new IntType(),new Id("v")),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new IntType()),new Id("a")),
                                new CompStmt(
                                        new AssignStmt(
                                                new Id("v"),
                                                new ValueExp(new IntValue(10))
                                        ),
                                        new CompStmt(
                                                new New(
                                                        new Id("a"),
                                                        new ValueExp(new IntValue(22))
                                                ),
                                                new CompStmt(
                                                        new ForkStmt(
                                                                new CompStmt(
                                                                        new CompStmt(
                                                                                new PrintStmt(new VariableExp(new Id("a"))),
                                                                                new WHStmt(
                                                                                        new Id("a"),
                                                                                        new ValueExp(new IntValue(30))
                                                                                )
                                                                        ),
                                                                        new CompStmt(
                                                                                new AssignStmt(
                                                                                        new Id("v"),
                                                                                        new ValueExp(new IntValue(32))
                                                                                ),
                                                                                new CompStmt(
                                                                                        new PrintStmt(
                                                                                                new VariableExp(new Id("v"))
                                                                                        ),
                                                                                        new PrintStmt(
                                                                                                new RHExp(
                                                                                                        new VariableExp(new Id("a"))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStmt(
                                                                new PrintStmt(new VariableExp(new Id("v"))),
                                                                new PrintStmt(
                                                                        new RHExp(new VariableExp(new Id("a")))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );

    }

    //10...10
    private static IStmt ForkProgram3() {
        return
                new CompStmt(
                        new VarDeclStmt(new IntType(),new Id("v")),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new IntType()),new Id("a")),
                                new CompStmt(
                                        new CompStmt(
                                                new AssignStmt(
                                                        new Id("v"),
                                                        new ValueExp(new IntValue(10))
                                                ),
                                                new PrintStmt(
                                                        new VariableExp(new Id("v"))
                                                )
                                        ),
                                        new CompStmt(
                                                new New(
                                                        new Id("a"),
                                                        new ValueExp(new IntValue(22))
                                                ),
                                                new CompStmt(
                                                        new ForkStmt(
                                                                new CompStmt(
                                                                        new WHStmt(
                                                                                new Id("a"),
                                                                                new ValueExp(new IntValue(30))
                                                                        ),
                                                                        new CompStmt(
                                                                                new AssignStmt(
                                                                                        new Id("v"),
                                                                                        new ValueExp(new IntValue(32))
                                                                                ),
                                                                                new CompStmt(
                                                                                        new PrintStmt(
                                                                                                new VariableExp(new Id("v"))
                                                                                        ),
                                                                                        new PrintStmt(
                                                                                                new RHExp(
                                                                                                        new VariableExp(new Id("a"))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStmt(
                                                                new CompStmt(
                                                                        new CompStmt(new CompStmt(new NopStmt(),new NopStmt()),new NopStmt()),
                                                                        new PrintStmt(new VariableExp(new Id("v")))
                                                                ),
                                                                new PrintStmt(
                                                                        new RHExp(new VariableExp(new Id("a")))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );

    }

    private static IStmt FaultyForkProgram1() {
        return
                new CompStmt(
                        new VarDeclStmt(new IntType(),new Id("a")),
                        new CompStmt(
                                new VarDeclStmt(new IntType(),new Id("b")),
                                new CompStmt(
                                        new ForkStmt(
                                                new CompStmt(
                                                        new AssignStmt(
                                                                new Id("a"),
                                                                new ValueExp(new IntValue(30))
                                                        ),
                                                        new CompStmt(
                                                                new AssignStmt(
                                                                        new Id("b"),
                                                                        new ValueExp(new IntValue(32))
                                                                ),
                                                                new CompStmt(
                                                                        new PrintStmt(
                                                                                new VariableExp(new Id("a"))
                                                                        ),
                                                                        new VarDeclStmt(new IntType(),new Id("hehe"))
                                                                )
                                                        )
                                                )
                                        ),
                                        new CompStmt(
                                                new CompStmt(
                                                        new CompStmt(new CompStmt(new NopStmt(),new NopStmt()),new NopStmt()),
                                                        new PrintStmt(new VariableExp(new Id("hehe")))
                                                ),
                                                new PrintStmt(
                                                        new VariableExp(new Id("a"))
                                                )
                                        )

                                )
                        )
                );

    }
}
