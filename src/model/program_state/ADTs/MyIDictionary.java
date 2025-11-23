package model.program_state.ADTs;

public interface MyIDictionary <K,V>{
    V remove(K k);
    V add(K k, V v);

    boolean isDefined(K k);

    V lookUp(K k);
}
