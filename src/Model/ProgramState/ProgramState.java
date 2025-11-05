package Model.ProgramState;

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
        str+="Execution Stack: \n"+executionStack.toString();
        str+="Symbol table: \n"+symTable.toString()+"\n";
        str+="Output list: \n"+output.toString();
        str+="-------------------------------------------";
        return str;
    }


}
