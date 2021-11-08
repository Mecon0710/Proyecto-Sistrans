package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

class SQLEmpleado {
	
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

	public SQLEmpleado(PersistenciaBancAndes pp) {
		super();
		this.pp = pp;
	}
	
	public long addEmpleado(PersistenceManager pm, long idEmpleado, String tipoEmpleado, long numDocumEmpleado, long idClienteEmpleado) 
{
    Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEmpleado () + "(idEmpleado, tipoEmpleado, numDocumEmpleado, idClienteEmpleado) values (?, ?, ?, ?, ?)");
    q.setParameters(idEmpleado, tipoEmpleado, numDocumEmpleado, idClienteEmpleado);
    return (long) q.executeUnique();
}


}
