package uniandes.isis2304.bancAndes.negocio;

import java.util.List;

public class Cliente extends Usuario implements VOCliente{
	
	private Integer idCliente;
	
	private String tipoCliente;
	
	private List<Cuenta> cuentas;
	
	private List<Prestamo> prestamos;

	

	public Cliente(String login, String palabClave, String documTipo, int documNum, String nombre,
			String nacionalidad, String direccFisica, String direccElectro, Integer telefono, String ciudad,
			String departamento, int codPostal,Integer idCliente, String tipoCliente, List<Cuenta> cuentas, List<Prestamo> prestamos) {
		super(login, palabClave, documTipo, documNum, nombre, nacionalidad, direccFisica, direccElectro, telefono,
				ciudad, departamento, codPostal);
		this.idCliente = idCliente;
		this.tipoCliente = tipoCliente;
		this.cuentas = cuentas;
		this.prestamos = prestamos;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", tipoCliente=" + tipoCliente + ", cuentas=" + cuentas
				+ ", prestamos=" + prestamos + "]";
	}

	
}
