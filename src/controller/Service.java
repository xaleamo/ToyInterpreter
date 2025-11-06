package controller;

import model.programState.ProgramState;
import model.statement.Statement;
import repository.IRepository;

public class Service {
    IRepository repo;
    ProgramState state;
    boolean displayFlag=false;

    public Service(IRepository repo) {
        this.repo = repo;
        state=repo.getCrtProgram();
    }

    public void setDisplayFlag(boolean b) {displayFlag = b;}


    /**
     * Executes the statement at the top of the <i>ExecutionStack</i> of <i>state</i>.
     * @throws myExceptions.ProgramStateException if ExecutionStack is empty.
     */
    public void executeOneStep(){
        Statement s = state.getExecutionStack().pop();
        s.execute(state);
    }

    public void printProgramState(){
        System.out.println(state.toString("color"));
    }

    public void executeAll(){
        repo.reloadProgram();
        printProgramState();
        repo.logPrgStateExec();
        while(!state.getExecutionStack().isEmpty()){
            executeOneStep();
            repo.logPrgStateExec();
            if(displayFlag) printProgramState();
        }
    }


}
