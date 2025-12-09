package gei.id.tutelado.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import gei.id.tutelado.model.Concierto;

public class ConciertoDaoJPA implements ConciertoDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Object config) {
        this.emf = (EntityManagerFactory) config;
    }

    // Recuperar
    @Override
    public Concierto recuperaPorCodigo(Integer codigo_evento) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            TypedQuery<Concierto> q = em.createQuery(
                    "SELECT c FROM Concierto c WHERE c.codigo_evento = :codigo",
                    Concierto.class);
            q.setParameter("codigo", codigo_evento);
            Concierto resultado = q.getSingleResult();

            em.getTransaction().commit();
            return resultado;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Guardar
    @Override
    public void almacena(Concierto concierto) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(concierto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Eliminar
    @Override
    public void elimina(Concierto concierto) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Concierto conciertoTmp = em.find(Concierto.class, concierto.getId_evento());
            em.remove(conciertoTmp);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Modificar
    @Override
    public Concierto modifica(Concierto concierto) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Concierto conciertoTmp = em.merge(concierto);
            em.getTransaction().commit();
            return conciertoTmp;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Cargar Lazy
    @Override
    public Concierto restauraArtistas(Concierto concierto) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            // Recuperamos el concierto y forzamos la inicialización de la colección LAZY
            Concierto conciertoTmp = em.find(Concierto.class, concierto.getId_evento());
            conciertoTmp.getArtistas().size(); // Fuerza la inicialización

            em.getTransaction().commit();
            return conciertoTmp;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Contar por artista (INNER JOIN)
    @Override
    public Long contarConciertosPorArtista(String nombreArtista) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            TypedQuery<Long> q = em.createQuery(
                    "SELECT COUNT(c) FROM Concierto c " +
                            "JOIN c.artistas a " +
                            "WHERE a.nombre = :nombre",
                    Long.class);
            q.setParameter("nombre", nombreArtista);
            Long resultado = q.getSingleResult();

            em.getTransaction().commit();
            return resultado;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Buscar todos (OUTER JOIN)
    @Override
    public java.util.List<Concierto> buscarConciertosConOSinArtistas() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            TypedQuery<Concierto> q = em.createQuery(
                    "SELECT DISTINCT c FROM Concierto c " +
                            "LEFT JOIN c.artistas a",
                    Concierto.class);
            java.util.List<Concierto> resultado = q.getResultList();

            em.getTransaction().commit();
            return resultado;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}
