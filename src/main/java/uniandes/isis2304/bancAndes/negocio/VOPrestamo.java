package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public interface VOPrestamo {
	
	public String getIdPrestamo();

	public void setIdPrestamo(String idPrestamo);

	public String getTipoPrestamo();

	public void setTipoPrestamo(String tipoPrestamo);

	public float getMonto();

	public void setMonto(float monto);

	public float getInteres();

	public void setInteres(float interes);

	public Integer getNumCuotas();
	
	public void setNumCuotas(Integer numCuotas);

	public Date getDiaPago();

	public void setDiaPago(Date diaPago);

	public float getValorMinCuo();

	public void setValorMinCuo(float valorMinCuo);

	public Boolean getEstaCerrado();

	public void setEstaCerrado(Boolean estaCerrado);

	public float getDeudaActual();

	public void setDeudaActual(float deudaActual);

	public Empleado getEmpleadoEncargado();

	public void setEmpleadoEncargado(Empleado empleadoEncargado);

	public Cliente getCliente();

	public void setCliente(Cliente cliente);

	public Cuenta getCuentaPerteneciente();

	public void setCuentaPerteneciente(Cuenta cuentaPerteneciente);

	@Override
	public String toString();

}
