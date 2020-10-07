package com.jluque.springboot.app.item.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jluque.springboot.app.item.model.Item;
import com.jluque.springboot.app.item.model.Producto;

@Service("restTemplate")
@Primary
public class ItemServiceImpl implements ItemService {

	private static final Logger log = LoggerFactory.getLogger(ItemServiceFeign.class);

	@Autowired
	private RestTemplate clienteRest;

	@Override
	public List<Item> findAll() {
		List<Producto> productos = Arrays
				.asList(clienteRest.getForObject("http://servicio-productos/listar", Producto[].class));
		log.info("Metodo listar. Usando rest template");
		// retornar productos, no es compatibe, por eso convertimos productos en item...
		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item finById(Long id, Integer cantidad) {
		log.info("Metodo ver y cantidad. Usando rest template");
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());

		Producto producto = clienteRest.getForObject("http://servicio-productos/ver/{id}", Producto.class,
				pathVariables);
		return new Item(producto, cantidad);
	}

	@Override
	public Producto save(Producto producto) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Producto> body = new HttpEntity<Producto>(producto, headers);
		ResponseEntity<Producto> response = clienteRest.exchange("http://servicio-productos/crear", HttpMethod.POST,
				body, Producto.class);
		Producto productoResponse = response.getBody();
		return productoResponse;
	}

	@Override
	public Producto update(Producto producto, Long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, String> pathVariable = new HashMap<String, String>();
		pathVariable.put("id", id.toString());
		HttpEntity<Producto> body = new HttpEntity<Producto>(producto, headers);
		ResponseEntity<Producto> response = clienteRest.exchange("http://servicio-productos/editar/{id}",
				HttpMethod.PUT, body, Producto.class, pathVariable);
		return response.getBody();
	}

	@Override
	public void delete(Long id) {
		Map<String, String> pathVariable = new HashMap<String, String>();
		pathVariable.put("id", id.toString());
		clienteRest.delete("http://servicio-productos/borrar/{id}", pathVariable);

	}

}
