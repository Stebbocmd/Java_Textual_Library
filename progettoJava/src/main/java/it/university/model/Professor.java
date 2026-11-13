package it.university.model;

public record Professor(Integer id, String name, String department) {
    // Non serve altro.
    // Costruttore, getter, toString(), vengono creati automaticamente dal record. I dati vengono messi final per default

    public Professor (String name, String department){//costruttore con overloading
     this(null, name, department); //chiamo costruttore canonico per inizializzare con id = null
    }

    @Override
    public String toString() {
        return "Professor{" + id + ", " + name + ", dept=" + department + "}";
    }
}

