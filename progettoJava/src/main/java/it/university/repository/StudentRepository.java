package it.university.repository;
import java.util.Optional;
import it.university.model.Student;
import java.util.HashMap;
import java.util.Map;
import it.university.exceptions.NoDataFoundException;

// Estende  AbstractMapRepository
public class StudentRepository extends AbstractMapRepository<Student, Integer> {

   
    //    Questa mappa associa: Email -> StudentID
    private final Map<String, Integer> emailIndex = new HashMap<>();

    @Override
    protected Integer getEntityId(Student entity) {
        return entity.id();
    }

    @Override
    public void save(Student entity) {
        if (entity == null) {
            throw new IllegalArgumentException("L'entità non può essere nulla.");
        }

        // Gestisco auto-ID
        Student toSave = entity;
        if (entity.id() == null) {
            toSave = new Student(
                idGenerator.getAndIncrement(),
                entity.name(),
                entity.email()
            );
        }

        // Gestisco email vecchia
        String oldEmail = null;
        if (data.containsKey(toSave.id())) {
            oldEmail = data.get(toSave.id()).email();
        }

        // Salva
        super.save(toSave);

        // Aggiorna indice
        if (oldEmail != null && !oldEmail.equals(toSave.email())) {
            emailIndex.remove(oldEmail);
        }
        emailIndex.put(toSave.email(), toSave.id());
    }

    //  METODO DI RICERCA VELOCE (O(1))
   public Optional<Student> findByEmail(String email) {
        Integer id = emailIndex.get(email);
        return id != null ? findById(id) : Optional.empty(); // se id e' diverso da null chiama findById altrimenti ritorna Optional.empty()
    }

    @Override
    public void deleteById(Integer id) throws NoDataFoundException{
        Student s = data.get(id);
        if (s != null) {
            emailIndex.remove(s.email());
        }
        super.deleteById(id);
    }

}

