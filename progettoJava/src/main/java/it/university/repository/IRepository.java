package it.university.repository;
import java.util.List;
import java.util.Optional;
import it.university.exceptions.NoDataFoundException;

public interface IRepository<T, ID> {
    void save(T entity);
    Optional<T> findById(ID id);     
    List<T> findAll();
    void deleteById(ID id) throws NoDataFoundException;         
}//interface generica per i repository 