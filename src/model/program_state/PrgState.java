package model.program_state;


import model.statement.Statement;
import my_exceptions.MyException;

import java.util.concurrent.atomic.AtomicInteger;

public class PrgState implements Cloneable {
    private ExecutionStack executionStack;
    private SymTable symTable;
    private Output output;
    private FileTable fileTable;
    private Heap heap;
    private ExecutionStack original;
    private static AtomicInteger lastid=new AtomicInteger(-1);
    private final Integer id;

    public PrgState(ExecutionStack executionStack, SymTable symTable, Heap heap, Output output, FileTable fileTable) {
        this.executionStack = executionStack;
        this.symTable = symTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        original = executionStack.clone();
        id=lastid.incrementAndGet();
    }

    @Override
    public PrgState clone(){
        PrgState newProg= new PrgState(new ExecutionStack(),new SymTable(), new Heap(), new Output(), new FileTable());
        newProg.executionStack = executionStack.clone();
        newProg.symTable = symTable.clone();
        newProg.output = output.clone();
        newProg.fileTable = fileTable.clone();
        newProg.heap=heap.clone();
        newProg.original = executionStack.clone();
        return newProg;
    }

    public ExecutionStack getExecutionStack() {return  executionStack;}
    public SymTable getSymTable() {return symTable;}
    public Output getOutput() {return output;}
    public FileTable getFileTable() {return fileTable;}
    public Heap getHeap() {return heap;}
    public int getId(){return id;}


    public PrgState oneStep() throws MyException {
        if(executionStack.isEmpty()) throw new MyException("prgstate stack is empty");
        Statement crtStmt = executionStack.pop();
        return crtStmt.execute(this);
    }

    public Boolean isNotCompleted(){
        return !executionStack.isEmpty();
    }

//    public void setExecutionStack(ExecutionStack executionStack) {this.executionStack = executionStack;}
//    public void setSymTable(SymTable symTable) {this.symTable = symTable;}
//    public void setOutput(Output output) {this.output = output;}
    //public void setFileTable(FileTable fileTable) {this.fileTable = fileTable;}

    @Override
    public String toString() {
        String str=id.toString()+'\n';
        str+="Execution Stack: \n"+executionStack.toString();
        str+="Symbol table: \n"+symTable.toString();
        str+="Output list: \n"+output.toString();
        str+="File table: \n"+fileTable.toString();
        str+="Heap: \n"+heap.toString();
        str+="-------------------------------------------";
        return str;
    }

    public String toString(String color){
        String str=id.toString()+'\n';
        str+="\033[0;35mExecution Stack:\033[0m \n"+executionStack.toString();
        str+="\033[0;35mSymbol table:\033[0m \n"+symTable.toString()+"\n";
        str+="\033[0;35mOutput list:\033[0m \n"+output.toString();
        str+="\033[0;35mFile table:\033[0m \n"+fileTable.toString();
        str+="\033[0;35mHeap:\033[0m \n"+heap.toString();
        str+="\033[0;34m-------------------------------------------\033[0m";
        return str;
    }

    public void reload(){
        symTable=new SymTable();
        output=new Output();
        fileTable=new FileTable();
        executionStack=original.clone();
    }
}
