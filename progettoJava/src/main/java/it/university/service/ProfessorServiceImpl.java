package it.university.service;
import it.university.repository.AbstractMapRepository;
import it.university.exceptions.NoDataFoundException; 
import it.university.model.Professor;
import it.university.repository.IRepository; 
import java.util.List;

public class ProfessorServiceImpl{ 

   
    private final IRepository<Professor, Integer> repository;

    
    //    Il repository viene iniettato dall'esterno
    public ProfessorServiceImpl(IRepository<Professor, Integer> repository) {
        this.repository = repository;
    }

    
    public Professor register(String name, String department)throws IllegalStateException {
        Professor professor = new Professor(name, department);
        repository.save(professor);
        int lastId = ((AbstractMapRepository<Professor, Integer>) repository).getLastGeneratedId();
        return repository.findById(lastId).orElseThrow(() -> new IllegalStateException("Professore non salvato correttamente"));
        //return professor; ritornerebbe professor con id null, non corretto -> succedeva anche in student ed in course...
    }
    
    
    public List<Professor> list() throws NoDataFoundException { 
        List<Professor> professors = repository.findAll(); 
        
        if (professors.isEmpty()){
            throw new NoDataFoundException("Nessun professore trovato");
        }
        return professors; 
    }
}
