package gei.id.tutelado.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import gei.id.tutelado.model.Artista;

public class ArtistaDaoJPA implements ArtistaDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Object config) {
        this.emf = (EntityManagerFactory) config;
    }

    // Recuperar
    @Override
    public Artista recuperaPorCodigo(Integer codigo_artista) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            TypedQuery<Artista> q = em.createQuery(
                    "SELECT a FROM Artista a WHERE a.codigo_artista = :codigo",
                    Artista.class);
            q.setParameter("codigo", codigo_artista);
            Artista resultado = q.getSingleResult();

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
    public void almacena(Artista artista) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(artista);
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
    public void elimina(Artista artista) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Artista artistaTmp = em.find(Artista.class, artista.getId_artista());
            em.remove(artistaTmp);
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
    public Artista modifica(Artista artista) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Artista artistaTmp = em.merge(artista);
            em.getTransaction().commit();
            return artistaTmp;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Buscar artistas con más conciertos que la media
    @Override
    public java.util.List<Artista> buscarArtistasConMasConciertosDeLaMedia() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            TypedQuery<Artista> q = em.createQuery(
                    "SELECT a FROM Artista a " +
                            "WHERE SIZE(a.conciertos) > " +
                            "(SELECT AVG(SIZE(a2.conciertos)) FROM Artista a2)",
                    Artista.class);
            java.util.List<Artista> resultado = q.getResultList();

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

    // Contar artistas por género
    @Override
    public Long contarArtistasDelGenero(String genero) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            TypedQuery<Long> q = em.createQuery(
                    "SELECT COUNT(a) FROM Artista a WHERE a.genero = :genero",
                    Long.class);
            q.setParameter("genero", genero);
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
}
