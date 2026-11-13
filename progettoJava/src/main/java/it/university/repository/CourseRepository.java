package it.university.repository;

import it.university.model.Course;

public class CourseRepository extends AbstractMapRepository<Course, Integer> {

    @Override
    protected Integer getEntityId(Course entity) {
        return entity.getId();
    }

    @Override // Implementazione del metodo save con gestione ID automatici
    public void save(Course entity) {
        Course toSave = entity;
        if (entity.getId() == null) {
            toSave = new Course(
             idGenerator.getAndIncrement(),
                entity.getName(),
                entity.getCredits(),
                entity.getProfessorId()
           );
        }
    super.save(toSave); // Usa AbstractMapRepository
    }

}
