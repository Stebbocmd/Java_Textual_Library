package it.university.model;

public class Course {
    private Integer id;  // ← Integer, non int perché id può essere null. Utile per i nuovi corsi con id assegnato dal repo
    private String name;
    private int credits;
    private Integer professorId;

    // Costruttore SENZA ID (per nuovi corsi)
    public Course(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    // Costruttore CON ID (per caricamento da repo)
    public Course(Integer id, String name, int credits, Integer professorId) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.professorId = professorId;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public Integer getProfessorId() { return professorId; }
    public void setProfessorId(Integer professorId) { this.professorId = professorId; }


    @Override
    public String toString() {
        return "Course{" + id + ", " + name + ", " + credits + " CFU, prof=" + professorId + "}";
    }
}// questo non si puo trasformare in record perche è l'unico che contiene un setter ed è mutabile

