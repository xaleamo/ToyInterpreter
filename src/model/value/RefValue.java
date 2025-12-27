package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value {
    private int address;//make Address type
    private Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    //getters
    public int getAddress(){return address;}

    @Override
    public Type getType() {return new RefType(locationType);}

    //others
    @Override
    public Value clone(){
        return new RefValue(address,locationType.clone());
    }
    @Override
    public String toString(){
        return '('+Integer.toString(address)+','+locationType.toString()+')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RefValue)) return false;
        RefValue other = (RefValue) o;

        return this.address == other.address
                && this.locationType.equals(other.locationType); // safe Type comparison
    }
}
