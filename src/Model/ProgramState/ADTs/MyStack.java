package Model.ProgramState.ADTs;

import MyExceptions.ProgramStateException;

import java.util.EmptyStackException;
import java.util.LinkedList;

public class MyStack<T> implements MyIStack<T>{
    //I need getAll
    protected LinkedList<T> stack=new LinkedList<>();//Linked list

    @Override
    public T pop(){
        try
        {
            return stack.pop();
        }
        catch (EmptyStackException e) {
             throw new ProgramStateException("Empty Execution Stack");
        }
    }
    @Override
    public void push(T v){stack.push(v);}

    @Override
    public boolean isEmpty(){return stack.isEmpty();}


    @Override
    public String toString() {
        StringBuilder rez= new StringBuilder();
        for(T v:stack){
            rez.append(v.toString()).append("\n");
        }
        return rez.toString();
    }

}
