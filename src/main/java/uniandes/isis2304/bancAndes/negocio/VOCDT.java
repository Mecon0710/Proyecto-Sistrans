package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public interface VOCDT {

	public Date getFecha();
	
	public void setFecha(Date fecha);
	
	public String getEstado();
	
	public void setEstado(String estado);
	
	@Override
	public String toString();
}
