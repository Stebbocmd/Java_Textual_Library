package it.university.repository;
import it.university.exceptions.NoDataFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger; // serve per gestione ID automatici

public abstract class AbstractMapRepository<T, ID> implements IRepository<T, ID> {

    // 'protected' così è accessibile solo da questa classe e dalle sue sottoclassi
    protected final Map<ID, T> data = new HashMap<>();  /* Mettendo final si fa in modo che la mappa non venga modificata */
    protected final AtomicInteger idGenerator = new AtomicInteger(1); //  AUTO-ID

    /**
     * Metodo astratto che deve essere implementato dalle sottoclassi.
     * Definisce come estrarre l'ID univoco dall'entità.
     */
    
    protected abstract ID getEntityId(T entity);

    @Override
    public void save(T entity) {
        if (entity == null) {   
            throw new IllegalArgumentException("L'entità da salvare non può essere nulla.");
        }
            ID id = getEntityId(entity);
            data.put(id, entity);
        
    }

    @Override
    public Optional<T> findById(ID id) { //Optional perchè potrebbe non avere un ID
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<T> findAll() {
        // Restituiamo una nuova ArrayList per evitare modifiche esterne alla nostra mappa interna
        return new ArrayList<>(data.values());
    }

    @Override
    public void deleteById(ID id) throws NoDataFoundException{
        T removed = data.remove(id);
        if (removed == null) {
            throw new NoDataFoundException("Entità con ID " + id + " non trovata");
        }
    }

    public ID getLastGeneratedId() {
    return (ID) Integer.valueOf(idGenerator.get() - 1); //utile per i return dei save nei service
    }


}


