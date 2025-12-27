package model.program_state.ADTs;

import java.util.ArrayList;

public interface MyIList <T>{
    public void add(T v);
    public T remove(int index);

    ArrayList<T> getContent();
}
