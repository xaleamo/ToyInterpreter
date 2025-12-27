package model.program_state.ADTs;

import my_exceptions.MyException;
import my_exceptions.ProgramStateException;

import java.util.Hashtable;
import java.util.Map;

public class FileTableGeneric<K,V> implements IFileTableGeneric<K,V>{

    protected Hashtable<K,V> table=new Hashtable<>();

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
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<K,V> e:table.entrySet()){
            sb.append(e.getKey().toString()).append(" -> ").append(e.getValue().toString());
            sb.append("\n");
        }
        return sb.toString();
    }
    @Override
    public Hashtable<K,V> getContent(){return table;}
}
