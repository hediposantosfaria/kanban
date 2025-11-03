package br.com.facilit.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ResponsavelDTO {

    private Long id;

    private String nome;

    private String email;

}
