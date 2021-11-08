package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public class Prestamo implements VOPrestamo{
	private String idPrestamo;
	private String tipoPrestamo;
	private float monto;
	private float interes;
	private Integer numCuotas;
	private Date diaPago;
	private float valorMinCuo;
	private Boolean estaCerrado;
	private float deudaActual;
	private Empleado empleadoEncargado;
	private Cliente cliente;
	private Cuenta cuentaPerteneciente;
	
	public Prestamo(String idPrestamo, String tipoPrestamo, float monto, float interes, Integer numCuotas, Date diaPago,
			float valorMinCuo, Boolean estaCerrado, float deudaActual, Empleado empleadoEncargado, Cliente cliente,
			Cuenta cuentaPerteneciente) {
		super();
		this.idPrestamo = idPrestamo;
		this.tipoPrestamo = tipoPrestamo;
		this.monto = monto;
		this.interes = interes;
		this.numCuotas = numCuotas;
		this.diaPago = diaPago;
		this.valorMinCuo = valorMinCuo;
		this.estaCerrado = estaCerrado;
		this.deudaActual = deudaActual;
		this.empleadoEncargado = empleadoEncargado;
		this.cliente = cliente;
		this.cuentaPerteneciente = cuentaPerteneciente;
	}

	public String getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(String idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public String getTipoPrestamo() {
		return tipoPrestamo;
	}

	public void setTipoPrestamo(String tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public float getInteres() {
		return interes;
	}

	public void setInteres(float interes) {
		this.interes = interes;
	}

	public Integer getNumCuotas() {
		return numCuotas;
	}

	public void setNumCuotas(Integer numCuotas) {
		this.numCuotas = numCuotas;
	}

	public Date getDiaPago() {
		return diaPago;
	}

	public void setDiaPago(Date diaPago) {
		this.diaPago = diaPago;
	}

	public float getValorMinCuo() {
		return valorMinCuo;
	}

	public void setValorMinCuo(float valorMinCuo) {
		this.valorMinCuo = valorMinCuo;
	}

	public Boolean getEstaCerrado() {
		return estaCerrado;
	}

	public void setEstaCerrado(Boolean estaCerrado) {
		this.estaCerrado = estaCerrado;
	}

	public float getDeudaActual() {
		return deudaActual;
	}

	public void setDeudaActual(float deudaActual) {
		this.deudaActual = deudaActual;
	}

	public Empleado getEmpleadoEncargado() {
		return empleadoEncargado;
	}

	public void setEmpleadoEncargado(Empleado empleadoEncargado) {
		this.empleadoEncargado = empleadoEncargado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cuenta getCuentaPerteneciente() {
		return cuentaPerteneciente;
	}

	public void setCuentaPerteneciente(Cuenta cuentaPerteneciente) {
		this.cuentaPerteneciente = cuentaPerteneciente;
	}

	@Override
	public String toString() {
		return "Prestamo [idPrestamo=" + idPrestamo + ", tipoPrestamo=" + tipoPrestamo + ", monto=" + monto
				+ ", interes=" + interes + ", numCuotas=" + numCuotas + ", diaPago=" + diaPago + ", valorMinCuo="
				+ valorMinCuo + ", estaCerrado=" + estaCerrado + ", deudaActual=" + deudaActual + ", empleadoEncargado="
				+ empleadoEncargado + ", cliente=" + cliente + ", cuentaPerteneciente=" + cuentaPerteneciente + "]";
	}
	
	

}
