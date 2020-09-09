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
		Producto producto = clienteRest.getForObject("http://servicio-productos/ver/{id}", Producto.class, pathVariables);
		return new Item(producto, cantidad);
	}

}
