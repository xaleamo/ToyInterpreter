package model.type;

import model.value.RefValue;
import model.value.Value;

public class RefType implements Type{

    private Type inner;


    public RefType(Type inner){
        this.inner = inner;
    }

    public Type getInner(){return inner;}

    @Override
    public Value defaultValue() {
        return new RefValue(0,inner);
    }

    @Override
    public String toString(){
        return "Ref("+inner.toString()+")";
    }

    @Override
    public boolean equals(Object t) {
        if(t instanceof RefType){
            return inner.equals(((RefType)t).inner);
        }
        else return false;
    }

    @Override
    public Type clone(){
        return new RefType(inner.clone());
    }

}
