package br.com.alura.spring.data.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioDTO;
import br.com.alura.spring.data.orm.FuncionarioProjecao;

@Repository
//public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer>
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>, JpaSpecificationExecutor<Funcionario>{

	List<Funcionario> findByNome(String nome);
	
//	List<Funcionario> findByNameAndSalarioGreaterThanAndDataContratacao(String nome, BigDecimal salario, LocalDate data); Padrao Derived Querie
	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario >= :salario AND f.dataContratacao = :data") // Padrao JPQL
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, BigDecimal salario, LocalDate data);
	
	@Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data", nativeQuery = true) // Padrao Query Native
	List<Funcionario> findDataContratacaoMaior(LocalDate data);
	
	//@Query(value = "SELECT f.id, f.nome, f.salario from funcionarios f", nativeQuery = true) // projecoes não pode ser Derived Query
	//List<FuncionarioProjecao> findFuncionarioSalario()
	@Query("SELECT new br.com.alura.spring.data.orm.FuncionarioDTO(f.id, f.nome, f.salario) FROM Funcionario f")
	List<FuncionarioDTO> findFuncionarioSalario();
	
	
}
