package com.dgomes.financas.api.dto;

import java.math.BigDecimal;

public record LancamentoDTO(Long id,
                            String descricao,
                            Integer mes,
                            Integer ano,
                            BigDecimal valor,
                            Long usuario,
                            String tipo,
                            String status) {
}
