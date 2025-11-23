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

    public int getAddress(){return address;}
    @Override
    public Type getType() {return new RefType(locationType);}

    @Override
    public Object clone(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public String toString(){
        return '('+Integer.toString(address)+','+locationType.toString()+')';
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof RefValue){

            return ((RefValue)o).address==this.address && ((RefValue)o).equals((RefValue)this.locationType);
        }
        else return false;
    }
}
