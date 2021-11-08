package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public class CDT extends Cuenta implements VOCDT{
	
	private Date fecha;
	private String estado;
	
	public CDT(Integer codigo, float dinero, String tipoDeCuenta, Boolean estaCerrada, Date fecha, String estado) {
		super(codigo, dinero, tipoDeCuenta, estaCerrada);
		this.fecha = fecha;
		this.estado = estado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "CDT [fecha=" + fecha + ", estado=" + estado + "]";
	}
	
}
