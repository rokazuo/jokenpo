package com.prova.restservice.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URISyntaxException;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.prova.restservice.controller.GamerController;
import com.prova.restservice.model.Gamer;

@Component
public class GamerResourceAssemblerService implements ResourceAssembler<Gamer, Resource<Gamer>> {

	@Override
	public Resource<Gamer> toResource(Gamer gamer) {
		Resource<Gamer> resourceGamer = null;
		try {
			resourceGamer = new Resource<>(gamer,
					linkTo(methodOn(GamerController.class).one(gamer.getName())).withSelfRel(),
					linkTo(methodOn(GamerController.class).delete(gamer.getName())).withRel("gamers/" + gamer.getName()),
					linkTo(methodOn(GamerController.class).insertOrUpdate(gamer)).withRel("gamers"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return resourceGamer;
	}
}