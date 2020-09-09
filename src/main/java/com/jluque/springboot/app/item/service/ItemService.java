package com.jluque.springboot.app.item.service;

import java.util.List;

import com.jluque.springboot.app.item.model.Item;

public interface ItemService {

	public List<Item> findAll();

	public Item finById(Long id, Integer cantidad);
}
