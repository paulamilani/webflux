package com.apirest.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.apirest.webflux.document.Playlist;
import com.apirest.webflux.services.PlaylistService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

@RestController
public class PlaylistController {

	@Autowired
	PlaylistService service;

	@GetMapping(value = "/Playlist")
	public Flux<Playlist> getPlaylist() {
		return service.findAll();
	}

	@GetMapping(value = "/Playlist/{id}")
	public Mono<Playlist> getPlaylistId(@PathVariable String id) {
		return service.findById(id);
	}

	@PostMapping(value = "/Playlist")
	public Mono<Playlist> save(@RequestBody Playlist playlist) {
		return service.save(playlist);
	}

	@GetMapping(value="/playlist/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
		public Flux<Tuple2<Long, Playlist>> getPlaylistByEvents(){

		Flux<Long> interval = Flux.interval((Duration.ofSeconds(10)));
		Flux<Playlist> events = service.findAll();
		System.out.println("Passou aqui events");
		return Flux.zip(interval, events);
	}

}
