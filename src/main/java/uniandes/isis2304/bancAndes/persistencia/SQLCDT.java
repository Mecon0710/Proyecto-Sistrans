package uniandes.isis2304.bancAndes.persistencia;

import java.sql.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

class SQLCDT {
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
	public SQLCDT (PersistenciaBancAndes pp)
	{
		this.pp = pp;
	}
	public long addCDT(PersistenceManager pm, Date fecha, String estado) 
	{
	    Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCDT () + "(fecha, estado) values (?, ?)");
	    q.setParameters(fecha, estado);
	    return (long) q.executeUnique();
	}
}
