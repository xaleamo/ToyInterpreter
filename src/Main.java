//import controller.Service;
//import model.expression.*;
//import model.programState.*;
//import model.statement.*;
//import model.type.*;
//import model.value.*;
//import repository.Repository;
//import view.TextMenu;
//
//
//void main() {
//    Repository repo = new Repository();
//    Service service = new Service(repo);
//    TextMenu textMenu = new TextMenu(service);
//
//    addPredefinedPrograms(repo);
//
//    textMenu.run();
//}
//
//private void addPredefinedPrograms(Repository repo) {
//    repo.addProgram(Program0());
//    repo.addProgram(Program1());
//    repo.addProgram(Program2());
//    repo.addProgram(Program3());
//
//    repo.addProgram(FaultyProgram1());
//    repo.addProgram(FaultyProgram2());
//    repo.addProgram(FaultyProgram3());
//    repo.addProgram(FaultyProgram4());
//    repo.addProgram(FaultyProgram5());
//    repo.addProgram(FaultyProgram6());
//
//    repo.addProgram(FileProgram1());
//    repo.addProgram(InequalityProgram1());
//
//}
//
//private ProgramState Program0() {   //int a; int b; a=10;b=20; print(a);print(b);print(a+b);
//    ExecutionStack executionStack = new ExecutionStack();
//    ProgramState p = new ProgramState(executionStack, new SymTable(), new Output());
//
//    ArrayList<Statement> statements = new ArrayList<>();
//    statements.add(new VarDeclaration(new IntType(), new Id("a")));
//    statements.add(new VarDeclaration(new IntType(), new Id("b")));
//    statements.add(new AssignStatement(new Id("a"), new ValueExpr(new IntValue(10))));
//    statements.add(new AssignStatement(new Id("b"), new ValueExpr(new IntValue(20))));
//    statements.add(new PrintStatement(new VariableExpr(new Id("a"))));
//    statements.add(new PrintStatement(new VariableExpr(new Id("b"))));
//    statements.add(new PrintStatement(new ArithExpr(new VariableExpr(new Id("a")), new VariableExpr(new Id("b")), ArithExpr.Operator.ADD)));
//
//    for (int i = statements.size() - 1; i >= 0; i--) {
//        executionStack.push(statements.get(i));
//    }
//    return p;
//}
//
//private ProgramState Program1() {   //int v; v=2;Print(v)
//    Statement s1 = new CompStatement(
//            new VarDeclaration(new IntType(), new Id("v")),
//            new CompStatement(
//                    new AssignStatement(new Id("v"), new ValueExpr(new IntValue(2))),
//                    new PrintStatement(new VariableExpr(new Id("v")))
//            )
//    );
//
//
//    ExecutionStack executionStack = new ExecutionStack();
//    executionStack.push(s1);
//
//    return new ProgramState(executionStack, new SymTable(), new Output());
//}
//
//private ProgramState Program2() {  //int a;int b; a=2+3*5;b=a+1;Print(b)
//    Statement s2 = new CompStatement(
//            new VarDeclaration(new IntType(), new Id("a")),
//            new CompStatement(
//                    new VarDeclaration(new IntType(), new Id("b")),
//                    new CompStatement(
//                            new AssignStatement(
//                                    new Id("a"),
//                                    new ArithExpr(
//                                            new ValueExpr(new IntValue(2)),
//                                            new ArithExpr(
//                                                    new ValueExpr(new IntValue(3)),
//                                                    new ValueExpr(new IntValue(5)),
//                                                    ArithExpr.Operator.MULTIPLY
//                                            ),
//                                            ArithExpr.Operator.ADD
//                                    )
//                            ),
//                            new CompStatement(
//                                    new AssignStatement(
//                                            new Id("b"),
//                                            new ArithExpr(
//                                                    new VariableExpr(new Id("a")),
//                                                    new ValueExpr(new IntValue(1)),
//                                                    ArithExpr.Operator.ADD
//                                            )
//                                    ),
//                                    new PrintStatement(new VariableExpr(new Id("b")))
//                            )
//                    )
//            )
//    );
//    ExecutionStack executionStack = new ExecutionStack();
//    executionStack.push(s2);
//    return new ProgramState(executionStack, new SymTable(), new Output());
//}
//
//private ProgramState Program3() {   //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
//    Statement s3 = new CompStatement(
//            new VarDeclaration(new BoolType(), new Id("a")),
//            new CompStatement(
//                    new VarDeclaration(new IntType(), new Id("v")),
//                    new CompStatement(
//                            new AssignStatement(
//                                    new Id("a"),
//                                    new ValueExpr(new BoolValue(true))
//                            ),
//                            new CompStatement(
//                                    new IfStatement(
//                                            new VariableExpr(new Id("a")),
//                                            new AssignStatement(
//                                                    new Id("v"),
//                                                    new ValueExpr(new IntValue(2))
//                                            ),
//                                            new AssignStatement(
//                                                    new Id("v"),
//                                                    new ValueExpr(new IntValue(3))
//                                            )
//                                    ),
//                                    new PrintStatement(new VariableExpr(new Id("v")))
//                            )
//                    )
//            )
//    );
//    ExecutionStack executionStack = new ExecutionStack();
//    executionStack.push(s3);
//    return new ProgramState(executionStack, new SymTable(), new Output());
//}
//
///////assignment before declaration //a=10;print(a);
//private ProgramState FaultyProgram1() {
//    Statement ex1 = new CompStatement(
//            new AssignStatement(
//                    new Id("a"),
//                    new ValueExpr(new IntValue(10))
//            ),
//            new PrintStatement(new VariableExpr(new Id("a")))
//    );
//    ExecutionStack executionStack = new ExecutionStack();
//    executionStack.push(ex1);
//    return new ProgramState(executionStack, new SymTable(), new Output());
//}
//
///////type mismatch in assignment //int v; v= true;
//private ProgramState FaultyProgram2() {
//    Statement ex2 = new CompStatement(
//            new VarDeclaration(new IntType(), new Id("v")),
//            new AssignStatement(
//                    new Id("v"),
//                    new ValueExpr(new BoolValue(true))
//            )
//    );
//    ExecutionStack executionStack = new ExecutionStack();
//    executionStack.push(ex2);
//    return new ProgramState(executionStack, new SymTable(), new Output());
//}
//
///////division by zero //int x; x=5/0;print(x);
//private ProgramState FaultyProgram3() {
//    Statement ex3 = new CompStatement(
//            new VarDeclaration(new IntType(), new Id("x")),
//            new CompStatement(
//                    new AssignStatement(
//                            new Id("x"),
//                            new ArithExpr(
//                                    new ValueExpr(new IntValue(5)),
//                                    new ValueExpr(new IntValue(0)),
//                                    ArithExpr.Operator.DIVIDE
//                            )
//                    ),
//                    new PrintStatement(new VariableExpr(new Id("x")))
//            )
//    );
//    ExecutionStack executionStack = new ExecutionStack();
//    executionStack.push(ex3);
//    return new ProgramState(executionStack, new SymTable(), new Output());
//}
//
///////undeclared variable in logic expression //if(b) then v=1 else v=2;
//private ProgramState FaultyProgram4() {
//    Statement ex2 = new CompStatement(
//            new VarDeclaration(new IntType(), new Id("v")),
//            new IfStatement(
//                    new VariableExpr(new Id("b")),  // 'b' is undeclared
//                    new AssignStatement(new Id("v"), new ValueExpr(new IntValue(1))),
//                    new AssignStatement(new Id("v"), new ValueExpr(new IntValue(2)))
//            )
//    );
//    ExecutionStack executionStack = new ExecutionStack();
//    executionStack.push(ex2);
//    return new ProgramState(executionStack, new SymTable(), new Output());
//}
//
///////type mismatch //int a; bool b; a=true+5; print(a);
//private ProgramState FaultyProgram5() {
//    Statement ex6 = new CompStatement(
//            new VarDeclaration(new IntType(), new Id("a")),
//            new CompStatement(
//                    new VarDeclaration(new BoolType(), new Id("b")),
//                    new CompStatement(
//                            new AssignStatement(
//                                    new Id("a"),
//                                    new ArithExpr(
//                                            new ValueExpr(new BoolValue(true)),
//                                            new ValueExpr(new IntValue(5)),
//                                            ArithExpr.Operator.ADD
//                                    )
//                            ),
//                            new PrintStatement(new VariableExpr(new Id("a")))
//                    )
//            )
//    );
//    ExecutionStack executionStack = new ExecutionStack();
//    executionStack.push(ex6);
//    return new ProgramState(executionStack, new SymTable(), new Output());
//}
//
///////invalid if condition type //int a; a=10; if(a) then print(1) else print(0);
//private ProgramState FaultyProgram6() {
//    Statement ex7 = new CompStatement(
//            new VarDeclaration(new IntType(), new Id("a")),
//            new CompStatement(
//                    new AssignStatement(new Id("a"), new ValueExpr(new IntValue(10))),
//                    new IfStatement(
//                            new VariableExpr(new Id("a")), // condition must be boolean
//                            new PrintStatement(new ValueExpr(new IntValue(1))),
//                            new PrintStatement(new ValueExpr(new IntValue(0)))
//                    )
//            )
//    );
//    ExecutionStack executionStack = new ExecutionStack();
//    executionStack.push(ex7);
//    return new ProgramState(executionStack, new SymTable(), new Output());
//}
//
///////open, read twice and close file
/////string varf;
///// varf="test.in";
///// openRFile(varf);
///// int varc;
///// readFile(varf,varc);print(varc);
///// readFile(varf,varc);print(varc)
///// closeRFile(varf)
//private ProgramState FileProgram1() {
//    Statement s1 = new CompStatement(
//            new VarDeclaration(new StringType(), new Id("varf")),
//            new CompStatement(
//                    new AssignStatement(new Id("varf"), new ValueExpr(new StringValue("Files/test.txt"))),
//                    new CompStatement(
//                            new OpenRFile(new VariableExpr(new Id("varf"))),
//                            new CompStatement(
//                                    new VarDeclaration(new IntType(), new Id("varc")),
//                                    new CompStatement(
//                                            new ReadFile(new VariableExpr(new Id("varf")), new Id("varc")),
//                                            new CompStatement(
//                                                    new PrintStatement(new VariableExpr(new Id("varc"))),
//                                                    new CompStatement(
//                                                            new ReadFile(new VariableExpr(new Id("varf")), new Id("varc")),
//                                                            new CompStatement(
//                                                                    new PrintStatement(new VariableExpr(new Id("varc"))),
//                                                                    new CloseRFile(new VariableExpr(new Id("varf")))
//                                                            )
//                                                    )
//                                            )
//                                    )
//                            )
//                    )
//            )
//    );
//
//    //Statement s2= new CloseRFile(new VariableExpr(new Id("varf")));
//
//    ExecutionStack executionStack = new ExecutionStack();
//    //executionStack.push(s2);
//    executionStack.push(s1);
//    return new ProgramState(executionStack, new SymTable(), new Output());
//
//}
//
//private ProgramState InequalityProgram1() {
//    Statement s2 = new CompStatement(
//            new PrintStatement(new RelationalExpr(
//                    new ValueExpr(new IntValue(10)),
//                    new ValueExpr(new IntValue(10)),
//                    RelationalExpr.Operator.EQUAL
//            )),
//            new PrintStatement(new RelationalExpr(
//                    new ValueExpr(new IntValue(10)),
//                    new ValueExpr(new BoolValue(true)),
//                    RelationalExpr.Operator.LESS_OR_EQUAL
//            ))
//    );
//    ExecutionStack executionStack = new ExecutionStack();
//    executionStack.push(s2);
//    return new ProgramState(executionStack, new SymTable(), new Output());
//}
