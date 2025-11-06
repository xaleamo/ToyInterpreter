package repository;

import model.programState.ProgramState;

public interface IRepository {
    ProgramState getCrtProgram();
    void logPrgStateExec();

    void reloadProgram();
}
