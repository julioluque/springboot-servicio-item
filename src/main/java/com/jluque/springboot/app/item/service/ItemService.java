package com.jluque.springboot.app.item.service;

import java.util.List;

import com.jluque.springboot.app.commons.models.entity.Producto;
import com.jluque.springboot.app.item.model.Item;

public interface ItemService {

	public List<Item> findAll();

	public Item finById(Long id, Integer cantidad);

	public Producto save(Producto producto);

	public Producto update(Producto producto, Long id);

	public void delete(Long id);
}
