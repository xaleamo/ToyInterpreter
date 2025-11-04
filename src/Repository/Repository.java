package Repository;

import MyExceptions.RepositoryException;
import Model.ProgramState.*;

import java.util.ArrayList;

public class Repository implements IRepository {
    ArrayList<ProgramState> programState = new ArrayList<ProgramState>();

    public Repository() {}

    /**
     *
     * @param i index
     * @return corresponding ProgramState
     * @throws RepositoryException if index is out of bounds/program does not exist
     */
    @Override
    public ProgramState GetCrtProgram(int i) {
        try{
            return programState.get(i);
        }
        catch(IndexOutOfBoundsException e){
            throw new RepositoryException("Program does not exist.");
        }
    }

    public void addProgram(ProgramState p){
        programState.add(p);
    }

}
