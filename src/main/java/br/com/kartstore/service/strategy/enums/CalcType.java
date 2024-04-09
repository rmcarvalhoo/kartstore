package br.com.kartstore.service.strategy.enums;

import lombok.Getter;

public enum CalcType {
    NOTHING(0L),
    FIVE(5L),
    TEN(10L);

    @Getter
    private Long cod;
    CalcType(Long cod) {
        this.cod = cod;
    }

    public static CalcType getByValue(Long vlr){
        if (vlr < CalcType.FIVE.getCod()) {
            return CalcType.NOTHING;
        } else if (vlr < CalcType.TEN.getCod()) {
            return CalcType.FIVE;
        } else {
            return CalcType.TEN;
        }
    }

}
