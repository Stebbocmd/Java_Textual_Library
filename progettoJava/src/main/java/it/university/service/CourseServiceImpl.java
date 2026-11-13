package it.university.service;
import java.util.Optional;
import it.university.exceptions.NoDataFoundException;
import it.university.model.Course;
import it.university.repository.IRepository;
import java.util.List;
import it.university.repository.AbstractMapRepository;
import it.university.repository.ProfessorRepository;




public class CourseServiceImpl{

    // Dipende dall'INTERFACCIA GENERICA, quindi meglio per l'implementazione dell'interfaccia
    private final IRepository<Course, Integer> repository;
    private final ProfessorRepository professorRepo;

    
    public CourseServiceImpl(IRepository<Course, Integer> repository, ProfessorRepository professorRepo) {
        this.professorRepo = professorRepo;
        this.repository = repository;
    }


    public Course createCourse(String name, int credits) throws IllegalStateException{
        Course course = new Course(name, credits);
        repository.save(course);
        int lastId = ((AbstractMapRepository<Course, Integer>) repository).getLastGeneratedId();
        return repository.findById(lastId).orElseThrow(() -> new IllegalStateException("Corso non salvato correttamente"));
        //return course; ritornerebbe course con id null, non corretto -> succedeva anche in student e professor...
    }
    

    
    public void assignProfessor(int courseId, int professorId) throws NoDataFoundException {
        Course course = repository.findById(courseId)
        .orElseThrow(() -> new NoDataFoundException("Corso con ID " + courseId + " non trovato."));
        // Controllo esistenza Professore
        if (professorRepo.findById(professorId).isEmpty()) {
            throw new NoDataFoundException("Professore con ID " + professorId + " non trovato.");
        }
        course.setProfessorId(professorId);
        repository.save(course); // Aggiorna il corso con il professore assegnato
    }

    
    public List<Course> list() throws NoDataFoundException {
        List<Course> courses = repository.findAll();
        if (courses.isEmpty()) {
            throw new NoDataFoundException("Nessun corso trovato");
        }
        return courses;
    }

    
    public Optional<Course> findById(int id) {
        return repository.findById(id);
    }
}
