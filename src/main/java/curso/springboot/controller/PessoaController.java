package curso.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.model.Telefone;
import curso.springboot.repository.PessoaRepositoy;
import curso.springboot.repository.TelefoneRepository;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepositoy pessoaRepositoy;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@GetMapping("/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoaIt = pessoaRepositoy.findAll();
		modelAndView.addObject("pessoas", pessoaIt);
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}

	@PostMapping("**/salvarpessoa")
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaRepositoy.save(pessoa);

		ModelAndView andView = new ModelAndView();
		andView.setViewName("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoaIt = pessoaRepositoy.findAll();
		andView.addObject("pessoas", pessoaIt);
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}

	@GetMapping("/listapessoas")
	public ModelAndView pessoas() {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoaIt = pessoaRepositoy.findAll();
		andView.addObject("pessoas", pessoaIt);
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}

	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {

		ModelAndView modelAndView = new ModelAndView();

		Optional<Pessoa> pessoa = pessoaRepositoy.findById(idpessoa);

		modelAndView.setViewName("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", pessoa.get());
		return modelAndView;
	}

	@GetMapping("/excluirpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {

		pessoaRepositoy.deleteById(idpessoa);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoaRepositoy.findAll());
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}

	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastro/cadastropessoa");

		modelAndView.addObject("pessoas", pessoaRepositoy.findPessoaByNome(nomepesquisa));
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}

	@GetMapping("**/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastro/telefones");

		Optional<Pessoa> pessoa = pessoaRepositoy.findById(idpessoa);

		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));
		return modelAndView;

	}

	@PostMapping("**/addfonePessoa/{pessoaid}")
	public ModelAndView addfonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastro/telefones");

		Pessoa pessoa = pessoaRepositoy.findById(pessoaid).get();

		telefone.setPessoa(pessoa);

		telefoneRepository.save(telefone);
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
		return modelAndView;
	}
	
	
	@GetMapping("**/excluirtelefone/{idtelefone}")
	public ModelAndView editartelefone(@PathVariable("idtelefone") Long idtelefone) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastro/telefones");
		
		Pessoa  pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
		
		telefoneRepository.deleteById(idtelefone);

		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
		return modelAndView;

	}
	
}
