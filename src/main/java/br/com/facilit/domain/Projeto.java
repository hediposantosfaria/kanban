package br.com.facilit.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String status;

    private LocalDate inicioPrevisto;

    private LocalDate terminoPrevisto;

    private LocalDate inicioRealizado;

    private LocalDate terminoRealizado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private Responsavel responsavel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getInicioPrevisto() {
        return inicioPrevisto;
    }

    public void setInicioPrevisto(LocalDate inicioPrevisto) {
        this.inicioPrevisto = inicioPrevisto;
    }

    public LocalDate getTerminoPrevisto() {
        return terminoPrevisto;
    }

    public void setTerminoPrevisto(LocalDate terminoPrevisto) {
        this.terminoPrevisto = terminoPrevisto;
    }

    public LocalDate getInicioRealizado() {
        return inicioRealizado;
    }

    public void setInicioRealizado(LocalDate inicioRealizado) {
        this.inicioRealizado = inicioRealizado;
    }

    public LocalDate getTerminoRealizado() {
        return terminoRealizado;
    }

    public void setTerminoRealizado(LocalDate terminoRealizado) {
        this.terminoRealizado = terminoRealizado;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }
}
