package in.fssa.homebakery.interfaces;

import in.fssa.homebakery.exception.PersistanceException;

public interface Base<T> {
	public abstract <T> T findAll() throws PersistanceException;
	public abstract void create(T newT) throws PersistanceException;
	public abstract void update(int id, T newT) throws PersistanceException;
	public abstract void delete(int id) throws PersistanceException;
	public abstract <T> T findById(int id) throws PersistanceException;
}
