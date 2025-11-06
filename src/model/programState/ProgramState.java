package model.programState;

public class ProgramState {
    ExecutionStack executionStack;
    SymTable symTable;
    Output output;
    FileTable fileTable;


    public ProgramState(ExecutionStack executionStack, SymTable symTable, Output output) {
        this.executionStack = executionStack;
        this.symTable = symTable;
        this.output = output;
        this.fileTable = new FileTable();
    }

    public ExecutionStack getExecutionStack() {return  executionStack;}
    public SymTable getSymTable() {return symTable;}
    public Output getOutput() {return output;}
    public FileTable getFileTable() {return fileTable;}

    public void setExecutionStack(ExecutionStack executionStack) {this.executionStack = executionStack;}
    public void setSymTable(SymTable symTable) {this.symTable = symTable;}
    public void setOutput(Output output) {this.output = output;}
    public void setFileTable(FileTable fileTable) {this.fileTable = fileTable;}

    @Override
    public String toString() {
        String str="";
        str+="\033[0;35mExecution Stack:\033[0m \n"+executionStack.toString();
        str+="\033[0;35mSymbol table:\033[0m \n"+symTable.toString()+"\n";
        str+="\033[0;35mOutput list:\033[0m \n"+output.toString();
        str+="\033[0;34m-------------------------------------------\033[0m";
        return str;
    }


}
