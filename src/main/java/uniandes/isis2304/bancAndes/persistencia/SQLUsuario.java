package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.Usuario;

public class SQLUsuario {
	
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
	private PersistenciaBancAndes pb;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLUsuario (PersistenciaBancAndes pb)
	{
		this.pb = pb;
	}
	
	public long registrarUsuario(PersistenceManager pm, long idUsuario, String login, String palabClave, String documTipo, Integer documNum, String nombre, String nacionalidad, String direccFisica, String direccElectro, Integer telefono, String ciudad, String departamento, Integer codPostal) 
	{
	    Query q = pm.newQuery(SQL, "INSERT INTO " + pb.darTablaUsuario () + "(idUsuario, login, palabClave, documTipo, documNum, nombre, nacionalidad, direccFisica, direccElectro, telefono, ciudad, departamento, codPostal) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    q.setParameters(idUsuario, login, palabClave, documTipo, documNum, nombre, nacionalidad, direccFisica, direccElectro, telefono, ciudad, departamento, codPostal);
	    return (long) q.executeUnique();
}

}
