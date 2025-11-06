package model.programState;

import model.programState.ADTs.FileTableGeneric;
import model.value.StringValue;
import myExceptions.FileException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class FileTable extends FileTableGeneric<StringValue, BufferedReader> implements Cloneable {

    @Override
    public FileTable clone() {
        FileTable f = new FileTable();
        try {
            for (var e : table.entrySet()) {
                f.table.put(e.getKey().clone(), new BufferedReader(new FileReader(e.getKey().getValue())));
            }
        }catch(FileNotFoundException e){
            throw new FileException("File not found.");
        }
        return f;
    }
}
