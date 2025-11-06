package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value {
    String value;

    public StringValue(String value){
        this.value = value;
    }

    @Override
    public StringValue clone(){
        return new StringValue(value);
    }

    public String getValue(){return value;}
    public void setValue(String val){this.value = val;}

    @Override
    public Type getType(){
        return new StringType();
    }
    @Override
    public String toString(){return value;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValue val1 = (StringValue) o;
        return this.value.equals(val1.value);
    }
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

}
