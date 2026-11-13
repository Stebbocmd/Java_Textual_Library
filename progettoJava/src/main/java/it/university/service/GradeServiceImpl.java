package it.university.service;
import it.university.exceptions.NoDataFoundException; //  importa l'eccezione
import it.university.model.EnrollmentKey; // Importa la chiave
import it.university.model.Grade;
import it.university.repository.IRepository; // Importa l'INTERFACCIA
import java.util.List;

public class GradeServiceImpl{

    
    private final IRepository<Grade, EnrollmentKey> repo;
    private final EnrollmentServiceImpl enrollmentService;  // <--- importantissimo per controllare iscrizione e doppioni

    public GradeServiceImpl(IRepository<Grade, EnrollmentKey> repo, EnrollmentServiceImpl enrollmentService) {
        this.repo = repo;
        this.enrollmentService = enrollmentService; 
    }
  
    
    
    
    public void registerGrade(int studentId, int courseId, int value)throws IllegalArgumentException{
        //Lo studente deve essere iscritto al corso
        if (!enrollmentService.isEnrolled(studentId, courseId)) {
            throw new IllegalArgumentException("Impossibile registrare il voto: lo studente " + studentId + " non è iscritto al corso " + courseId);
        }

        //Evitiamo doppioni
        EnrollmentKey key = new EnrollmentKey(studentId, courseId);
        if (repo.findById(key).isPresent()) {
            throw new IllegalArgumentException("Voto già registrato per questo studente in questo corso");
        }

        //la validazione avviene automaticamente nel costruttore di Grade
        //se il voto non e' valido, salta direttamente nel catch del MainApp
        Grade grade = new Grade(studentId, courseId, value);
        repo.save(grade);
    }

    
     
    public List<Grade> list() throws NoDataFoundException { 
        List<Grade> grades = repo.findAll();
        if (grades.isEmpty()){
            throw new NoDataFoundException("Nessun voto registrato");
        } 
        return grades; 
    }

}