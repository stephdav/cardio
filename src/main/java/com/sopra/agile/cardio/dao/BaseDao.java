package com.sopra.agile.cardio.dao;

import java.util.List;

public interface BaseDao<T> {

	List<T> all();

	T find(String id);

	T add(T entity);

	void remove(String id);

}
