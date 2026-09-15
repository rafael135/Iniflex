package com.iniflex.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioTest {

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario(
                "Maria",
                LocalDate.of(2000, 10, 18),
                BigDecimal.valueOf(2009.44),
                "Operador");
    }

    @Test
    @DisplayName("Deve formatar a data de nascimento para o padrão dd/MM/yyyy")
    void deveFormatarDataDeNascimento() {
        assertEquals("18/10/2000", funcionario.getDataNascimentoFormatada());
    }

    @Test
    @DisplayName("Deve formatar o salário corretamente utilizando a notação brasileira")
    void deveFormatarSalarioCorretamente() {
        assertEquals("2.009,44", funcionario.getSalarioFormatado());
    }

    @Test
    @DisplayName("Deve aplicar aumento de salário com precisão correta")
    void deveAplicarAumentoCorretamente() {
        funcionario.receberAumento(10.0);

        BigDecimal esperado = BigDecimal.valueOf(2009.44)
                .add(BigDecimal.valueOf(2009.44).multiply(BigDecimal.valueOf(0.10)));

        assertEquals(0, esperado.compareTo(funcionario.getSalario()));
    }

    @Test
    @DisplayName("Deve calcular a quantidade de salários mínimos corretamente")
    void deveCalcularQuantidadeDeSalariosMinimosCorretamente() {
        BigDecimal esperado = BigDecimal.valueOf(2009.44)
                .divide(BigDecimal.valueOf(1212.0), RoundingMode.HALF_UP);

        assertEquals(esperado, funcionario.getSalarioEmSalariosMinimos());
    }
}