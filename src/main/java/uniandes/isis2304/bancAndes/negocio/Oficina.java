package uniandes.isis2304.bancAndes.negocio;

import java.util.List;

public class Oficina implements VOOficina{
	private String nombre;
	private String direccion;
	private List<PuestoDeAtencion> puestos;
	private Empleado gerente;
	
	public Oficina(String nombre, String direccion, List<PuestoDeAtencion> puestos, Empleado gerente) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.puestos = puestos;
		this.gerente = gerente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<PuestoDeAtencion> getPuestos() {
		return puestos;
	}

	public void setPuestos(List<PuestoDeAtencion> puestos) {
		this.puestos = puestos;
	}

	public Empleado getGerente() {
		return gerente;
	}

	public void setGerente(Empleado gerente) {
		this.gerente = gerente;
	}

	@Override
	public String toString() {
		return "Oficina [nombre=" + nombre + ", direccion=" + direccion + ", puestos=" + puestos + ", gerente="
				+ gerente + "]";
	}
	
	

}
