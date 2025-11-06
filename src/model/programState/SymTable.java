package model.programState;

import model.programState.ADTs.MyDictionary;
import model.value.Id;
import model.value.Value;

import java.util.Map;

public class SymTable extends MyDictionary<Id,Value> implements Cloneable {
    @Override
    public SymTable clone() {

        SymTable newS=new SymTable();

        for (Map.Entry<Id, Value> e : map.entrySet()) {
            newS.map.put(e.getKey().clone(),e.getValue().clone());
        }

        return newS;
    }
}
