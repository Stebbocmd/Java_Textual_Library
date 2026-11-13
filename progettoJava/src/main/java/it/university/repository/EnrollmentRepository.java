package it.university.repository;
import it.university.model.EnrollmentKey;

import it.university.model.Enrollment;

public class EnrollmentRepository extends AbstractMapRepository<Enrollment, EnrollmentKey> {   // Rende la ricerca da O(n) a O(1)
    @Override
    protected EnrollmentKey getEntityId(Enrollment entity) {
        return new EnrollmentKey(entity.studentId(), entity.courseId());
    }
}//cosi utilizzo la mappa al posto della lista per evitare di scorrere tutti gli iscritti ogni volta che voglio controllare se uno studente è iscritto a un corso,
// come succede nel caso della lista che avevamo prima