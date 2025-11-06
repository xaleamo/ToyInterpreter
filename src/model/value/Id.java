package model.value;

public class Id {
    private final String id;
    public Id(String id) {
        this.id = id;
    }
    public String getId() {return id;}

    @Override
    public String toString(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id1 = (Id) o;
        return this.id.equals(id1.id);
    }
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
