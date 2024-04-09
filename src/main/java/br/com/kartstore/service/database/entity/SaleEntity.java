package br.com.kartstore.service.database.entity;

import br.com.kartstore.controller.sale.dto.Sale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "SALE", schema = "ksdb", catalog = "")
public class SaleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Integer id;

  @Basic
  @Column(name = "NUM_CONTROL", nullable = false, length = 200)
  private String numControl;

  @Basic
  @Column(name = "CREATED", nullable = false)
  private Date created;

  @Basic
  @Column(name = "PRODUCT_NAME", nullable = false, length = 50)
  private String productName;

  @Basic
  @Column(name = "PRODUCT_VALUE", nullable = false)
  private BigDecimal productValue;

  @Basic
  @Column(name = "AMOUNT", nullable = false)
  private Long amount;

  @Basic
  @Column(name = "CLIENT_ID", nullable = false)
  private Integer clientId;

  @Column(name = "ORDER_TOTAL", nullable = false)
  private BigDecimal orderTotal;

  @ManyToOne
  @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID", nullable = false, insertable=false, updatable=false)
  private ClientEntity clientByClientId;

  public Sale mapSale(){
         return Sale.builder()
            .numControl(numControl)
            .created(created)
            .productName(productName)
            .productValue(productValue)
            .amount(amount)
            .clientId(clientId)
            .build();
  }

}
