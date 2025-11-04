package Model.ProgramState.ADTs;

public interface MyIStack <T>{
    public T pop();
    public void push(T v);
    public boolean isEmpty();
}
