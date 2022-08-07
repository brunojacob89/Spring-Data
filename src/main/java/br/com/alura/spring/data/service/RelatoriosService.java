package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioDTO;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	FuncionarioRepository funcionarioRepository;

	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {
		Boolean system = true;

		while (system) {
			System.out.println("Qual acao de cargo deseja executar ");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca Funcionario pelo nome");
			System.out.println("2 - Busca Funcionario pelo nome, salario, data da contratacao");
			System.out.println("3 - Busca Funcionario pela data de contratacao");
			System.out.println("4 - Busca Funcionario pelo salario");
		
			int action = scanner.nextInt();

			switch (action) {
			case 1:
				buscaFuncionarioNome(scanner);
				break;
			case 2:
				buscaFuncionarioNomeSalarioMaiorData(scanner);
				break;
			case 3:
				buscaFuncionarioDataContratacao(scanner);
				break;
			case 4:
				buscaFuncionarioSalario();
			default:
				system = false;
				break;
			}
		}
	}
	//BRUNO JACOB 2018-12-08 3459.00
	
	private void buscaFuncionarioNome(Scanner scanner) {
		System.out.println("Qual nome deseja pesquisar");
		String nome = scanner.nextLine().toUpperCase();
		nome += scanner.nextLine().toUpperCase();
		
		List<Funcionario> list = funcionarioRepository.findByNome(nome);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Qual nome deseja buscar");
		String nome = scanner.nextLine().toUpperCase();
		nome += scanner.nextLine().toUpperCase();
		
		System.out.println("Qual Salario  deseja buscar");
		String salario = scanner.nextLine().toUpperCase();
		BigDecimal salarioFinal = new BigDecimal(salario);
		
		System.out.println("Qual data de contratacao");
		String dataContratacao = scanner.nextLine().toUpperCase();
		LocalDate data = LocalDate.parse(dataContratacao, formatter);
		
		List<Funcionario> list = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salarioFinal, data);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioDataContratacao(Scanner scanner) {
		System.out.println("Qual data de contratacao");
		String dataContratacao = scanner.next();
		LocalDate data = LocalDate.parse(dataContratacao, formatter);
		
		List<Funcionario> list = funcionarioRepository.findDataContratacaoMaior(data);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioSalario() {
//		List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
		List<FuncionarioDTO> list = funcionarioRepository.findFuncionarioSalario();
		list.forEach(f -> System.out.println("Funcionario ID:" + f.getId() + " | Nome: " + f.getNome() + " | Salario" + f.getSalario()));
	}
}
