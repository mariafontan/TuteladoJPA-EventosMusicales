package gei.id.tutelado.dao;

import gei.id.tutelado.model.Festival;

public interface FestivalDao {
    
    void setup(Object config);
    
    // MO4.1
    Festival recuperaPorCodigo(Integer codigo);
    
    // MO4.2
    void almacena(Festival festival);
    
    // MO4.3
    void elimina(Festival festival);
    
    // MO4.4
    Festival modifica(Festival festival);
    
    // MO4.5: Inicialización lazy (si fuera necesario, por ejemplo estilos o artistas)
    // El enunciado pide solo UNO en total para todo el modelo, y ya está en ConciertoDaoJPA.restauraArtistas().
    // Pero si queremos ser completos, podemos añadir uno aquí para estilos. 
    // No obstante, el requisito MO4.1-MO4.4 es obligatorio por cada clase.
}
