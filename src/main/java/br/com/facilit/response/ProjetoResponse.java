package br.com.facilit.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjetoResponse {
    private Long id;
    private String nome;
    private String status;
    private LocalDate inicioPrevisto;
    private LocalDate terminoPrevisto;
    private LocalDate inicioRealizado;
    private LocalDate terminoRealizado;
    private Long responsavelId;
}
