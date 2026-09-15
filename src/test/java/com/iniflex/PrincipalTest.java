package com.iniflex;

import com.iniflex.model.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PrincipalTest {

    private Principal principal;

    @BeforeEach
    void setUp() {
        principal = new Principal();

        principal.adicionarFuncionario(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador"));
        principal.adicionarFuncionario(
                new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador"));
        principal.adicionarFuncionario(
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), BigDecimal.valueOf(9836.14), "Coordenador"));
        principal.adicionarFuncionario(
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), BigDecimal.valueOf(19119.88), "Diretor"));
        principal.adicionarFuncionario(
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), BigDecimal.valueOf(2234.68), "Recepcionista"));
        principal.adicionarFuncionario(
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), BigDecimal.valueOf(1582.72), "Operador"));
        principal.adicionarFuncionario(
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), BigDecimal.valueOf(4071.84), "Contador"));
        principal.adicionarFuncionario(
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), BigDecimal.valueOf(3017.45), "Gerente"));
        principal.adicionarFuncionario(
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), BigDecimal.valueOf(1606.85), "Eletricista"));
        principal.adicionarFuncionario(
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), BigDecimal.valueOf(2799.93), "Gerente"));
    }

    @Test
    @DisplayName("Deve remover um funcionário pelo nome")
    void deveRemoverFuncionarioPorNome() {
        assertEquals(10, principal.getFuncionarios().size());

        principal.removerFuncionarioPorNome("João");

        assertEquals(9, principal.getFuncionarios().size());
        boolean joaoExiste = principal.getFuncionarios().stream()
                .anyMatch(f -> f.getNome().equalsIgnoreCase("João"));
        assertFalse(joaoExiste);
    }

    @Test
    @DisplayName("Deve aplicar um aumento de 10% em todos os funcionários da coleção")
    void deveAplicarAumento() {
        BigDecimal salarioInicialMaria = principal.getFuncionarios().get(0).getSalario();

        principal.aplicarAumento(10.0);

        BigDecimal salarioAtualizadoEsperado = salarioInicialMaria
                .add(salarioInicialMaria.multiply(BigDecimal.valueOf(0.10)));

        assertEquals(0, salarioAtualizadoEsperado.compareTo(principal.getFuncionarios().get(0).getSalario()));
    }

    @Test
    @DisplayName("Deve agrupar os  funcionários por função em um map")
    void deveAgruparFuncionariosPorFuncao() {
        Map<String, List<Funcionario>> agrupado = principal.agruparPorFuncao();

        assertTrue(agrupado.containsKey("Operador"));
        assertTrue(agrupado.containsKey("Gerente"));
        assertTrue(agrupado.containsKey("Coordenador"));

        assertEquals(3, agrupado.get("Operador").size());
        assertEquals(2, agrupado.get("Gerente").size());
        assertEquals(1, agrupado.get("Coordenador").size());
    }

    @Test
    @DisplayName("Deve filtrar os funcionários nascidos nos meses especificados (10 e 12)")
    void deveFiltrarFuncionariosNascidosNosMeses() {
        List<Funcionario> resultado = principal.filtrarPorMesesAniversario(10, 12);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(f -> f.getNome().equals("Maria")));
        assertTrue(resultado.stream().anyMatch(f -> f.getNome().equals("Miguel")));
    }

    @Test
    @DisplayName("Deve identificar o funcionário mais velho baseado na data de nascimento mais antiga")
    void deveRetornarFuncionarioMaisVelho() {
        Funcionario maisVelho = principal.obterFuncionarioMaiorIdade();

        assertNotNull(maisVelho);
        assertEquals("Caio", maisVelho.getNome());
        assertEquals(LocalDate.of(1961, 5, 2), maisVelho.getDataNascimento());
    }

    @Test
    @DisplayName("Deve ordernar os funcionários em ordem alfabética pelo nome")
    void deveOrdernarFuncionariosEmOrdemAlfabetica() {
        List<Funcionario> resultado = principal.obterFuncionariosOrdemAlfabetica();

        assertEquals(10, resultado.size());
        assertEquals("Alice", resultado.get(0).getNome());
        assertEquals("Arthur", resultado.get(1).getNome());
        assertEquals("Caio", resultado.get(2).getNome());
        assertEquals("Miguel", resultado.get(9).getNome());
    }

    @Test
    @DisplayName("Deve calcular corretamente a soma total de todos os salários")
    void deveCalcularTotalSalarios() {
        BigDecimal total = principal.calcularTotalSalarios();

        assertEquals(0, BigDecimal.valueOf(48563.31).compareTo(total));
    }
}