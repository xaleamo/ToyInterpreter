package model.programState.ADTs;

public interface MyIStack <T> {
    T pop();
    void push(T v);
    boolean isEmpty();
}
