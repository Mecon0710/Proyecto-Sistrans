package uniandes.isis2304.bancAndes.negocio;

import java.util.List;

public class Empleado extends Usuario implements VOEmpleado{
	
	private Integer idEmpleado;
	
	private PuestoDeAtencion puestoDeAtencion;
	
	private Oficina oficina;
	
	private List<Prestamo> prestamos;

	public Empleado(String login, String palabClave, String documTipo, int documNum, String nombre,
			String nacionalidad, String direccFisica, String direccElectro, Integer telefono, String ciudad,
			String departamento, int codPostal,Integer idEmpleado, PuestoDeAtencion puestoDeAtencion, Oficina oficina, List<Prestamo> prestamos) {
		super(login, palabClave, documTipo, documNum, nombre, nacionalidad, direccFisica, direccElectro, telefono,
				ciudad, departamento, codPostal);
		this.idEmpleado = idEmpleado;
		this.puestoDeAtencion = puestoDeAtencion;
		this.oficina = oficina;
		this.prestamos = prestamos;
	}

	public Integer getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Integer idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public PuestoDeAtencion getPuestoDeAtencion() {
		return puestoDeAtencion;
	}

	public void setPuestoDeAtencion(PuestoDeAtencion puestoDeAtencion) {
		this.puestoDeAtencion = puestoDeAtencion;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}

	@Override
	public String toString() {
		return "Empleado [idEmpleado=" + idEmpleado + ", puestoDeAtencion="
				+ puestoDeAtencion + ", oficina=" + oficina + ", prestamos=" + prestamos + "]";
	}
	
	

}
