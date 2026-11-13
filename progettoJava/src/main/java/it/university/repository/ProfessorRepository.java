package it.university.repository; 

import it.university.model.Professor;


// Estende la classe astratta, specificando i tipi Generics
public class ProfessorRepository extends AbstractMapRepository<Professor, Integer> {
 
    @Override
    protected Integer getEntityId(Professor entity) {
        return entity.id();// getter creato nel record quindi diventa solo il nome della variabile 
    }

    @Override
    public void save(Professor entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entità non può essere nulla");
        }

        Professor toSave = entity;
        if (entity.id() == null) {
            toSave = new Professor(
                idGenerator.getAndIncrement(),
                entity.name(),
                entity.department()
            );
        }
        // Usa il metodo della superclasse
        super.save(toSave);
    }

}
