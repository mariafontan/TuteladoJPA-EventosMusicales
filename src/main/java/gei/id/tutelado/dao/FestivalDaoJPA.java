package gei.id.tutelado.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import gei.id.tutelado.model.Festival;

public class FestivalDaoJPA implements FestivalDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Object config) {
        this.emf = (EntityManagerFactory) config;
    }

    // Recuperar por codigo
    @Override
    public Festival recuperaPorCodigo(Integer codigo) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            TypedQuery<Festival> q = em.createQuery(
                    "SELECT f FROM Festival f WHERE f.codigo_evento = :codigo",
                    Festival.class);
            q.setParameter("codigo", codigo);
            Festival resultado = q.getSingleResult();

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

    // Guardar festival
    @Override
    public void almacena(Festival festival) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(festival);
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

    // Borrar festival
    @Override
    public void elimina(Festival festival) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Festival festivalTmp = em.find(Festival.class, festival.getId_evento());
            em.remove(festivalTmp);
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
    public Festival modifica(Festival festival) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Festival festivalTmp = em.merge(festival);
            em.getTransaction().commit();
            return festivalTmp;
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
