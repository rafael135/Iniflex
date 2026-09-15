package com.iniflex;

import com.iniflex.model.Funcionario;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {

    private final List<Funcionario> funcionarios = new ArrayList<>();
    private final NumberFormat formatadorMoeda;

    public Principal() {
        this.formatadorMoeda = NumberFormat.getNumberInstance(Locale.of("pt", "BR"));
        this.formatadorMoeda.setMinimumFractionDigits(2);
        this.formatadorMoeda.setMaximumFractionDigits(2);
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
    }

    public List<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    public void removerFuncionarioPorNome(String nome) {
        this.funcionarios.removeIf(funcionario -> funcionario.getNome().equalsIgnoreCase(nome));
    }

    public void aplicarAumento(Double percentual) {
        for (Funcionario funcionario : this.funcionarios) {
            funcionario.receberAumento(percentual);
        }
    }

    public Map<String, List<Funcionario>> agruparPorFuncao() {
        return this.funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public List<Funcionario> filtrarPorMesesAniversario(int... meses) {
        List<Integer> mesesList = Arrays.stream(meses).boxed().toList();
        return this.funcionarios.stream()
                .filter(f -> mesesList.contains(f.getDataNascimento().getMonthValue()))
                .collect(Collectors.toList());
    }

    public Funcionario obterFuncionarioMaiorIdade() {
        return this.funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);
    }

    public List<Funcionario> obterFuncionariosOrdemAlfabetica() {
        return this.funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }

    public BigDecimal calcularTotalSalarios() {
        return this.funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void imprimirFuncionarios() {
        System.out.println("\n--- Lista de Funcionários ---");
        this.funcionarios.forEach(System.out::println);
    }

    public void imprimirFuncionariosAgrupadosPorFuncao() {
        System.out.println("\n--- Funcionários Agrupados por Função ---");
        Map<String, List<Funcionario>> agrupados = agruparPorFuncao();
        agrupados.forEach((funcao, lista) -> {
            System.out.println("\nFunção: " + funcao);
            lista.forEach(f -> System.out.println("  - " + f.getNome()));
        });
    }

    public void imprimirAniversariantes(int... meses) {
        List<Funcionario> aniversariantes = filtrarPorMesesAniversario(meses);
        String mesesFormatados = Arrays.toString(meses);
        System.out.println("\n--- Funcionários com Aniversário nos meses " + mesesFormatados + " ---");
        if (aniversariantes.isEmpty()) {
            System.out.println("Nenhum funcionário encontrado.");
        } else {
            aniversariantes.forEach(System.out::println);
        }
    }

    public void imprimirFuncionarioMaiorIdade() {
        System.out.println("\n--- Funcionário com Maior Idade ---");
        Funcionario maisVelho = obterFuncionarioMaiorIdade();
        if (maisVelho != null) {
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idade + " anos");
        } else {
            System.out.println("Nenhum funcionário cadastrado.");
        }
    }

    public void imprimirFuncionariosOrdemAlfabetica() {
        System.out.println("\n--- Funcionários em Ordem Alfabética ---");

        List<Funcionario> funcionariosOrdemAlfabetica = obterFuncionariosOrdemAlfabetica();

        for (Funcionario f : funcionariosOrdemAlfabetica) {
            System.out.println(f);
        }
    }

    public void imprimirTotalSalarios() {
        BigDecimal total = calcularTotalSalarios();
        System.out.println("\nTotal de salários: R$ " + formatadorMoeda.format(total));
    }

    public void imprimirSalariosEmSalariosMinimos() {
        System.out.println("\n--- Salários em Quantidade de Salários Mínimos (R$ 1.212,00) ---");
        for (Funcionario f : this.funcionarios) {
            System.out.println(f.getNome() + ": " + formatadorMoeda.format(f.getSalarioEmSalariosMinimos())
                    + " salários mínimos");
        }
    }

    public static void main(String[] args) {
        Principal app = new Principal();

        // 3.1 - Inserir todos os funcionários
        app.adicionarFuncionario(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador"));
        app.adicionarFuncionario(
                new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador"));
        app.adicionarFuncionario(
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), BigDecimal.valueOf(9836.14), "Coordenador"));
        app.adicionarFuncionario(
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), BigDecimal.valueOf(19119.88), "Diretor"));
        app.adicionarFuncionario(
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), BigDecimal.valueOf(2234.68), "Recepcionista"));
        app.adicionarFuncionario(
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), BigDecimal.valueOf(1582.72), "Operador"));
        app.adicionarFuncionario(
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), BigDecimal.valueOf(4071.84), "Contador"));
        app.adicionarFuncionario(
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), BigDecimal.valueOf(3017.45), "Gerente"));
        app.adicionarFuncionario(
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), BigDecimal.valueOf(1606.85), "Eletricista"));
        app.adicionarFuncionario(
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), BigDecimal.valueOf(2799.93), "Gerente"));

        // 3.2 - Remover João
        app.removerFuncionarioPorNome("João");

        // 3.3 - Imprimir todos os funcionários
        app.imprimirFuncionarios();

        // 3.4 - Aplicar 10% de aumento
        app.aplicarAumento(10.0);
        System.out.println("\n>>> Salários atualizados com 10% de aumento <<<");
        app.imprimirFuncionarios();

        // 3.5 e 3.6 - Agrupar e imprimir por função
        app.imprimirFuncionariosAgrupadosPorFuncao();

        // 3.8 - Aniversariantes dos meses 10 e 12
        app.imprimirAniversariantes(10, 12);

        // 3.9 - Funcionário com maior idade
        app.imprimirFuncionarioMaiorIdade();

        // 3.10 - Funcionários em ordem alfabetica
        app.imprimirFuncionariosOrdemAlfabetica();

        // 3.11 - Total dos salários
        app.imprimirTotalSalarios();

        // 3.12 - Salários mínimos
        app.imprimirSalariosEmSalariosMinimos();
    }
}