package model.program_state.ADTs;

import my_exceptions.ProgramStateException;

import java.util.LinkedList;
import java.util.NoSuchElementException;


public class MyStack<T> implements MyIStack<T>{
    protected LinkedList<T> stack=new LinkedList<>();//Linked list


    @Override
    public T pop(){
        try
        {
            return stack.pop();
        }
        catch (NoSuchElementException e) {
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

//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        MyStack<T> ret = (MyStack<T>) super.clone();
//        for (T v:stack){
//            ret.push(v.clone());
//        }
//        return ret;
//    }
}
