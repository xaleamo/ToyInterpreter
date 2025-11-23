package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value {
    private static final StringType stringType = new StringType();
    private String value;

    public StringValue(String value){
        this.value = value;
    }

    @Override
    public StringValue clone(){
        return new StringValue(value);
    }
//    @Override
//    public Object clone(){
//        try {
//            StringValue ov = (StringValue) super.clone();//copies on surface level all of object
//            return ov;
//        }
//        catch (CloneNotSupportedException e) {
//            throw new MyException("StringValue.clone() error:"+e.getMessage());
//        }
//    }

    public String getValue(){return value;}
    //public void setValue(String val){this.value = val;}

    @Override
    public Type getType(){
        return stringType;
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
