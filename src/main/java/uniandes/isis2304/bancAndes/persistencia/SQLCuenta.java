package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

class SQLCuenta {

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
	public SQLCuenta (PersistenciaBancAndes pp)
	{
		this.pp = pp;
	}
	
	public long addCuenta(PersistenceManager pm, long codigo, String tipoCuenta, String estaCerrada, long clienteTieneCuenta, double saldo) 
	{
	    Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCuenta () + "(codigo, tipoCuenta, estaCerrada, clienteTieneCuenta, saldo) values (?, ?, ?, ?, ?)");
	    q.setParameters(codigo, tipoCuenta, estaCerrada, clienteTieneCuenta, saldo);
	    return (long) q.executeUnique();
	}
}
