package model.program_state;

import model.program_state.ADTs.HashMapHeap;
import model.program_state.ADTs.MyDictionary;
import model.value.Id;
import model.value.Value;

import java.util.Map;

public class Heap extends HashMapHeap<Value> implements Cloneable {
    @Override
    public Heap clone() {

        Heap newH=new Heap();

        for (Map.Entry<Integer, Value> e : map.entrySet()) {//Integer is immutable, so no clone needed
            newH.map.put(e.getKey(),e.getValue().clone());
        }

        return newH;
    }
}
