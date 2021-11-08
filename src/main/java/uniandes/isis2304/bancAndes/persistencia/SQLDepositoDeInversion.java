package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

class SQLDepositoDeInversion {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaBancAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaBancAndes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLDepositoDeInversion (PersistenciaBancAndes pp)
	{
		this.pp = pp;
	}
	
	public long addDeposito(PersistenceManager pm, long depositoDeInversionEsCuenta) 
	{
	    Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCuenta () + "(depositoDeInversionEsCuenta) values (?)");
	    q.setParameters(depositoDeInversionEsCuenta);
	    return (long) q.executeUnique();

}
}
