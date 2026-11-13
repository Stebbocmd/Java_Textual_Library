package it.university.model;

public record Student(Integer id, String name, String email) {
    // Non serve altro.
    // Costruttore, getter, toString(), vengono creati automaticamente dal record. I dati vengono messi final per default

    public Student (String name, String email){//costruttore sovraccarico (overloading)
        this(null,name,email);// chiamo il costruttore canonico (record) per inizializzare id null
    }

    public Student {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome obbligatorio");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email non valida");
        }
    }

    @Override
    public String toString() {
        return "Student{" + id + ", " + name + ", " + email + "}";
    }
}