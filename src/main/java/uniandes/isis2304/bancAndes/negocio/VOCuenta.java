package uniandes.isis2304.bancAndes.negocio;

public interface VOCuenta {

	public Integer getCodigo();

	public void setCodigo(Integer codigo);

	public String getTipoDeCuenta();

	public void setTipoDeCuenta(String tipoDeCuenta);

	public Boolean getEstaCerrada();

	public void setEstaCerrada(Boolean estaCerrada);
	
	public float getDinero();
	
	public void setDinero(float dinero);

	@Override
	public String toString();
}
