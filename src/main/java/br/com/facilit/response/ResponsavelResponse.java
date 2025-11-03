package br.com.facilit.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsavelResponse {
    private Long id;
    private String nome;
    private String email;
}
