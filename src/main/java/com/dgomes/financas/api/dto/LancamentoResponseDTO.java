package com.dgomes.financas.api.dto;

import com.dgomes.financas.model.entity.Usuario;
import com.dgomes.financas.model.enums.StatusLancamento;
import com.dgomes.financas.model.enums.TipoLancamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoResponseDTO(Long id, String descricao, Integer mes, Integer ano, BigDecimal valor, LocalDate data_cadastro, TipoLancamento tipo, StatusLancamento status) {
}
