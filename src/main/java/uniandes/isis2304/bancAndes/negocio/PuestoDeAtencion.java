package uniandes.isis2304.bancAndes.negocio;

public class PuestoDeAtencion implements VOPuestoDeAtencion{
	private Integer idPuesto;
	private String tipoPuesto;
	private String localizacion;
	private Oficina oficina;
	private Empleado empleado;
	public PuestoDeAtencion(Integer idPuesto, String tipoPuesto, String localizacion, Oficina oficina,
			Empleado empleado) {
		super();
		this.idPuesto = idPuesto;
		this.tipoPuesto = tipoPuesto;
		this.localizacion = localizacion;
		this.oficina = oficina;
		this.empleado = empleado;
	}
	public Integer getIdPuesto() {
		return idPuesto;
	}
	public void setIdPuesto(Integer idPuesto) {
		this.idPuesto = idPuesto;
	}
	public String getTipoPuesto() {
		return tipoPuesto;
	}
	public void setTipoPuesto(String tipoPuesto) {
		this.tipoPuesto = tipoPuesto;
	}
	public String getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	public Oficina getOficina() {
		return oficina;
	}
	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	@Override
	public String toString() {
		return "PuestoDeAtencion [idPuesto=" + idPuesto + ", tipoPuesto=" + tipoPuesto + ", localización="
				+ localizacion + ", oficina=" + oficina + ", empleado=" + empleado + "]";
	}

	
	
	
}
