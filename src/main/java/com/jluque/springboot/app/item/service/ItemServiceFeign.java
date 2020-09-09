package com.jluque.springboot.app.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.jluque.springboot.app.item.clientes.ProductoClienteRest;
import com.jluque.springboot.app.item.model.Item;

@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements ItemService {

	private static final Logger log = LoggerFactory.getLogger(ItemServiceFeign.class);

	@Autowired
	private ProductoClienteRest clienteFeign;

	@Override
	public List<Item> findAll() {
		log.info("Metodo listar. Usando cliente feign");
		return clienteFeign.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item finById(Long id, Integer cantidad) {
		log.info("Metodo ver y cantidad. Usando cliente feign");
		return new Item(clienteFeign.detalle(id), cantidad);
	}

}
