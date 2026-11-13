package it.university.model;

public record Classroom(String code, int capacity) {
    // Non serve altro.
    // Costruttore, getter, toString(), vengono creati automaticamente dal record. I dati vengono messi final per default
    public Classroom {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Il codice aula non può essere vuoto");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("La capienza deve essere positiva");
        }
    }

    @Override
    public String toString() {
        return "Classroom{" + code + ", capacity=" + capacity + "}";
    }
}

