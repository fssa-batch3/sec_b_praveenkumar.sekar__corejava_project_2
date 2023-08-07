package in.fssa.homebakery.interface_files;

public interface Base<T> {
	public abstract <T> T findAll();
	public abstract void create(T newT);
	public abstract void update(int id, T newT);
	public abstract void delete(int id);
	public abstract <T> T findById(int id);
}
