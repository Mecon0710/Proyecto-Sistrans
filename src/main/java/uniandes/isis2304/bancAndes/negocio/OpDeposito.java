package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public class OpDeposito extends Operacion implements VOOPDeposito{
	DepositoDeInversion depositoDeInversion;

	public OpDeposito(Integer idOperacion, String tipo, float valor, Date horaYfecha, PuestoDeAtencion puestoDeAtencion,
			DepositoDeInversion depositoDeInversion) {
		super(idOperacion, tipo, valor, horaYfecha, puestoDeAtencion);
		this.depositoDeInversion = depositoDeInversion;
	}

	public DepositoDeInversion getDepositoDeInversion() {
		return depositoDeInversion;
	}

	public void setDepositoDeInversion(DepositoDeInversion depositoDeInversion) {
		this.depositoDeInversion = depositoDeInversion;
	}

	@Override
	public String toString() {
		return "OpDeposito [depositoDeInversion=" + depositoDeInversion + "]";
	}
	
	

}
