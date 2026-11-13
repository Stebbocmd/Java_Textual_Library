package it.university.service;
import java.util.Optional;
import it.university.exceptions.NoDataFoundException;
import it.university.model.Classroom;
import it.university.repository.IRepository;
import java.util.List;

public class ClassroomServiceImpl{ 
    
    // Dipende dall'INTERFACCIA GENERICA, quindi meglio per l'implementazione dell'interfaccia
    private final IRepository<Classroom, String> repo;

    public ClassroomServiceImpl(IRepository<Classroom, String> repo) {
        this.repo = repo;
    }

    
    public Classroom createClassroom(String code, int capacity) {
        Classroom classroom = new Classroom(code, capacity);
        repo.save(classroom);
        return classroom; //in questo caso va bene ritornare classroom perché la chiave è code che è già settata
    }

   
    public List<Classroom> list() throws NoDataFoundException {
        List<Classroom> classrooms = repo.findAll(); //delega al metodo ereditato
        if (classrooms.isEmpty()) {
            throw new NoDataFoundException("Nessuna aula trovata");
        }
        return classrooms;
    }

    public Optional<Classroom> findByCode(String code) {
        return repo.findById(code);  //delega al metodo ereditato
    }
}