package it.university.repository;
import java.util.Optional;
import it.university.model.Classroom;

public class ClassroomRepository extends AbstractMapRepository<Classroom, String> {

    @Override
    protected String getEntityId(Classroom entity) {
        return entity.code();//getCode(); diventa code(); perche i getter sono creati automaticamente nei record
    }

    
    public Optional<Classroom> findByCode(String code) {
        return findById(code); // Delega al metodo ereditato
    }
}
