package Model.ProgramState.ADTs;

import MyExceptions.MyException;
import MyExceptions.ProgramStateException;

import java.util.Hashtable;

public class FileTableGeneric<K,V> implements IFileTableGeneric<K,V>{

    Hashtable<K,V> table=new Hashtable<>();

    /**
     *
     * @param k key
     * @param v value
     * @return previous value or null if key did not exist
     * @throws MyException if k or v are null
     */
    @Override
    public V add(K k,V v) {
        if(k==null || v==null) throw new MyException("K or V is null");
        return table.put(k,v);
    }
    @Override
    public void remove(K k) {
        if(k==null) throw new MyException("K is null");
        table.remove(k);
    }
    @Override
    public boolean isDefined(K k) {
        if(k==null) throw new MyException("K is null");
        return table.containsKey(k);
    }
    @Override
    public V lookUp(K k) {
        if(k==null) throw new MyException("K is null");
        V v= table.get(k);
        if(v==null) throw new ProgramStateException("No such key exists.");
        return v;
    }
}
