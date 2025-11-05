package Model.ProgramState.ADTs;

public interface MyIDictionary <K,V>{
    public V remove(K k);
    public V add(K k, V v);
}
