package com.prova.restservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prova.restservice.model.Gamer;
import com.prova.restservice.model.MoveType;
import com.prova.restservice.repository.Repository;

@SpringBootTest
@AutoConfigureMockMvc
public class GamerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getJoao() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		repository.save(new Gamer("Joao", MoveType.Rock));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/gamers/Joao")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Joao")).andExpect(jsonPath("$.move").value("Rock"));
	}

	@Test
	public void getGamerNotFound() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		repository.save(new Gamer("Joao", MoveType.Rock));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/gamers/Maria")).andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void insertJoao() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		Gamer gamer = new Gamer("Joao", MoveType.Paper);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/gamers").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(gamer))).andDo(print()).andExpect(status().isOk());
		Gamer expectedGamer = repository.findById("Joao");
		assertEquals(expectedGamer, gamer);
	}

	@Test
	public void insertBadParamNameParam() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		Gamer gamer = new Gamer("", MoveType.Paper);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/gamers").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(gamer))).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void insertBadParamMoveParam() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		Gamer gamer = new Gamer("Joao", MoveType.Invalid);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/gamers").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(gamer))).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void deleteJoao() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		Gamer gamer = new Gamer("Joao", MoveType.Paper);
		repository.save(gamer);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/gamers/Joao")).andDo(print()).andExpect(status().isOk());
		Gamer expectedGamer = repository.findById("Joao");
		assertEquals(expectedGamer, null);
	}

	@Test
	public void deleteNotFound() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/gamers/Joao")).andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void winnerInvalidNumberOfGamers() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		repository.save(new Gamer("Joao", MoveType.Rock));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/winner")).andDo(print())
				.andExpect(status().is5xxServerError());
	}

	@Test
	public void noWinner() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		repository.save(new Gamer("Joao", MoveType.Rock));
		repository.save(new Gamer("Maria", MoveType.Paper));
		repository.save(new Gamer("Pedro", MoveType.Scissor));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/winner")).andDo(print())
				.andExpect(status().is5xxServerError());
	}

	@Test
	public void scissorWinner() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		repository.save(new Gamer("Maria", MoveType.Paper));
		repository.save(new Gamer("Pedro", MoveType.Scissor));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/winner")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Pedro")).andExpect(jsonPath("$[0].move").value("Scissor"));
	}

	@Test
	public void paperWinner() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		repository.save(new Gamer("Maria", MoveType.Rock));
		repository.save(new Gamer("Pedro", MoveType.Paper));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/winner")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Pedro")).andExpect(jsonPath("$[0].move").value("Paper"));
	}

	@Test
	public void rockWinner() throws Exception {
		Repository repository = new Repository();
		repository.deleteAll();
		repository.save(new Gamer("Andrezza", MoveType.Rock));
		repository.save(new Gamer("Maria", MoveType.Scissor));
		repository.save(new Gamer("Pedro", MoveType.Rock));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/winner")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Andrezza")).andExpect(jsonPath("$[0].move").value("Rock"))
				.andExpect(jsonPath("$[1].name").value("Pedro")).andExpect(jsonPath("$[1].move").value("Rock"));
	}

	private String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
