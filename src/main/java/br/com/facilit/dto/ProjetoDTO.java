package br.com.facilit.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoDTO {

    private Long id;

    private String nome;

    private String status;

    private LocalDate inicioPrevisto;

    private LocalDate terminoPrevisto;

    private LocalDate inicioRealizado;

    private LocalDate terminoRealizado;

    private ResponsavelDTO responsavel;

}
