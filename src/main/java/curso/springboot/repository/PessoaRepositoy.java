package curso.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Pessoa;

@Repository
@Transactional
public interface PessoaRepositoy extends JpaRepository<Pessoa, Long>{

	@Query("select m from Pessoa m where m.nome like %?1% ")
	List<Pessoa> findPessoaByNome(String nome);
}
