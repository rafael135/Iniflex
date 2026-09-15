package com.iniflex.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;
    private static BigDecimal salarioMinimo = BigDecimal.valueOf(1212.0);

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);

        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getSalarioFormatado() {
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        return formatter.format(salario);
    }

    public void receberAumento(Double aumentoPorcentagem) {
        this.salario = this.salario
                .add(this.salario.multiply(BigDecimal.valueOf(aumentoPorcentagem).divide(BigDecimal.valueOf(100))));
    }

    public String getFuncao() {
        return funcao;
    }

    public BigDecimal getSalarioEmSalariosMinimos() {
        return this.salario.divide(Funcionario.salarioMinimo, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return """
                Nome: %s
                Data de Nascimento: %s
                Salário: R$%s
                Função: %s
                """.formatted(this.getNome(), this.getDataNascimentoFormatada(), this.getSalarioFormatado(),
                this.getFuncao());
    }
}
