package gei.id.tutelado.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import gei.id.tutelado.model.Concierto;

public class ConciertoDao JPA implements ConciertoDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Object config) {
        this.emf = (EntityManagerFactory) config;
    }

    // MO4.1: Recuperación por clave natural usando consulta estática JPQL
    @Override
    public Concierto recuperaPorCodigo(Integer codigo_evento) {
        TypedQuery<Concierto> q = em.createQuery(
            "SELECT c FROM Concierto c WHERE c.codigo_evento = :codigo", 
            Concierto.class
        );
        q.setParameter("codigo", codigo_evento);
        return q.getSingleResult();
    }

    // MO4.2: Alta usando persist()
    @Override
    public void almacena(Concierto concierto) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(concierto);
            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
            throw (ex);
        }
    }

    // MO4.3: Eliminación usando remove()
    @Override
    public void elimina(Concierto concierto) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Concierto conciertoTmp = em.find(Concierto.class, concierto.getId_evento());
            em.remove(conciertoTmp);
            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
            throw (ex);
        }
    }

    // MO4.4: Actualización usando merge()
    @Override
    public Concierto modifica(Concierto concierto) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Concierto conciertoTmp = em.merge(concierto);
            em.getTransaction().commit();
            em.close();
            return conciertoTmp;
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
            throw (ex);
        }
    }

    // MO4.5: Inicializar colección LAZY (artistas) fuera de sesión
    @Override
    public Concierto restauraArtistas(Concierto concierto) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            
            // Recuperamos el concierto y forzamos la inicialización de la colección LAZY
            Concierto conciertoTmp = em.find(Concierto.class, concierto.getId_evento());
            conciertoTmp.getArtistas().size(); // Fuerza la inicialización
            
            em.getTransaction().commit();
            em.close();
            return conciertoTmp;
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
            throw (ex);
        }
    }

    // MO4.6.a: Consulta dinámica JPQL con INNER JOIN
    @Override
    public Long contarConciertosPorArtista(String nombreArtista) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            
            TypedQuery<Long> q = em.createQuery(
                "SELECT COUNT(c) FROM Concierto c " +
                "JOIN c.artistas a " +
                "WHERE a.nombre = :nombre",
                Long.class
            );
            q.setParameter("nombre", nombreArtista);
            Long resultado = q.getSingleResult();
            
            em.getTransaction().commit();
            em.close();
            return resultado;
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
            throw (ex);
        }
    }

    // MO4.6.b: Consulta dinámica JPQL con OUTER JOIN
    @Override
    public java.util.List<Concierto> buscarConciertosConOSinArtistas() {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            
            TypedQuery<Concierto> q = em.createQuery(
                "SELECT DISTINCT c FROM Concierto c " +
                "LEFT JOIN c.artistas a",
                Concierto.class
            );
            java.util.List<Concierto> resultado = q.getResultList();
            
            em.getTransaction().commit();
            em.close();
            return resultado;
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
            throw (ex);
        }
    }
}
