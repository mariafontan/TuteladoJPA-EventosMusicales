package gei.id.tutelado.dao;

import gei.id.tutelado.model.EventoMusical;

public interface EventoMusicalDao {
    
    // MO4.1: Recuperación por clave natural
    EventoMusical recuperaPorCodigo(Integer codigo_evento);
    
    // MO4.2: Alta
    void almacena(EventoMusical evento);
    
    // MO4.3: Eliminación
    void elimina(EventoMusical evento);
    
    // MO4.4: Actualización
    EventoMusical modifica(EventoMusical evento);
    
    void setup(Object config);
}
