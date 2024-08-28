package com.mitocode.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mitocode.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class RestTemplateController {
	
private final RestTemplate restTemplate;
	
	@GetMapping("/pokemon/{name}")
	public ResponseEntity<?>getPokemon(@PathVariable("name")String name){
		String pokemonUrl ="https://pokeapi.co/api/v2/pokemon/" + name;
		String response =restTemplate.getForEntity(pokemonUrl, String.class).getBody();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/test1")
	public ResponseEntity<List<CategoryDTO>>test1(){
		String url ="http://localhost:8080/categories";

		HttpHeaders headers =new HttpHeaders();
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> entity =new HttpEntity<>(headers);
		ParameterizedTypeReference<List<CategoryDTO>> typeRef =new ParameterizedTypeReference<>() {};
		
		return restTemplate.exchange(url, HttpMethod.GET, entity, typeRef);

  }
}
