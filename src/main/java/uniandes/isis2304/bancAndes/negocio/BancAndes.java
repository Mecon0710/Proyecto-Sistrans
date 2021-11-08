package uniandes.isis2304.bancAndes.negocio;
/** Tener los requerimientos funcionales de Bancandes */


import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import uniandes.isis2304.bancAndes.persistencia.PersistenciaBancAndes;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 */
public class BancAndes {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(BancAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaBancAndes pb;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public BancAndes ()
	{
		pb = PersistenciaBancAndes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public BancAndes (JsonObject tableConfig)
	{
		pb = PersistenciaBancAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pb.cerrarUnidadPersistencia ();
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los USUARIOS
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un tipo de bebida 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
	 */
	public Usuario registrarUsuario (String login, String palabClave, String documTipo, Integer documNum, String nombre, String nacionalidad, String direccFisica, String direccElectro, Integer telefono, String ciudad, String departamento, Integer codPostal)
	{
        log.info ("Registrando un usuario: " + nombre);
        Usuario usuario = pb.registrarUsuario (login, palabClave, documTipo, documNum, nombre, nacionalidad, direccFisica, direccElectro, telefono, ciudad, departamento, codPostal);		
        log.info ("Adicionando Usuario: " + usuario);
        return usuario;
	}
	
	}

