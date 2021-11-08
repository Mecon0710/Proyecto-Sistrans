package uniandes.isis2304.bancAndes.negocio;

public interface VOPuestoDeAtencion {

	public Integer getIdPuesto();
	
	public void setIdPuesto(Integer idPuesto);
	
	public String getTipoPuesto();
	
	public void setTipoPuesto(String tipoPuesto);
	
	public String getLocalizacion();
	
	public void setLocalizacion(String localizacion);
	
	public Oficina getOficina();
	
	public void setOficina(Oficina oficina);
	
	public Empleado getEmpleado();
	
	public void setEmpleado(Empleado empleado);
	
	@Override
	public String toString();
}
