package com.sopra.agile.cardio.service;

import java.util.List;

public interface BaseService<T> {

	List<T> all();

	T find(String id);

	T add(T task);

}
