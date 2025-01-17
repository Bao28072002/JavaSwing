package dao;

import java.util.List;

public interface DAOInterface<T> {

	public void insert(T t);

	public void update(T t);

	public T selectById(String id);

	public List<T> selectall();

	public List<T> selectid(String id);
}
