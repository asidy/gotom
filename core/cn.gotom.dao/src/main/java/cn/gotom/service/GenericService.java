package cn.gotom.service;

import java.io.Serializable;
import java.util.List;

import cn.gotom.vo.Pagination;

/**
 * GenericDao to CRUD POJOs
 * 
 * @author <a href="mailto:qixere@qq.com">pei shaoguo</a>
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
public interface GenericService<T, PK extends Serializable> extends UniversalService
{
	boolean exist(PK id);

	List<T> find(int maxResults, int firstResult);

	List<T> findAll();

	T get(String name, Object value);

	T get(PK id);

	T getLast();

	long getCount();

	void removeById(PK id);

	void removeById(PK[] ids);
	
	void removeAll();

	T save(T value);

	Pagination<T> findPagination(int pageIndex, int pageSize);
}