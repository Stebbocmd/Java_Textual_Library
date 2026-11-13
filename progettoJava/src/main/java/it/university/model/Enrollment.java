package it.university.model;

public record Enrollment(int studentId, int courseId) {
    // Non serve altro.
    // Costruttore, getter, toString(), vengono creati automaticamente dal record. I dati vengono messi final per default
    @Override
    public String toString() {
        return "Enrollment{student=" + studentId + ", course=" + courseId + "}";
    }
}
