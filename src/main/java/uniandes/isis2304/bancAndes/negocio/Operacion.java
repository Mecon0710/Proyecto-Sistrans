package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public class Operacion implements VOOperacion{
	protected Integer idOperacion;
	protected String tipo;
	protected float valor;
	protected Date horaYfecha;
	protected PuestoDeAtencion puestoDeAtencion;
	public Operacion(Integer idOperacion, String tipo, float valor, Date horaYfecha,
			PuestoDeAtencion puestoDeAtencion) {
		super();
		this.idOperacion = idOperacion;
		this.tipo = tipo;
		this.valor = valor;
		this.horaYfecha = horaYfecha;
		this.puestoDeAtencion = puestoDeAtencion;
	}
	public Integer getIdOperacion() {
		return idOperacion;
	}
	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public Date getHoraYfecha() {
		return horaYfecha;
	}
	public void setHoraYfecha(Date horaYfecha) {
		this.horaYfecha = horaYfecha;
	}
	public PuestoDeAtencion getPuestoDeAtencion() {
		return puestoDeAtencion;
	}
	public void setPuestoDeAtencion(PuestoDeAtencion puestoDeAtencion) {
		this.puestoDeAtencion = puestoDeAtencion;
	}
	@Override
	public String toString() {
		return "Operacion [idOperacion=" + idOperacion + ", tipo=" + tipo + ", valor=" + valor + ", horaYfecha="
				+ horaYfecha + ", puestoDeAtencion=" + puestoDeAtencion + "]";
	}
	
	
	
}
