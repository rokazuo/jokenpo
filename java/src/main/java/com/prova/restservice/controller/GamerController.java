package com.prova.restservice.controller;

import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prova.restservice.exception.GamerNotFoundException;
import com.prova.restservice.exception.ImpossibleToDetermineWinnersException;
import com.prova.restservice.exception.InvalidNumberOfGamersException;
import com.prova.restservice.model.Gamer;
import com.prova.restservice.repository.Repository;
import com.prova.restservice.service.GamerResourceAssemblerService;
import com.prova.restservice.service.PlayService;
import com.prova.restservice.validation.GamerValidator;;

@RestController
public class GamerController {

	private Repository repository;

	private final GamerResourceAssemblerService assembler;

	GamerController(GamerResourceAssemblerService assembler) {
		this.assembler = assembler;
	}

	@PostConstruct
	void atStartup() {
		repository = new Repository();
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new GamerValidator());
	}

	@GetMapping("/gamers/{name}")
	public Resource<Gamer> one(@PathVariable String name) throws URISyntaxException {
		Gamer gamer = repository.findById(name);
		if (gamer == null) {
			throw new GamerNotFoundException(name);
		}
		return assembler.toResource(gamer);
	}

	@PostMapping("/gamers")
	public Resource<Gamer> insertOrUpdate(@RequestBody @Valid Gamer newGamer) throws URISyntaxException {
		return assembler.toResource(repository.save(newGamer));
	}

	@DeleteMapping("/gamers/{name}")
	public Resource<Gamer> delete(@PathVariable String name) throws URISyntaxException {
		Gamer gamer = repository.deleteByName(name);
		if (gamer == null) {
			throw new GamerNotFoundException(name);
		}
		return assembler.toResource(gamer);
	}

	@GetMapping("/winner")
	List<Gamer> winner() {
		List<Gamer> gamerList = repository.findAll();
		List<Gamer> winnerList = null;
		if (gamerList.size() <= 1) {
			throw new InvalidNumberOfGamersException();
		} else {
			winnerList = PlayService.determineWinners(gamerList);
		}
		if (winnerList == null || winnerList.size() == 0) {
			throw new ImpossibleToDetermineWinnersException();
		}
		return winnerList;
	}

}