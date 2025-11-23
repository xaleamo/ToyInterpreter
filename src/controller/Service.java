package controller;

import model.program_state.ProgramState;
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
     * @throws my_exceptions.ProgramStateException if ExecutionStack is empty.
     */
    public void executeOneStep(){//this does not log stuff
        Statement s = state.getExecutionStack().pop();
        s.execute(state);
    }

    public void printProgramState(){
        System.out.println(state.toString("color"));
    }

    public void executeUntilEnd(){
        printProgramState();
        repo.logPrgStateExec();
        while(!state.getExecutionStack().isEmpty()){
            executeOneStep();
            repo.logPrgStateExec();
            if(displayFlag) printProgramState();
        }
    }
    public void executeAll(){//from start
        repo.reloadProgram();
        executeUntilEnd();
    }


}
