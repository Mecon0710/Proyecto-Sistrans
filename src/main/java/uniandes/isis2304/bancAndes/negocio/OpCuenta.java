package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public class OpCuenta extends Operacion implements VOOPCuenta{
	
	private Cuenta cuenta;

	public OpCuenta(Integer idOperacion, String tipo, float valor, Date horaYfecha, PuestoDeAtencion puestoDeAtencion,
			Cuenta cuenta) {
		super(idOperacion, tipo, valor, horaYfecha, puestoDeAtencion);
		this.cuenta = cuenta;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	@Override
	public String toString() {
		return "OpCuenta [cuenta=" + cuenta + "]";
	}
	
	
	
	

}
