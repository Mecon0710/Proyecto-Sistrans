package uniandes.isis2304.bancAndes.negocio;

import java.util.List;

public interface VOCliente {

	public Integer getIdCliente();

	public void setIdCliente(Integer idCliente);

	public String getTipoCliente();

	public void setTipoCliente(String tipoCliente);
	
	public List<Cuenta> getCuentas();

	public void setCuentas(List<Cuenta> cuentas);

	public List<Prestamo> getPrestamos();

	public void setPrestamos(List<Prestamo> prestamos);

	@Override
	public String toString();
}
