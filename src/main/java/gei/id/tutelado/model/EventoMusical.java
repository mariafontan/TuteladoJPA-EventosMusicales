package gei.id.tutelado.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "evento_musical")
@TableGenerator(name = "gen_evento", table = "id_gen", 
                pkColumnName = "gen_name", valueColumnName = "gen_val",
                pkColumnValue = "evento_gen", allocationSize = 1)
public abstract class EventoMusical implements Serializable {

    @Id
    @GeneratedValue(generator = "gen_evento", strategy = GenerationType.TABLE)
    private Long id_evento;

    @Column(nullable = false, unique = true)
    private Integer codigo_evento;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, length = 200)
    private String lugar;

    // Constructores
    public EventoMusical() {
    }

    public EventoMusical(Integer codigo_evento, String nombre, LocalDateTime fecha, String lugar) {
        this.codigo_evento = codigo_evento;
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
    }

    // Getters y Setters
    public Long getId_evento() {
        return id_evento;
    }

    public void setId_evento(Long id_evento) {
        this.id_evento = id_evento;
    }

    public Integer getCodigo_evento() {
        return codigo_evento;
    }

    public void setCodigo_evento(Integer codigo_evento) {
        this.codigo_evento = codigo_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    // MO2.1: equals y hashCode usando clave natural (codigo_evento)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventoMusical)) return false;
        EventoMusical that = (EventoMusical) o;
        return Objects.equals(codigo_evento, that.codigo_evento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo_evento);
    }

    @Override
    public String toString() {
        return "EventoMusical{" +
                "id_evento=" + id_evento +
                ", codigo_evento=" + codigo_evento +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", lugar='" + lugar + '\'' +
                '}';
    }
}
