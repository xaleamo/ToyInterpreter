package model.program_state.ADTs;

import my_exceptions.ProgramStateException;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T>{
    protected ArrayList<T> list=new ArrayList<>();

    @Override
    public void add(T v) {
        list.add(v);
    }

    /**
     Removes the element at the specified position in this list. Shifts any subsequent elements to the left (subtracts one from their indices).
     @param index the index of the element to be removed
     @return the element that was removed from the list
     @throws my_exceptions.ProgramStateException if index is out of bounds
     */
    @Override
    public T remove(int index) {
        try {
            return list.remove(index);
        }catch(IndexOutOfBoundsException e) {
            throw new ProgramStateException("Index Out of Bounds");
        }
    }

    @Override
    public String toString() {
        StringBuilder rez= new StringBuilder();
        for(T v:list){
            rez.append(v.toString()).append("\n");
        }
        return rez.toString();
    }
    @Override
    public ArrayList<T> getContent(){return list;}
}
