package it.university.service;
import java.util.Optional;
import it.university.exceptions.NoDataFoundException;
import it.university.model.Student;
import it.university.repository.IRepository;
import java.util.List;
import it.university.repository.AbstractMapRepository;
import it.university.repository.StudentRepository;  


public class StudentServiceImpl {

    // Dipende dall'interfaccia IRepository
    private final IRepository<Student, Integer> repository;

    // Il repository viene iniettato dall'esterno
    public StudentServiceImpl(IRepository<Student, Integer> repository) {
        this.repository = repository;
    }

    
    public Student registerStudent(String name, String email) throws IllegalStateException {
        Student student = new Student(name,email);
        repository.save(student);
        int lastId = ((AbstractMapRepository<Student, Integer>) repository).getLastGeneratedId();
        return repository.findById(lastId).orElseThrow(() -> new IllegalStateException("Studente non salvato correttamente"));
    }
    
    public List<Student> list() throws NoDataFoundException {
        List<Student> students = repository.findAll();
        if (students.isEmpty()) {
            // Lancia l'eccezione invece di stampare
            throw new NoDataFoundException("Nessuno studente");
        }
        return students;
    }

    public Optional<Student> findByEmail(String email) {
        return ((StudentRepository) repository).findByEmail(email); // O(1)
    }
}