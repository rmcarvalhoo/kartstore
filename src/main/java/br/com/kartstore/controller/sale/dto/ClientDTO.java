package br.com.kartstore.controller.sale.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClientDTO {
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    private String cpf;
    private String email;
}
