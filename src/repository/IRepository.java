package repository;

import model.programState.ProgramState;

public interface IRepository {
    //public void AddProgram(ProgramState programState);
    //public void ExecuteAll(ProgramState programState);
    ProgramState getCrtProgram();
    void logPrgStateExec();
}
