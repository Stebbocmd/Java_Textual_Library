package it.university.service;
import it.university.exceptions.NoDataFoundException;
import it.university.model.Enrollment;
import it.university.model.EnrollmentKey;
import it.university.repository.EnrollmentRepository; // Interfaccia specifica
import java.util.List;
import it.university.repository.StudentRepository;  
import it.university.repository.CourseRepository;

public class EnrollmentServiceImpl{

    // Dipende dall'interfaccia specifica
    private final EnrollmentRepository enrollmentRepo;
    private final StudentRepository studentRepo;      
    private final CourseRepository courseRepo;

    // Il repository viene iniettato dall'esterno
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepo, StudentRepository studentRepo, CourseRepository courseRepo) {
        this.enrollmentRepo = enrollmentRepo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    
    public void enrollStudent(int studentId, int courseId) throws NoDataFoundException {
        // Controllo esistenza Studente
        if (studentRepo.findById(studentId).isEmpty()) {
            throw new NoDataFoundException("Studente con ID " + studentId + " non trovato.");
        }

        // Controllo esistenza Corso
        if (courseRepo.findById(courseId).isEmpty())  {
            throw new NoDataFoundException("Corso con ID " + courseId + " non trovato.");
        }

        // Si procede con l'iscrizione solo se i controlli sono superati
        Enrollment enrollment = new Enrollment(studentId, courseId);
        enrollmentRepo.save(enrollment);
    }

    
    public List<Enrollment> list() throws NoDataFoundException {
        List<Enrollment> enrollments = enrollmentRepo.findAll();
        if (enrollments.isEmpty()) {
            throw new NoDataFoundException("Nessuna iscrizione trovata");
        }
        return enrollments;
    }

    public boolean isEnrolled(int studentId, int courseId) {
        EnrollmentKey key = new EnrollmentKey(studentId, courseId);
        return enrollmentRepo.findById(key).isPresent();
    }
}