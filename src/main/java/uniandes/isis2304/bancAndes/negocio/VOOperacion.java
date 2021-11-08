package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public interface VOOperacion {
	public Integer getIdOperacion();
	
	public void setIdOperacion(Integer idOperacion);
	
	public String getTipo();
	
	public void setTipo(String tipo);
	
	public float getValor();
	
	public void setValor(float valor);
	
	public Date getHoraYfecha();
	
	public void setHoraYfecha(Date horaYfecha);
	
	public PuestoDeAtencion getPuestoDeAtencion();
	
	public void setPuestoDeAtencion(PuestoDeAtencion puestoDeAtencion);

	@Override
	public String toString();
}
