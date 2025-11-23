package repository;

import model.program_state.ProgramState;

public interface IRepository {
    ProgramState getCrtProgram();
    void logPrgStateExec();

    void reloadProgram();
}
