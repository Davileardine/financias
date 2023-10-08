package com.dgomes.financas.api.dto;

import lombok.Builder;

@Builder
public record UsuarioResponseDTO(String nome, String email, String senha){
}
