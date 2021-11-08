package uniandes.isis2304.bancAndes.negocio;

import java.util.List;

public interface VOEmpleado {

	public Integer getIdEmpleado();

	public void setIdEmpleado(Integer idEmpleado);

	public PuestoDeAtencion getPuestoDeAtencion();

	public void setPuestoDeAtencion(PuestoDeAtencion puestoDeAtencion);

	public Oficina getOficina();

	public void setOficina(Oficina oficina);

	public List<Prestamo> getPrestamos();

	public void setPrestamos(List<Prestamo> prestamos);

	@Override
	public String toString();
}
