package it.university.model;

public record Grade(int studentId, int courseId, int value) {

    // Non serve altro.
    // Costruttore, getter, toString(), vengono creati automaticamente dal record. I dati vengono messi final per default

    public Grade {
        if (value < 0 || value > 30) {
            throw new IllegalArgumentException("Voto non valido: " + value);
        }
        if (value > 0 && value < 18) {
            throw new IllegalArgumentException("Voto insufficiente: minimo 18");
        }
    }


   @Override
    public String toString() {
        return "Grade{student=" + studentId + ", course=" + courseId + ", value=" + value + "}";
    }
}
