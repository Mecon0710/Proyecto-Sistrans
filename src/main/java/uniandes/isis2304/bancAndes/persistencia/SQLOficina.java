package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

class SQLOficina {
	
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
	public SQLOficina (PersistenciaBancAndes pp)
	{
		this.pp = pp;
	}
	public long addOficina(PersistenceManager pm, String nombre, String direccion, long empleadoDeOficina) 
	{
	    Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEmpleado () + "(nombre, direccion, empleadoDeOficina) values (?, ?, ?)");
	    q.setParameters(nombre, direccion, empleadoDeOficina);
	    return (long) q.executeUnique();
}
}
