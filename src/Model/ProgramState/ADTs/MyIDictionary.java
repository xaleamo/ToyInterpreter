package Model.ProgramState.ADTs;

public interface MyIDictionary <K,V>{
    public V Remove(K k);
    public V Add(K k,V v);
}
