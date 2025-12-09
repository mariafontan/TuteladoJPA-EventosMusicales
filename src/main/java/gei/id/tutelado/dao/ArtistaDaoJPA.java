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

    // MO4.1: Recuperación por clave natural usando consulta estática JPQL
    @Override
    public Artista recuperaPorCodigo(Integer codigo_artista) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            
            TypedQuery<Artista> q = em.createQuery(
                "SELECT a FROM Artista a WHERE a.codigo_artista = :codigo", 
                Artista.class
            );
            q.setParameter("codigo", codigo_artista);
            Artista resultado = q.getSingleResult();
            
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

    // MO4.2: Alta usando persist()
    @Override
    public void almacena(Artista artista) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(artista);
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
    public void elimina(Artista artista) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Artista artistaTmp = em.find(Artista.class, artista.getId_artista());
            em.remove(artistaTmp);
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
    public Artista modifica(Artista artista) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Artista artistaTmp = em.merge(artista);
            em.getTransaction().commit();
            em.close();
            return artistaTmp;
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
            throw (ex);
        }
    }

    // MO4.6.c: Consulta dinámica JPQL con subconsulta
    // Busca artistas que tienen más conciertos que la media
    @Override
    public java.util.List<Artista> buscarArtistasConMasConciertosDeLaMedia() {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            
            TypedQuery<Artista> q = em.createQuery(
                "SELECT a FROM Artista a " +
                "WHERE SIZE(a.conciertos) > " +
                "(SELECT AVG(SIZE(a2.conciertos)) FROM Artista a2)",
                Artista.class
            );
            java.util.List<Artista> resultado = q.getResultList();
            
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

    // MO4.6.d: Consulta dinámica JPQL con función de agregación
    @Override
    public Long contarArtistasDelGenero(String genero) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            
            TypedQuery<Long> q = em.createQuery(
                "SELECT COUNT(a) FROM Artista a WHERE a.genero = :genero",
                Long.class
            );
            q.setParameter("genero", genero);
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
}
