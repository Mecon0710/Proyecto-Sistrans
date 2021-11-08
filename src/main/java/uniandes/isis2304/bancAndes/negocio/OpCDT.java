package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public class OpCDT extends Operacion implements VOOPCDT{
	
	private CDT cDT;

	public OpCDT(Integer idOperacion, String tipo, float valor, Date horaYfecha, PuestoDeAtencion puestoDeAtencion,
			CDT cDT) {
		super(idOperacion, tipo, valor, horaYfecha, puestoDeAtencion);
		this.cDT = cDT;
	}

	public CDT getcDT() {
		return cDT;
	}

	public void setcDT(CDT cDT) {
		this.cDT = cDT;
	}
	
	

}
