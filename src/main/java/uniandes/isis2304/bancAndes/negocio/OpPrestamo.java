package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public class OpPrestamo extends Operacion implements VOOPPrestamo{
	private Prestamo prestamo;

	public OpPrestamo(Integer idOperacion, String tipo, float valor, Date horaYfecha, PuestoDeAtencion puestoDeAtencion,
			Prestamo prestamo) {
		super(idOperacion, tipo, valor, horaYfecha, puestoDeAtencion);
		this.prestamo = prestamo;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	@Override
	public String toString() {
		return "OpPrestamo [prestamo=" + prestamo + "]";
	}
	

}
