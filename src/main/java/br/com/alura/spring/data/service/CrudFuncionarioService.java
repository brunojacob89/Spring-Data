package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	private Boolean system = true;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final CargoRepository cargoRepository;
	private final FuncionarioRepository funcionarioRepository;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

	public CrudFuncionarioService(CargoRepository cargoRepository, FuncionarioRepository funcionarioRepository,
			UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
		this.cargoRepository = cargoRepository;
		this.funcionarioRepository = funcionarioRepository;
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
	}

	public void inicial(Scanner scanner) {
		while (system) {
			System.out.println("Qual acao de cargo deseja executar ");
			System.out.println("0 - Sair");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Visualizar");
			System.out.println("4 - Deletar");

			int action = scanner.nextInt();

			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				visualizar(scanner);
				break;
			case 4:
				deletar(scanner);
				break;
			default:
				system = false;
				break;
			}
		}

	}

	private void salvar(Scanner scanner) {
		System.out.println("Digite seu nome");
		String nome = scanner.nextLine().toUpperCase();
		nome += scanner.nextLine().toUpperCase();
		nome += scanner.nextLine().toUpperCase();
		
		System.out.println("Digite seu CPF");
		String cpf = scanner.nextLine().toUpperCase();
				
		System.out.println("Digite o Salario");
		String salario = scanner.nextLine().toUpperCase();
		BigDecimal salarioFinal = new BigDecimal(salario);
		
		System.out.println("Digite a Data da Contratação");
		String dataContratacao = scanner.nextLine().toUpperCase();
		
		System.out.println("Digite o cargoId");
		Integer cargoId = scanner.nextInt();
		
		List<UnidadeTrabalho> unidades = unidade(scanner);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salarioFinal);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		funcionario.setUnidadeTrabalhos(unidades);
		
		funcionarioRepository.save(funcionario);
		System.out.println("Salvo");
	}

	private List<UnidadeTrabalho> unidade(Scanner scanner) {

		Boolean isTrue = true;
		List<UnidadeTrabalho> unidades = new ArrayList<>();
		
		while(isTrue) {
			System.out.println("digite a unidadeId (Para Sair digite 0)");
			Integer unidadeId = scanner.nextInt();
			
			if(unidadeId != 0) {
				Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
				unidades.add(unidade.get());
			}else {
				isTrue = false;
			}
		}
		return unidades;
	}

	private void atualizar(Scanner scanner) {
		System.out.println("Digite id");
		Integer id = scanner.nextInt();
		
		System.out.println("Digite seu nome");
		String nome = scanner.nextLine().toUpperCase();
		nome += scanner.nextLine().toUpperCase();
		
		System.out.println("Digite seu CPF");
		String cpf = scanner.nextLine().toUpperCase();
		cpf += scanner.nextLine().toUpperCase();
		
		System.out.println("Digite o Salario");
		String salario = scanner.nextLine().toUpperCase();
		salario += scanner.nextLine().toUpperCase();
		BigDecimal salarioFinal = new BigDecimal(salario);
		
		System.out.println("Digite a Data da Contratação");
		String dataContratacao = scanner.nextLine().toUpperCase();
		dataContratacao += scanner.nextLine().toUpperCase();
		
		System.out.println("Digite o cargoId");
		Integer cargoId = scanner.nextInt();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salarioFinal);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		
		funcionarioRepository.save(funcionario);
		System.out.println("Alterado");
	}
	
//	public void visualizar() {
//		Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
//		funcionarios.forEach(funcionario -> System.out.println(funcionario));
//	}
	
	public void visualizar(Scanner scanner) {
		System.out.println("Qual pagina você deseja visualizar");
		Integer page = scanner.nextInt();
		
		Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "nome"));
		
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
		
		System.out.println(funcionarios);
		System.out.println("Pagina Atual " + funcionarios.getNumber());
		System.out.println("Total  elementos" + funcionarios.getTotalElements());
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
	}
	
	public void deletar(Scanner scanner) {
		
		System.out.println("Digite o ID");
		Integer id = scanner.nextInt();
		funcionarioRepository.deleteById(id);
		System.out.println("Funcionario deletado");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
