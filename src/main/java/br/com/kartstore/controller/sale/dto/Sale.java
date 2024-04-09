package br.com.kartstore.controller.sale.dto;

import br.com.kartstore.service.database.entity.SaleEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Sale {

    @Null(message = "id may be null")
    private Integer id;

    @NotNull(message = "numControl may not be null")
    private String numControl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created = new Date();

    @NotNull(message = "productName may not be null")
    private String productName;

    @NotNull(message = "productValue may not be null")
    private BigDecimal productValue;

    @Positive(message = "amount must be greater than 0")
    private Long amount = 1L;

    @NotNull(message = "productValue may not be null")
    private Integer clientId;

}
