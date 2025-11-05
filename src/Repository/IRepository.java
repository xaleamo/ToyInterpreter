package Repository;

import Model.ProgramState.ProgramState;

public interface IRepository {
    //public void AddProgram(ProgramState programState);
    //public void ExecuteAll(ProgramState programState);
    ProgramState getCrtProgram(int i);
    void logPrgStateExec(int i);
}
