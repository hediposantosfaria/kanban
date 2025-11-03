package br.com.facilit.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjetoRequest {
    @NotBlank
    private String nome;
    private String status;
    private LocalDate inicioPrevisto;
    private LocalDate terminoPrevisto;
    private LocalDate inicioRealizado;
    private LocalDate terminoRealizado;
    private Long responsavelId;
}
