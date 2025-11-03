package br.com.facilit.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsavelRequest {
    @NotBlank
    private String nome;
    @Email
    @NotBlank
    private String email;
}
