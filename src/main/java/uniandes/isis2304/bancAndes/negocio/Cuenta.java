package uniandes.isis2304.bancAndes.negocio;

public class Cuenta implements VOCuenta{
	
	protected Integer codigo;
	protected float dinero;
	protected String tipoDeCuenta;
	protected Boolean estaCerrada;


	public Cuenta(Integer codigo, float dinero, String tipoDeCuenta, Boolean estaCerrada) {
		super();
		this.codigo = codigo;
		this.dinero = dinero;
		this.tipoDeCuenta = tipoDeCuenta;
		this.estaCerrada = estaCerrada;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getTipoDeCuenta() {
		return tipoDeCuenta;
	}

	public void setTipoDeCuenta(String tipoDeCuenta) {
		this.tipoDeCuenta = tipoDeCuenta;
	}

	public Boolean getEstaCerrada() {
		return estaCerrada;
	}

	public void setEstaCerrada(Boolean estaCerrada) {
		this.estaCerrada = estaCerrada;
	}

	public float getDinero() {
		return dinero;
	}

	public void setDinero(float dinero) {
		this.dinero = dinero;
	}

	@Override
	public String toString() {
		return "Cuenta [codigo=" + codigo + ", dinero=" + dinero + ", tipoDeCuenta=" + tipoDeCuenta + ", estaCerrada="
				+ estaCerrada + "]";
	}

	
}
