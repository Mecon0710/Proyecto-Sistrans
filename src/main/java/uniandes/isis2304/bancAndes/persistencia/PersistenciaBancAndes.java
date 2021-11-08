/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.bancAndes.persistencia;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.bancAndes.negocio.Usuario;

/**
 * Clase para el manejador de persistencia del proyecto Parranderos
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLTipoBebida y SQLVisitan, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Germán Bravo
 */
public class PersistenciaBancAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaBancAndes.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaBancAndes instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;
	
	private SQLUtil sqlUtil;
	private SQLAccion sqlAccion;
	private SQLCDT sqlCDT;
	private SQLCliente sqlCliente;
	private SQLCuenta sqlCuenta;
	private SQLDepositoDeInversion sqlDepositoDeInversion;
	private SQLEmpleado sqlEmpleado;
	private SQLOficina sqlOficina;
	private SQLOPCDT sqlOPCDT;
	private SQLOPCuenta sqlOPCuenta;
	private SQLOPDeposito sqlOPDeposito;
	private SQLOPPrestamo sqlOPPrestamo;
	private SQLOperacion sqlOperacion;
	private SQLPrestamo sqlPrestamo;
	private SQLPuestoDeAtencion sqlPuestoDeAtencion;
	private SQLUsuario sqlUsuario;
	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaBancAndes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Parranderos_sequence");
		tablas.add ("USUARIO");
		tablas.add ("ACCION");
		tablas.add ("CLIENTE");
		tablas.add ("EMPLEADO");
		tablas.add ("PUESTODEATENCION");
		tablas.add ("OFICINA");
		tablas.add ("CUENTA");
		tablas.add ("CDT");
		tablas.add ("DEPOSITODEINVERSION");
		tablas.add ("PRESTAMO");
		tablas.add ("OPERACION");
		tablas.add ("OPPRESTAMO");
		tablas.add ("OPCUENTA");
		tablas.add ("OPCDT");
		tablas.add ("OPDEPOSITO");
		
		
}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaBancAndes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaBancAndes existente - Patrón SINGLETON
	 */
	public static PersistenciaBancAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaBancAndes ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaBancAndes existente - Patrón SINGLETON
	 */
	public static PersistenciaBancAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaBancAndes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		
		sqlAccion = new SQLAccion(this);
		sqlCDT = new SQLCDT(this);
		sqlCliente = new SQLCliente(this);
		sqlCuenta = new SQLCuenta(this);
		sqlDepositoDeInversion = new SQLDepositoDeInversion(this);
		sqlEmpleado = new SQLEmpleado(this);
		sqlOficina = new SQLOficina(this);
		sqlOPCDT = new SQLOPCDT(this);
		sqlOPCuenta = new SQLOPCuenta(this);
		sqlOPDeposito = new SQLOPDeposito(this);
		sqlOPPrestamo = new SQLOPPrestamo(this);
		sqlOperacion = new SQLOperacion(this);
		sqlPrestamo = new SQLPrestamo(this);
		sqlPuestoDeAtencion = new SQLPuestoDeAtencion(this);
		sqlUsuario= new SQLUsuario(this);
		sqlUtil = new SQLUtil(this);
		
	}
	
	/*METODOS PARA DAR TABLAS*/
	
	public String darSeqBancandes() {
		return tablas.get(16);
	}
	
	public String darTablaAccion() {
		return tablas.get(2);
	}

	public String darTablaCDT() {
		return tablas.get(8);
	}

	public String darTablaCliente() {
		return tablas.get(3);
	}

	public String darTablaCuenta() {
		return tablas.get(7);
	}

	public String darTablaDepositoDeInversion() {
		return tablas.get(9);
	}

	public String darTablaEmpleado() {
		return tablas.get(4);
	}

	public String darTablaOficina() {
		return tablas.get(6);
	}

	public String darTablaOPCDT() {
		return tablas.get(14);
	}

	public String darTablaOPCuenta() {
		return tablas.get(13);
	}

	public String darTablaOPDeposito() {
		return tablas.get(15);
	}

	public String darTablaOPPrestamo() {
		return tablas.get(12);
	}

	public String darTablaOperacion() {
		return tablas.get(11);
	}

	public String darTablaPrestamo() {
		return tablas.get(10);
	}

	public String darTablaPuestoDeAtencion() {
		return tablas.get(5);
	}

	public String darTablaUsuario() {
		return tablas.get(0);
	}

	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los USUARIOS
	 *****************************************************************/
	public Usuario registrarUsuario(String login, String palabClave, String documTipo, Integer documNum, String nombre, String nacionalidad, String direccFisica, String direccElectro, Integer telefono, String ciudad, String departamento, Integer codPostal) {
		//sqlUsuario.addUsuario(login, palabClave, documTipo, documNum, nombre, nacionalidad, direccFisica, direccElectro, telefono, ciudad, departamento, codPostal);
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idUsuario = nextval ();
            long tuplasInsertadas = sqlUsuario.registrarUsuario( pm, idUsuario, login, palabClave, documTipo, documNum, nombre, nacionalidad, direccFisica, direccElectro, telefono, ciudad, departamento, codPostal);
            tx.commit();
            
            log.trace ("Inserción de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Usuario(login, palabClave, documTipo, documNum, nombre, nacionalidad, direccFisica, direccElectro, telefono, ciudad, departamento, codPostal);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
	}

	
	}

 }
