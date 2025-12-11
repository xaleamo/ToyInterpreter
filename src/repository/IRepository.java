package repository;

import model.program_state.PrgState;
import my_exceptions.FileException;

import java.util.List;

public interface IRepository {
    void logPrgStateExec(PrgState ps) throws FileException;

    void reloadProgram();

    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> t);
}
