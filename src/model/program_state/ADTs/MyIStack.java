package model.program_state.ADTs;

import java.util.LinkedList;

public interface MyIStack <T> {
    T pop();
    void push(T v);
    boolean isEmpty();

    LinkedList<T> getContent();
}
