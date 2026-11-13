package it.university;
import it.university.exceptions.NoDataFoundException;
import it.university.model.*;
import it.university.repository.*;
import it.university.service.*;
import java.util.Scanner;

public class MainApp {

    private static final Scanner scanner = new Scanner(System.in);

    // Repository
    private static final StudentRepository studentRepo = new StudentRepository();
    private static final ProfessorRepository professorRepo = new ProfessorRepository();
    private static final CourseRepository courseRepo = new CourseRepository();
    private static final ClassroomRepository classroomRepo = new ClassroomRepository();
    private static final EnrollmentRepository enrollmentRepo = new EnrollmentRepository();
    private static final GradeRepository gradeRepo = new GradeRepository();

    // Service 
    private static final StudentServiceImpl studentService = new StudentServiceImpl(studentRepo);
    private static final ProfessorServiceImpl professorService = new ProfessorServiceImpl(professorRepo);
    private static final CourseServiceImpl courseService = new CourseServiceImpl(courseRepo, professorRepo);
    private static final ClassroomServiceImpl classroomService = new ClassroomServiceImpl(classroomRepo);
    private static final EnrollmentServiceImpl enrollmentService = new EnrollmentServiceImpl(enrollmentRepo, studentRepo, courseRepo);
    private static final GradeServiceImpl gradeService = new GradeServiceImpl(gradeRepo, enrollmentService); 
    public static void main(String[] args) {
        System.out.println("Benvenuto nel Sistema di Gestione Università");
        boolean exit = false;

        while (!exit) {
            printMainMenu();
            int choice = readInt("Scegli un'opzione: ");

            try {
                switch (choice) {
                    case 1 -> manageStudents();
                    case 2 -> manageProfessors();
                    case 3 -> manageCourses();
                    case 4 -> manageClassrooms();
                    case 5 -> manageEnrollments();
                    case 6 -> manageGrades();
                    case 7 -> viewAllData();
                    case 0 -> { exit = true; System.out.println("Arrivederci! 👋"); }
                    default -> System.out.println("❌ Opzione non valida.");
                }
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MENU PRINCIPALE");
        System.out.println("=".repeat(50));
        System.out.println("1. Gestisci Studenti");
        System.out.println("2. Gestisci Professori");
        System.out.println("3. Gestisci Corsi");
        System.out.println("4. Gestisci Aule");
        System.out.println("5. Gestisci Iscrizioni");
        System.out.println("6. Gestisci Voti");
        System.out.println("7. Visualizza Tutti i Dati");
        System.out.println("0. Esci");
        System.out.println("=".repeat(50));
    }

    // ==================== GESTIONE STUDENTI ====================
    private static void manageStudents() {
        while (true) {
            System.out.println("\n--- Studenti ---");
            System.out.println("1. Registra nuovo studente");
            System.out.println("2. Elenca tutti gli studenti");
            System.out.println("0. Indietro");

            int choice = readInt("Scelta: ");
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1 -> {
                        String name = readString("Nome: ");
                        String email = readString("Email: ");
                        Student s = studentService.registerStudent(name, email);
                        System.out.println("Studente registrato: ID=" + s.id() + ", " + s.name());
                    }
                    case 2 -> {
                        for (Student s : studentService.list()) {
                            System.out.println(s.id() + " | " + s.name() + " | " + s.email());
                        }
                    }
                    default -> System.out.println("Opzione non valida.");
                }
            } catch (NoDataFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // ==================== GESTIONE PROFESSORI ====================
    private static void manageProfessors() {
        while (true) {
            System.out.println("\n--- Professori ---");
            System.out.println("1. Registra nuovo professore");
            System.out.println("2. Elenca tutti i professori");
            System.out.println("0. Indietro");

            int choice = readInt("Scelta: ");
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1 -> {
                        String name = readString("Nome: ");
                        String dept = readString("Dipartimento: ");
                        Professor p = professorService.register(name, dept);
                        System.out.println("Professore registrato: ID=" + p.id() + ", " + p.name());
                    }
                    case 2 -> {
                        for (Professor p : professorService.list()) {
                            System.out.println(p.id() + " | " + p.name() + " | " + p.department());
                        }
                    }
                    default -> System.out.println("Opzione non valida.");
                }
            } catch (NoDataFoundException e) {
                System.out.println(e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    // ==================== GESTIONE CORSI ====================
    private static void manageCourses() {
        while (true) {
            System.out.println("\n--- Corsi ---");
            System.out.println("1. Crea nuovo corso");
            System.out.println("2. Assegna professore a corso");
            System.out.println("3. Elenca tutti i corsi");
            System.out.println("0. Indietro");

            int choice = readInt("Scelta: ");
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1 -> {
                        String name = readString("Nome corso: ");
                        int credits = readInt("Crediti: ");
                        Course c = courseService.createCourse(name, credits);
                        System.out.println("Corso creato: ID=" + c.getId() + ", " + c.getName());
                    }
                    case 2 -> {
                        int courseId = readInt("ID Corso: ");
                        int profId = readInt("ID Professore: ");
                        courseService.assignProfessor(courseId, profId);
                        System.out.println("Professore assegnato con successo.");
                    }
                    case 3 -> {
                        for (Course c : courseService.list()) {
                            String prof = c.getProfessorId() == null ? "Nessuno" :
                                    professorRepo.findById(c.getProfessorId())
                                            .map(p -> p.name())
                                            .orElse("Sconosciuto");
                            System.out.println(c.getId() + " | " + c.getName() + " | Crediti: " + c.getCredits() + " | Prof: " + prof);
                        }
                    }
                    default -> System.out.println("Opzione non valida.");
                }
            } catch (NoDataFoundException e) {
                System.out.println(e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println("Errore: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    // ==================== GESTIONE AULE ====================
    private static void manageClassrooms() {
        while (true) {
            System.out.println("\n--- Aule ---");
            System.out.println("1. Crea nuova aula");
            System.out.println("2. Elenca tutte le aule");
            System.out.println("0. Indietro");

            int choice = readInt("Scelta: ");
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1 -> {
                        String code = readString("Codice aula (es. A101): ");
                        int capacity = readInt("Capacità: ");
                        Classroom c = classroomService.createClassroom(code, capacity);
                        System.out.println("Aula creata: " + c.code() + ", capacità: " + c.capacity());
                    }
                    case 2 -> {
                        for (Classroom c : classroomService.list()) {
                            System.out.println(c.code() + " | Capacità: " + c.capacity());
                        }
                    }
                    default -> System.out.println("Opzione non valida.");
                }
            } catch (NoDataFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // ==================== GESTIONE ISCRIZIONI ====================
    private static void manageEnrollments() {
        while (true) {
            System.out.println("\n--- Iscrizioni ---");
            System.out.println("1. Iscrivi studente a corso");
            System.out.println("2. Elenca tutte le iscrizioni");
            System.out.println("0. Indietro");

            int choice = readInt("Scelta: ");
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1 -> {
                        int studentId = readInt("ID Studente: ");
                        int courseId = readInt("ID Corso: ");
                        if (enrollmentService.isEnrolled(studentId, courseId)) {
                            System.out.println("Studente già iscritto a questo corso.");
                        } else {
                            enrollmentService.enrollStudent(studentId, courseId);
                            System.out.println("Iscrizione completata.");
                        }
                    }
                    case 2 -> {
                        for (Enrollment e : enrollmentService.list()) {
                            Student s = studentRepo.findById(e.studentId()).orElse(null);
                            Course c = courseRepo.findById(e.courseId()).orElse(null);
                            System.out.println("Studente: " + (s != null ? s.name() : e.studentId()) +
                                    " → Corso: " + (c != null ? c.getName() : e.courseId()));
                        }
                    }
                    default -> System.out.println("Opzione non valida.");
                }
            } catch (NoDataFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // ==================== GESTIONE VOTI ====================
    private static void manageGrades() {
        while (true) {
            System.out.println("\n--- Voti ---");
            System.out.println("1. Registra voto");
            System.out.println("2. Elenca tutti i voti");
            System.out.println("0. Indietro");

            int choice = readInt("Scelta: ");
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1 -> {
                        int studentId = readInt("ID Studente: ");
                        int courseId = readInt("ID Corso: ");
                        int value = readInt("Voto (18-30): ");
                        gradeService.registerGrade(studentId, courseId, value);
                        System.out.println("Voto registrato.");
                        }
                    case 2 -> {
                        for (Grade g : gradeService.list()) {
                            Student s = studentRepo.findById(g.studentId()).orElse(null);
                            Course c = courseRepo.findById(g.courseId()).orElse(null);
                            System.out.println("Studente: " + (s != null ? s.name() : g.studentId()) +
                                    " | Corso: " + (c != null ? c.getName() : g.courseId()) +
                                    " | Voto: " + g.value());
                        }
                    }
                    default -> System.out.println("Opzione non valida.");
                }
            }catch (NoDataFoundException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    // ==================== VISUALIZZA TUTTO ====================
    private static void viewAllData() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("RIEPILOGO COMPLETO");
        System.out.println("=".repeat(60));

        try { studentService.list().forEach(s -> System.out.println("Studente: " + s.id() + " | " + s.name())); }
        catch (NoDataFoundException e) { System.out.println(e.getMessage()); }

        try { professorService.list().forEach(p -> System.out.println("Prof: " + p.id() + " | " + p.name())); }
        catch (NoDataFoundException e) { System.out.println(e.getMessage()); }

        try { courseService.list().forEach(c -> System.out.println("Corso: " + c.getId() + " | " + c.getName())); }
        catch (NoDataFoundException e) { System.out.println(e.getMessage()); }

        try { classroomService.list().forEach(a -> System.out.println("Aula: " + a.code() + " | Cap: " + a.capacity())); }
        catch (NoDataFoundException e) { System.out.println(e.getMessage()); }

        try { enrollmentService.list().forEach(e -> System.out.println("Iscrizione: S" + e.studentId() + " → C" + e.courseId())); }
        catch (NoDataFoundException e) { System.out.println(e.getMessage()); }

        try { gradeService.list().forEach(g -> System.out.println("Voto: S" + g.studentId() + " | C" + g.courseId() + " → " + g.value())); }
        catch (NoDataFoundException e) { System.out.println(e.getMessage()); }
    }

    // ==================== UTILITY ====================
    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
    }
}





/*
 Compilazione
javac -d out $(find src/main/java -name "*.java")0

 Esecuzione
java -cp out it.university.MainApp
*/