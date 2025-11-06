package model.programState;


public class ProgramState implements Cloneable {
    ExecutionStack executionStack;
    SymTable symTable;
    Output output;
    FileTable fileTable;
    ExecutionStack original;

    public ProgramState(ExecutionStack executionStack, SymTable symTable, Output output) {
        this.executionStack = executionStack;
        this.symTable = symTable;
        this.output = output;
        this.fileTable = new FileTable();
        original = executionStack.clone();
    }

    @Override
    public ProgramState clone(){
        ProgramState newProg= new ProgramState(new ExecutionStack(),new SymTable(),new Output());
        newProg.executionStack = executionStack.clone();
        newProg.symTable = symTable.clone();
        newProg.output = output.clone();
        newProg.fileTable = fileTable.clone();
        newProg.original = executionStack.clone();
        return newProg;
    }

    public ExecutionStack getExecutionStack() {return  executionStack;}
    public SymTable getSymTable() {return symTable;}
    public Output getOutput() {return output;}
    public FileTable getFileTable() {return fileTable;}

    public void setExecutionStack(ExecutionStack executionStack) {this.executionStack = executionStack;}
    public void setSymTable(SymTable symTable) {this.symTable = symTable;}
    public void setOutput(Output output) {this.output = output;}
    //public void setFileTable(FileTable fileTable) {this.fileTable = fileTable;}

    @Override
    public String toString() {
        String str="";
        str+="Execution Stack: \n"+executionStack.toString();
        str+="Symbol table: \n"+symTable.toString();
        str+="Output list: \n"+output.toString();
        str+="File table: \n"+fileTable.toString();
        str+="-------------------------------------------";
        return str;
    }

    public String toString(String color){
        String str="";
        str+="\033[0;35mExecution Stack:\033[0m \n"+executionStack.toString();
        str+="\033[0;35mSymbol table:\033[0m \n"+symTable.toString()+"\n";
        str+="\033[0;35mOutput list:\033[0m \n"+output.toString();
        str+="\033[0;35mFile table:\033[0m \n"+fileTable.toString();
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
