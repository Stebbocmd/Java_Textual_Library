package it.university.repository;

import it.university.model.Grade;

import it.university.model.EnrollmentKey;
public class GradeRepository extends AbstractMapRepository<Grade, EnrollmentKey> {

    @Override
    protected EnrollmentKey getEntityId(Grade entity) {
        // La chiave è composta dai due ID
        return new EnrollmentKey(entity.studentId(), entity.courseId());
    }
}