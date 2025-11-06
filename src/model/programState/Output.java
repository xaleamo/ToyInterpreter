package model.programState;

import model.programState.ADTs.MyList;
import model.value.Value;

public class Output extends MyList<Value> {

    @Override
    public Output clone() {
        Output newO = new Output();
        for(Value v : list){
            newO.add(v.clone());
        }
        return newO;
    }
}
