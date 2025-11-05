package Controller;

import Model.ProgramState.ProgramState;
import Model.Statement.Statement;
import Repository.IRepository;

public class Service {
    IRepository repo;
    ProgramState state;
    boolean displayFlag=false;

    public Service(IRepository repo) {
        this.repo = repo;
    }

    public void setDisplayFlag(boolean b) {displayFlag = b;}

    /**
     * Sets the program state of the service to the <i>i</i>th program state in repository <i>repo</i>
     * @param i integer >=0
     * @throws MyExceptions.RepositoryException if program <i>i</i> does not exist (indexing starts from 0)
     */
    public void setProgramState(int i) {
        this.state=repo.getCrtProgram(i);
    }

    /**
     * Executes the statement at the top of the <i>ExecutionStack</i> of <i>state</i>.
     * @throws MyExceptions.ProgramStateException if ExecutionStack is empty.
     */
    public void executeOneStep(){
        Statement s = state.getExecutionStack().pop();
        s.execute(state);
    }

    public void printProgramState(){
        System.out.println(state.toString());
    }

    public void executeAll(){
        printProgramState();
        repo.logPrgStateExec(state);
        while(!state.getExecutionStack().isEmpty()){
            executeOneStep();
            repo.logPrgStateExec(state);
            if(displayFlag){
                String s=state.toString();
                System.out.println(s);
            }
        }
    }


}
