package Repository;

import MyExceptions.FileException;
import MyExceptions.RepositoryException;
import Model.ProgramState.*;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Repository implements IRepository {
    private ArrayList<ProgramState> programState = new ArrayList<ProgramState>();
    private String logFilePath;
    public Repository() {
        readLogFilePath();
    }

    /**
     *
     * @return corresponding ProgramState
     * @throws RepositoryException if index is out of bounds/program does not exist
     */
    @Override
    public ProgramState getCrtProgram() {
        try{
            return programState.getFirst();
        }
        catch(NoSuchElementException e){
            throw new RepositoryException("Program does not exist.");
        }
    }

    public void addProgram(ProgramState p){
        programState.add(p);
    }

    @Override
    public void logPrgStateExec(ProgramState ps) {
        try {
            if (ps == null) ps = programState.getFirst();
        }catch(NoSuchElementException e){
            throw new RepositoryException("Program does not exist.");
        }

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

    private void readLogFilePath(){//should move in UI
        System.out.println("Enter Log File Path");
        Scanner sc = new Scanner(System.in);
        logFilePath= sc.nextLine();
    }

}
