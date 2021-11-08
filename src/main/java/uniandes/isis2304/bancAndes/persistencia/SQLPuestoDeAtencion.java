package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLPuestoDeAtencion {
	
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
	public SQLPuestoDeAtencion (PersistenciaBancAndes pp)
	{
		this.pp = pp;
	}
	
	public long addPuestoDeAtencion(PersistenceManager pm, long idPuesto, String tipoDePuesto, String localizacion, long puestoAtiendeEmpleado, String oficinaDePuesto) 
	{
	    Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPuestoDeAtencion () + "(idPuesto, tipoDePuesto, localizacion, puestoAtiendeEmpleado, oficinaDePuesto) values (?, ?, ?, ?, ?)");
	    q.setParameters(idPuesto, tipoDePuesto, localizacion, puestoAtiendeEmpleado, oficinaDePuesto);
	    return (long) q.executeUnique();
}
}
