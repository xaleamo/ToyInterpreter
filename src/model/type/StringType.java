package model.type;

import model.value.StringValue;

public class StringType implements Type {

    @Override
    public StringType clone() {return new StringType();}

    @Override
    public StringValue defaultValue() {
        return new StringValue("default");
    }

    @Override
    public boolean equals(Object t) {
        return t instanceof StringType;
    }

    @Override
    public String toString() {
        return "StringType";
    }

}