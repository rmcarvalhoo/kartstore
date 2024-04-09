package br.com.kartstore.service.database.entity;

import br.com.kartstore.controller.sale.dto.ClientDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.Collection;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "CLIENT", schema = "ksdb", catalog = "")
public class ClientEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  @EqualsAndHashCode.Include
  private Integer id;

  @Basic
  @Column(name = "NAME", nullable = false, length = 200)
  private String name;

  @Basic
  @Column(name = "CPF", nullable = false, length = 50)
  private String cpf;

  @Basic
  @Column(name = "EMAIL", nullable = false, length = 50)
  private String email;

  @OneToMany(mappedBy = "clientByClientId")
  private Collection<SaleEntity> salesById;

  public ClientDTO mapClient(){
    return ClientDTO.builder()
            .id(id)
            .name(name)
            .cpf(cpf)
            .email(email)
            .build();
  }

}
