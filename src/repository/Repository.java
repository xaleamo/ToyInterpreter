package repository;

import my_exceptions.FileException;
import my_exceptions.RepositoryException;
import model.program_state.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private ArrayList<PrgState> threads = new ArrayList<PrgState>();
    private String logFilePath;
    public Repository(PrgState programState, String logFilePath) {
        this.threads.addFirst(programState);
        this.logFilePath = logFilePath;
    }

    /**
     *
     * @return corresponding ProgramState
     * @throws RepositoryException if index is out of bounds/program does not exist
     */


    private void addProgram(PrgState p){
        threads.add(p);
    }

    @Override
    public void logPrgStateExec(PrgState ps) throws FileException {
        //TODO manage which file it saves to
        PrintWriter pw=null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));//creates new file if one is not found
            pw.write(ps.toString());
            pw.println();
        }
        catch(IOException e) {
            throw new FileException("File not found.");
        }
        finally{
            if(pw!=null) pw.close();
        }
    }

    @Override
    public void reloadProgram(){
        threads.getFirst().reload();
    }

    @Override
    public List<PrgState> getPrgList(){
        return threads;
    }
    @Override
    public void setPrgList(List<PrgState> t){
        threads.clear();
        threads.addAll(t);
    }

}
