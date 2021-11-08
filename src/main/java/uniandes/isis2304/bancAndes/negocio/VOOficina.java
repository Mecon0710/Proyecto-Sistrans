package uniandes.isis2304.bancAndes.negocio;

import java.util.List;

public interface VOOficina {

	public String getNombre();
	
	public void setNombre(String nombre);

	public String getDireccion();

	public void setDireccion(String direccion);

	public List<PuestoDeAtencion> getPuestos();

	public void setPuestos(List<PuestoDeAtencion> puestos);

	public Empleado getGerente() ;

	public void setGerente(Empleado gerente);

	@Override
	public String toString();
}
