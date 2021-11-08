

package uniandes.isis2304.bancAndes.interfazDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.bancAndes.interfazApp.PanelDatos;
import uniandes.isis2304.bancAndes.negocio.BancAndes;
import uniandes.isis2304.bancAndes.negocio.VOAccion;
import uniandes.isis2304.bancAndes.negocio.VOCDT;
import uniandes.isis2304.bancAndes.negocio.VOCliente;
import uniandes.isis2304.bancAndes.negocio.VOCuenta;
import uniandes.isis2304.bancAndes.negocio.VOEmpleado;
import uniandes.isis2304.bancAndes.negocio.VOOPCDT;
import uniandes.isis2304.bancAndes.negocio.VOOPCuenta;
import uniandes.isis2304.bancAndes.negocio.VOOPDeposito;
import uniandes.isis2304.bancAndes.negocio.VOOPPrestamo;
import uniandes.isis2304.bancAndes.negocio.VOOficina;
import uniandes.isis2304.bancAndes.negocio.VOOperacion;
import uniandes.isis2304.bancAndes.negocio.VOPrestamo;
import uniandes.isis2304.bancAndes.negocio.VOPuestoDeAtencion;
import uniandes.isis2304.bancAndes.negocio.VOUsuario;


@SuppressWarnings("serial")

public class InterfazBancAndesDemo extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuciÃ³n
	 */
	private static Logger log = Logger.getLogger(InterfazBancAndesDemo.class.getName());
	
	/**
	 * Ruta al archivo de configuraciÃ³n de la interfaz
	 */
	private final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigDemo.json"; 
	
	/**
	 * Ruta al archivo de configuraciÃ³n de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * AsociaciÃ³n a la clase principal del negocio.
     */
    private BancAndes parranderos;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuraciÃ³n de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacciÃ³n para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * MenÃº de la aplicaciÃ³n
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			MÃ©todos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicaciÃ³n. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazBancAndesDemo( )
    {
        // Carga la configuraciÃ³n de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz grÃ¡fica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        parranderos = new BancAndes (tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			MÃ©todos para la configuraciÃ³n de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuraciÃ³n para la aplicaciÃ³n, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuraciÃ³n deseada
     * @param archConfig - Archivo Json que contiene la configuraciÃ³n
     * @return Un objeto JSON con la configuraciÃ³n del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontrÃ³ un archivo de configuraciÃ³n vÃ¡lido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontrÃ³ un archivo de configuraciÃ³n vÃ¡lido");			
			JOptionPane.showMessageDialog(null, "No se encontrÃ³ un archivo de configuraciÃ³n de interfaz vÃ¡lido: " + tipo, "BancAndes App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * MÃ©todo para configurar el frame principal de la aplicaciÃ³n
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuraciÃ³n por defecto" );			
			titulo = "BancAndes APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuraciÃ³n indicada en el archivo de configuraciÃ³n" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * MÃ©todo para crear el menÃº de la aplicaciÃ³n con base em el objeto JSON leÃ­do
     * Genera una barra de menÃº y los menÃºs con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menÃ¹s deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// CreaciÃ³n de la barra de menÃºs
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// CreaciÃ³n de cada uno de los menÃºs
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// CreaciÃ³n de cada una de las opciones del menÃº
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    
	/* ****************************************************************
	 * 			Demos de TipoBebida
	 *****************************************************************/
    /**
     * DemostraciÃ³n de creaciÃ³n, consulta y borrado de Tipos de Bebida
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    
    public void demoTipoBebida( )
    {
    	try 
    	{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMaS estÃ¡n en el cÃ³digo
			String nombreTipoBebida = "Vino tinto";
			boolean errorTipoBebida = false;
			VOTipoBebida tipoBebida = parranderos.adicionarTipoBebida (nombreTipoBebida);
			if (tipoBebida == null)
			{
				tipoBebida = parranderos.darTipoBebidaPorNombre (nombreTipoBebida);
				errorTipoBebida = true;
			}
			List <VOTipoBebida> lista = parranderos.darVOTiposBebida();
			long tbEliminados = parranderos.eliminarTipoBebidaPorId (tipoBebida.getId ());
			
			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de creaciÃ³n y listado de TipoBebida\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			if (errorTipoBebida)
			{
				resultado += "*** Exception creando tipo de bebida !!\n";
				resultado += "*** Es probable que ese tipo de bebida ya existiera y hay restricciÃ³n de UNICIDAD sobre el nombre del tipo de bebida\n";
				resultado += "*** Revise el log de parranderos para mÃ¡s detalles\n";
			}
			resultado += "Adicionado el tipo de bebida con nombre: " + nombreTipoBebida + "\n";
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado +=  "\n" + listarTiposBebida (lista);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += tbEliminados + " Tipos de bebida eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	/* ****************************************************************
	 * 			Demos de Bebida
	 *****************************************************************/
    /**
     * DemostraciÃ³n de creaciÃ³n, consulta y borrado de Bebidas.
     * Incluye tambiÃ©n los tipos de bebida pues el tipo de bebida es llave forÃ¡nea en las bebidas
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    @SuppressWarnings ("unused")
	public void demoBebida( )
    {
    	try 
    	{
    		
			String nombreTipoBebida = "Vino tinto";
			String nombreBebida = "120";
			boolean errorTipoBebida = false;
			VOTipoBebida tipoBebida = parranderos.adicionarTipoBebida (nombreTipoBebida);
			if (tipoBebida == null)
			{
				tipoBebida = parranderos.darTipoBebidaPorNombre (nombreTipoBebida);
				errorTipoBebida = true;
			}
			VOBebida bebida = parranderos.adicionarBebida(nombreBebida, tipoBebida.getId (), 10);
			
			List <VOTipoBebida> listaTiposBebida = parranderos.darVOTiposBebida();
			List <VOBebida> listaBebidas = parranderos.darVOBebidas();
			long bebEliminadas = parranderos.eliminarBebidaPorNombre(nombreBebida);
			long tbEliminados = parranderos.eliminarTipoBebidaPorId (tipoBebida.getId ());
			
			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de creaciÃ³n y listado de Bebidas\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n\n";
			if (errorTipoBebida)
			{
				resultado += "*** Exception creando tipo de bebida !!\n";
				resultado += "*** Es probable que ese tipo de bebida ya existiera y hay restricciÃ³n de UNICIDAD sobre el nombre del tipo de bebida\n";
				resultado += "*** Revise el log de parranderos para mÃ¡s detalles\n";
			}
			resultado += "Adicionado el tipo de bebida con nombre: " + nombreTipoBebida + "\n";
			resultado += "Adicionada la bebida con nombre: " + nombreBebida + "\n";
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado +=  "\n" + listarTiposBebida (listaTiposBebida);
			resultado += "\n" + listarBebidas (listaBebidas);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += bebEliminadas + " Bebidas eliminadas\n";
			resultado += tbEliminados + " Tipos de bebida eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * DemostraciÃ³n de creaciÃ³n y borrado de bebidas no servidas.
     * Incluye tambiÃ©n los tipos de bebida pues el tipo de bebida es llave forÃ¡nea en las bebidas
     * Caso 1: Ninguna bebida es servida en ningÃºn bar: La tabla de bebidas queda vacÃ­a antes de la fase de limpieza
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    public void demoElimNoServidas1 ( )
    {
    	try 
    	{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			String nombreTipoBebida = "Vino tinto";
			boolean errorTipoBebida = false;
			VOTipoBebida tipoBebida = parranderos.adicionarTipoBebida (nombreTipoBebida);
			if (tipoBebida == null)
			{
				tipoBebida = parranderos.darTipoBebidaPorNombre (nombreTipoBebida);
				errorTipoBebida = true;
			}
			VOBebida bebida1 = parranderos.adicionarBebida("120", tipoBebida.getId (), 10);
			VOBebida bebida2 = parranderos.adicionarBebida("Gato Negro", tipoBebida.getId (), 11);
			VOBebida bebida3 = parranderos.adicionarBebida("Don Pedro", tipoBebida.getId (), 12);
			
			List <VOTipoBebida> listaTiposBebida = parranderos.darVOTiposBebida();
			List <VOBebida> listaBebidas1 = parranderos.darVOBebidas();
			List <VOSirven> listaSirven = parranderos.darVOSirven ();
			long noServidasEliminadas = parranderos.eliminarBebidasNoServidas();
			List <VOBebida> listaBebidas2 = parranderos.darVOBebidas();
			
			long bebEliminadas1 = parranderos.eliminarBebidaPorId(bebida1.getId ());
			long bebEliminadas2 = parranderos.eliminarBebidaPorId(bebida2.getId ());
			long bebEliminadas3 = parranderos.eliminarBebidaPorId(bebida3.getId ());
			long tbEliminados = parranderos.eliminarTipoBebidaPorNombre (nombreTipoBebida);
			
			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de borrado de las bebidas no servidas 1\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			if (errorTipoBebida)
			{
				resultado += "*** Exception creando tipo de bebida !!\n";
				resultado += "*** Es probable que ese tipo de bebida ya existiera y hay restricciÃ³n de UNICIDAD sobre el nombre del tipo de bebida\n";
				resultado += "*** Revise el log de parranderos para mÃ¡s detalles\n";
			}
			resultado +=  "\n" + listarTiposBebida (listaTiposBebida);
			resultado += "\n" + listarBebidas (listaBebidas1);
			resultado += "\n" + listarSirven (listaSirven);
			resultado += "\n\n************ Ejecutando la demo: Borrando bebidas no servidas ************ \n";
			resultado += noServidasEliminadas + " Bebidas eliminadas\n";
			resultado += "\n" + listarBebidas (listaBebidas2);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += (bebEliminadas1 + bebEliminadas2 + bebEliminadas3) + " Bebidas eliminadas\n";
			resultado += tbEliminados + " Tipos de bebida eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * DemostraciÃ³n de creaciÃ³n y borrado de bebidas no servidas.
     * Incluye tambiÃ©n los tipos de bebida pues el tipo de bebida es llave forÃ¡nea en las bebidas
     * Incluye tambiÃ©n los bares pues son llave forÃ¡nea en la relaciÃ³n Sirven
     * Caso 2: Hay bebidas que son servidas y estas quedan en la tabla de bebidas
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    public void demoElimNoServidas2 ( )
    {
    	try 
    	{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			String nombreTipoBebida = "Vino tinto";
			boolean errorTipoBebida = false;
			VOTipoBebida tipoBebida = parranderos.adicionarTipoBebida (nombreTipoBebida);
			if (tipoBebida == null)
			{
				tipoBebida = parranderos.darTipoBebidaPorNombre (nombreTipoBebida);
				errorTipoBebida = true;
			}
			VOBebida bebida1 = parranderos.adicionarBebida("120", tipoBebida.getId (), 10);
			VOBebida bebida2 = parranderos.adicionarBebida("Gato Negro", tipoBebida.getId (), 11);
			VOBebida bebida3 = parranderos.adicionarBebida("Don Pedro", tipoBebida.getId (), 12);
			VOBar bar1 = parranderos.adicionarBar ("Los Amigos", "BogotÃ¡", "Bajo", 2);
			parranderos.adicionarSirven (bar1.getId (), bebida1.getId (), "diurno");
			
			List <VOTipoBebida> listaTiposBebida = parranderos.darVOTiposBebida();
			List <VOBebida> listaBebidas1 = parranderos.darVOBebidas();
			List <VOBar> bares = parranderos.darVOBares ();
			List <VOSirven> sirven = parranderos.darVOSirven ();
			long noServidasEliminadas = parranderos.eliminarBebidasNoServidas();
			List <VOBebida> listaBebidas2 = parranderos.darVOBebidas();
			
			long sirvenEliminados = parranderos.eliminarSirven(bar1.getId (), bebida1.getId ());
			long bebEliminadas1 = parranderos.eliminarBebidaPorId(bebida1.getId ());
			long bebEliminadas2 = parranderos.eliminarBebidaPorId(bebida2.getId ());
			long bebEliminadas3 = parranderos.eliminarBebidaPorId(bebida3.getId ());
			long tbEliminados = parranderos.eliminarTipoBebidaPorNombre (nombreTipoBebida);
			long baresEliminados = parranderos.eliminarBarPorNombre ("Los Amigos");
			
			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de borrado de las bebidas no servidas 2\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			if (errorTipoBebida)
			{
				resultado += "*** Exception creando tipo de bebida !!\n";
				resultado += "*** Es probable que ese tipo de bebida ya existiera y hay restricciÃ³n de UNICIDAD sobre el nombre del tipo de bebida\n";
				resultado += "*** Revise el log de parranderos para mÃ¡s detalles\n";
			}
			resultado += "\n" + listarTiposBebida (listaTiposBebida);
			resultado += "\n" + listarBebidas (listaBebidas1);
			resultado += "\n" + listarBares (bares);
			resultado += "\n" + listarSirven (sirven);
			resultado += "\n\n************ Ejecutando la demo: Borrando bebidas no servidas ************ \n";
			resultado += noServidasEliminadas + " Bebidas eliminadas\n";
			resultado += "\n" + listarBebidas (listaBebidas2);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += sirvenEliminados + " Sirven eliminados\n";
			resultado += baresEliminados + " Bares eliminados\n";
			resultado += (bebEliminadas1 + bebEliminadas2 + bebEliminadas3) + " Bebidas eliminadas\n";
			resultado += tbEliminados + " Tipos de bebida eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
	/* ****************************************************************
	 * 			Demos de Bar
	 *****************************************************************/
    /**
     * DemostraciÃ³n de creaciÃ³n, consulta y borrado de Bares
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    public void demoBar ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			VOBar bar1 = parranderos.adicionarBar ("Los Amigos", "BogotÃ¡", "Bajo", 2);
			
			List <VOBar> lista = parranderos.darVOBares ();
			
			long baresEliminados = parranderos.eliminarBarPorNombre("Los Amigos");
			
			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de creaciÃ³n y listado de Bares\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			resultado += "Adicionado el bar: " + bar1 + "\n";
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += "\n" + listarBares (lista);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += baresEliminados + " Bares eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * DemostraciÃ³n de la consulta: Dar el id y el nÃºmero de bebidas que sirve cada bar, siempre y cuando el bar sirva por los menos una bebida
     * Incluye el manejo de los tipos de bebida pues el tipo de bebida es llave forÃ¡nea en las bebidas
     * Incluye el manajo de las bebidas
     * Incluye el manejo de los bares
     * Incluye el manejo de la relaciÃ³n sirven
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    public void demoBaresBebidas ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			boolean errorTipoBebida = false;
			VOTipoBebida tipoBebida = parranderos.adicionarTipoBebida ("Vino tinto");
			if (tipoBebida == null)
			{
				tipoBebida = parranderos.darTipoBebidaPorNombre ("Vino tinto");
				errorTipoBebida = true;
			}
			VOBebida bebida1 = parranderos.adicionarBebida ("120", tipoBebida.getId (), 10);
			VOBebida bebida2 = parranderos.adicionarBebida ("121", tipoBebida.getId (), 10);
			VOBebida bebida3 = parranderos.adicionarBebida ("122", tipoBebida.getId (), 10);
			VOBebida bebida4 = parranderos.adicionarBebida ("123", tipoBebida.getId (), 10);
			VOBebida bebida5 = parranderos.adicionarBebida ("124", tipoBebida.getId (), 10);
			VOBar bar1 = parranderos.adicionarBar ("Los Amigos1", "BogotÃ¡", "Bajo", 2);
			VOBar bar2 = parranderos.adicionarBar ("Los Amigos2", "BogotÃ¡", "Bajo", 3);
			VOBar bar3 = parranderos.adicionarBar ("Los Amigos3", "BogotÃ¡", "Bajo", 4);
			VOBar bar4 = parranderos.adicionarBar ("Los Amigos4", "MedellÃ­n", "Bajo", 5);
			parranderos.adicionarSirven (bar1.getId (), bebida1.getId (), "diurno");
			parranderos.adicionarSirven (bar1.getId (), bebida2.getId (), "diurno");
			parranderos.adicionarSirven (bar2.getId (), bebida1.getId (), "diurno");
			parranderos.adicionarSirven (bar2.getId (), bebida2.getId (), "diurno");
			parranderos.adicionarSirven (bar2.getId (), bebida3.getId (), "diurno");
			parranderos.adicionarSirven (bar3.getId (), bebida1.getId (), "diurno");
			parranderos.adicionarSirven (bar3.getId (), bebida2.getId (), "diurno");
			parranderos.adicionarSirven (bar3.getId (), bebida3.getId (), "diurno");
			parranderos.adicionarSirven (bar3.getId (), bebida4.getId (), "diurno");
			parranderos.adicionarSirven (bar3.getId (), bebida5.getId (), "diurno");
			
			List <VOTipoBebida> listaTiposBebida = parranderos.darVOTiposBebida ();
			List <VOBebida> listaBebidas = parranderos.darVOBebidas ();
			List <VOBar> listaBares = parranderos.darVOBares ();
			List <VOSirven> listaSirven = parranderos.darVOSirven ();

			List <long []> listaByB = parranderos.darBaresYCantidadBebidasSirven();

			long sirvenEliminados = parranderos.eliminarSirven (bar1.getId (), bebida1.getId ());
			sirvenEliminados += parranderos.eliminarSirven (bar1.getId (), bebida2.getId ());
			sirvenEliminados += parranderos.eliminarSirven (bar2.getId (), bebida1.getId ());
			sirvenEliminados += parranderos.eliminarSirven (bar2.getId (), bebida2.getId ());
			sirvenEliminados += parranderos.eliminarSirven (bar2.getId (), bebida3.getId ());
			sirvenEliminados += parranderos.eliminarSirven (bar3.getId (), bebida1.getId ());
			sirvenEliminados += parranderos.eliminarSirven (bar3.getId (), bebida2.getId ());
			sirvenEliminados += parranderos.eliminarSirven (bar3.getId (), bebida3.getId ());
			sirvenEliminados += parranderos.eliminarSirven (bar3.getId (), bebida4.getId ());
			sirvenEliminados += parranderos.eliminarSirven (bar3.getId (), bebida5.getId ());
			long bebidasEliminadas = parranderos.eliminarBebidaPorNombre ("120");
			bebidasEliminadas += parranderos.eliminarBebidaPorNombre ("121");
			bebidasEliminadas += parranderos.eliminarBebidaPorNombre ("122");
			bebidasEliminadas += parranderos.eliminarBebidaPorNombre ("123");
			bebidasEliminadas += parranderos.eliminarBebidaPorNombre ("124");
			long tbEliminados = parranderos.eliminarTipoBebidaPorNombre ("Vino tinto");
			long baresEliminados = parranderos.eliminarBarPorNombre ("Los Amigos1");
			baresEliminados += parranderos.eliminarBarPorNombre ("Los Amigos2");
			baresEliminados += parranderos.eliminarBarPorNombre ("Los Amigos3");
			baresEliminados += parranderos.eliminarBarPorId (bar4.getId ());
			
			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de creaciÃ³n y listado de Bares y cantidad de visitas que reciben\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			if (errorTipoBebida)
			{
				resultado += "*** Exception creando tipo de bebida !!\n";
				resultado += "*** Es probable que ese tipo de bebida ya existiera y hay restricciÃ³n de UNICIDAD sobre el nombre del tipo de bebida\n";
				resultado += "*** Revise el log de parranderos para mÃ¡s detalles\n";
			}
			resultado += "\n" + listarTiposBebida (listaTiposBebida);
			resultado += "\n" + listarBebidas (listaBebidas);
			resultado += "\n" + listarBares (listaBares);
			resultado += "\n" + listarSirven (listaSirven);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += "\n" + listarBaresYBebidas (listaByB);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += sirvenEliminados + " Sirven eliminados\n";
			resultado += bebidasEliminadas + " Bebidas eliminados\n";
			resultado += tbEliminados + " Tipos de Bebida eliminados\n";
			resultado += baresEliminados + " Bares eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * DemostraciÃ³n de la modificaciÃ³n: Aumentar en uno el nÃºmero de sedes de los bares de una ciudad
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    public void demoAumentarSedesBaresEnCiudad ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			VOBar bar1 = parranderos.adicionarBar ("Los Amigos1", "BogotÃ¡", "Bajo", 2);
			VOBar bar2 = parranderos.adicionarBar ("Los Amigos2", "BogotÃ¡", "Bajo", 3);
			VOBar bar3 = parranderos.adicionarBar ("Los Amigos3", "BogotÃ¡", "Bajo", 4);
			VOBar bar4 = parranderos.adicionarBar ("Los Amigos4", "MedellÃ­n", "Bajo", 5);
			List <VOBar> listaBares = parranderos.darVOBares ();
			
			long baresModificados = parranderos.aumentarSedesBaresCiudad("BogotÃ¡");
			List <VOBar> listaBares2 = parranderos.darVOBares ();

			long baresEliminados = parranderos.eliminarBarPorId (bar1.getId ());
			baresEliminados += parranderos.eliminarBarPorId (bar2.getId ());
			baresEliminados += parranderos.eliminarBarPorId (bar3.getId ());
			baresEliminados += parranderos.eliminarBarPorId (bar4.getId ());
			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo

			String resultado = "Demo de modificaciÃ³n nÃºmero de sedes de los bares de una ciudad\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			resultado += "\n" + listarBares (listaBares);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += baresModificados + " Bares modificados\n";
			resultado += "\n" + listarBares (listaBares2);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += baresEliminados + " Bares eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	/* ****************************************************************
	 * 			Demos de Bebedor
	 *****************************************************************/
    /**
     * DemostraciÃ³n de creaciÃ³n, consulta y borrado de Bebedores
     * Incluye la consulta por nombre y por id
     * Incluye el borrado por nombre
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    @SuppressWarnings ("unused")
	public void demoBebedor ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			VOBebedor bdor1 = parranderos.adicionarBebedor ("Pepito", "BogotÃ¡", "Alto");
			VOBebedor bdor2 = parranderos.adicionarBebedor ("Pepito", "MedellÃ­n", "Alto");
			VOBebedor bdor3 = parranderos.adicionarBebedor ("Pedrito", "Cali", "Alto");
			
			List <VOBebedor> bebedores = parranderos.darVOBebedores();
			VOBebedor bdor5 = parranderos.darBebedorPorId(bdor1.getId ());
			VOBebedor bdor6 = parranderos.darBebedorPorId(0);
			List <VOBebedor> pepitos = parranderos.darVOBebedoresPorNombre("Pepito");
			List <VOBebedor> pedritos = parranderos.darVOBebedoresPorNombre("Pedrito");
			
			long pepitosEliminados = parranderos.eliminarBebedorPorNombre ("Pepito");
			long pedritosEliminados = parranderos.eliminarBebedorPorNombre ("Pedrito");

			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de creaciÃ³n y listado de Bebedores\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			resultado += "\n" + listarBebedores (bebedores);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += "\nBuscando el bebedor con id " + bdor1 + ":\n";
			resultado += bdor5 != null ? "El bebedor es: " + bdor5 + "\n" : "Ese bebedor no existe\n";
			resultado += "\nBuscando el bebedor con id " + 0 + ":\n";
			resultado += bdor6 != null ? "El bebedor es: " + bdor6 + "\n" : "Ese bebedor no existe\n";
			resultado += "\nBuscando los bebedores con nombre Pepito:";
			resultado += "\n" + listarBebedores (pepitos);
			resultado += "\nBuscando los bebedores con nombre Pedrito:";
			resultado += "\n" + listarBebedores (pedritos);
			
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += pepitosEliminados + " Pepitos eliminados\n";
			resultado += pedritosEliminados + " Pedritos eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * DemostraciÃ³n de creaciÃ³n, consulta de TODA LA INFORMACIÃ–N de un bebedor y borrado de un bebedor y sus visitas
     * Incluye el manejo de tipos de bebida
     * Incluye el manejo de bebidas
     * Incluye el manejo de bares
     * Incluye el manejo de la relaciÃ³n sirven
     * Incluye el manejo de la relaciÃ³n gustan
     * Incluye el borrado de un bebedor y todas sus visitas
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    public void demoDarBebedorCompleto ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados.
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			boolean errorTipoBebida = false;
			VOTipoBebida tipoBebida = parranderos.adicionarTipoBebida ("Vino tinto");
			if (tipoBebida == null)
			{
				tipoBebida = parranderos.darTipoBebidaPorNombre ("Vino tinto");
				errorTipoBebida = true;
			}
			VOBebida bebida1 = parranderos.adicionarBebida ("120", tipoBebida.getId (), 10);
			VOBar bar1 = parranderos.adicionarBar ("Los Amigos", "BogotÃ¡", "Bajo", 2);
			VOBebedor bdor1 = parranderos.adicionarBebedor ("Pepito", "BogotÃ¡", "Alto");
			
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "diurno");
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "nocturno");
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "todos");
			parranderos.adicionarGustan (bdor1.getId (), bebida1.getId ());

			List <VOTipoBebida> listaTipos = parranderos.darVOTiposBebida();
			List <VOBebida> listaBebidas = parranderos.darVOBebidas();
			List <VOBar> listaBares = parranderos.darVOBares ();
			List <VOBebedor> bebedores = parranderos.darVOBebedores();
			List <VOGustan> listaGustan = parranderos.darVOGustan();
			List <VOVisitan> listaVisitan = parranderos.darVOVisitan();

			VOBebedor bdor2 = parranderos.darBebedorCompleto(bdor1.getId ());

			long gustanEliminados = parranderos.eliminarGustan (bdor1.getId (), bebida1.getId ());
			long bebidasEliminadas = parranderos.eliminarBebidaPorNombre ("120");
			long tiposEliminados = parranderos.eliminarTipoBebidaPorNombre ("Vino tinto");
			long [] bebedorVisitasEliminados = parranderos.eliminarBebedorYVisitas_v1 (bdor1.getId ());
			long baresEliminados = parranderos.eliminarBarPorNombre ("Los Amigos");

			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de creaciÃ³n y listado de toda la informaciÃ³n de un bebedor\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			if (errorTipoBebida)
			{
				resultado += "*** Exception creando tipo de bebida !!\n";
				resultado += "*** Es probable que ese tipo de bebida ya existiera y hay restricciÃ³n de UNICIDAD sobre el nombre del tipo de bebida\n";
				resultado += "*** Revise el log de parranderos para mÃ¡s detalles\n";
			}
			resultado += "\n" + listarTiposBebida (listaTipos);
			resultado += "\n" + listarBebidas (listaBebidas);
			resultado += "\n" + listarBares (listaBares);
			resultado += "\n" + listarBebedores (bebedores);
			resultado += "\n" + listarGustan (listaGustan);
			resultado += "\n" + listarVisitan (listaVisitan);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += "\nBuscando toda la informaciÃ³n del bebedor con id " + bdor1 + ":\n";
			resultado += bdor2 != null ? "El bebedor es: " + bdor2.toStringCompleto() + "\n" : "Ese bebedor no existe\n";	
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += gustanEliminados + " Gustan eliminados\n";
			resultado += bebidasEliminadas + " Bebidas eliminadas\n";
			resultado += tiposEliminados + " Tipos de Bebida eliminados\n";
			resultado += bebedorVisitasEliminados [0] + " Bebedores eliminados y " + bebedorVisitasEliminados [1] +" Visitas eliminadas\n";
			resultado += baresEliminados + " Bares eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
}

    /**
     * DemostraciÃ³n de creaciÃ³n, consulta de TODA LA INFORMACIÃ–N de un bebedor y borrado de un bebedor y sus visitas.
     * Si hay posibilidades de alguna incoherencia con esta operaciÃ³n NO SE BORRA NI EL BEBEDOR NI SUS VISITAS
     * Incluye el manejo de tipos de bebida
     * Incluye el manejo de bebidas
     * Incluye el manejo de bares
     * Incluye el manejo de la relaciÃ³n sirven
     * Incluye el manejo de la relaciÃ³n gustan
     * Incluye el borrado de un bebedor y todas sus visitas v1
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos queda con las tuplas que no se pudieron borrar: ES COHERENTE DE TODAS MANERAS
     */
    public void demoEliminarBebedorYVisitas_v1 ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados.
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			VOBebedor bdor1 = parranderos.adicionarBebedor ("Pepito", "BogotÃ¡", "Alto");
			VOBar bar1 = parranderos.adicionarBar ("Los Amigos", "BogotÃ¡", "Bajo", 2);
			boolean errorTipoBebida = false;
			VOTipoBebida tipoBebida = parranderos.adicionarTipoBebida ("Vino tinto");
			if (tipoBebida == null)
			{
				tipoBebida = parranderos.darTipoBebidaPorNombre ("Vino tinto");
				errorTipoBebida = true;
			}
			VOBebida bebida1 = parranderos.adicionarBebida ("120", tipoBebida.getId (), 10);
			
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "diurno");
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "nocturno");
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "todos");
			parranderos.adicionarGustan (bdor1.getId (), bebida1.getId ());

			List <VOTipoBebida> listaTipos = parranderos.darVOTiposBebida();
			List <VOBebida> listaBebidas = parranderos.darVOBebidas();
			List <VOBar> listaBares = parranderos.darVOBares ();
			List <VOBebedor> bebedores = parranderos.darVOBebedores();
			List <VOGustan> listaGustan = parranderos.darVOGustan();
			List <VOVisitan> listaVisitan = parranderos.darVOVisitan();

			VOBebedor bdor2 = parranderos.darBebedorCompleto(bdor1.getId ());

			// No se elimina la tupla de GUSTAN para estudiar la coherencia de las operaciones en la base de daatos
			long gustanEliminados = 0;
			long bebidasEliminadas = parranderos.eliminarBebidaPorNombre ("120");
			long tiposEliminados = parranderos.eliminarTipoBebidaPorNombre ("Vino tinto");
			long [] bebedorVisitasEliminados = parranderos.eliminarBebedorYVisitas_v1 (bdor1.getId ());
			long baresEliminados = parranderos.eliminarBarPorNombre ("Los Amigos");

			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de creaciÃ³n y listado de toda la informaciÃ³n de un bebedor\n";
			resultado += "Y DE BORRADO DE BEBEDOR Y VISITAS cuando el bebedor aÃºn estÃ¡ referenciado cuando se quiere borrar\n";
			resultado += "v1: No se borra NI el bebedor NI sus visitas";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			if (errorTipoBebida)
			{
				resultado += "*** Exception creando tipo de bebida !!\n";
				resultado += "*** Es probable que ese tipo de bebida ya existiera y hay restricciÃ³n de UNICIDAD sobre el nombre del tipo de bebida\n";
				resultado += "*** Revise el log de parranderos para mÃ¡s detalles\n";
			}
			resultado += "\n" + listarTiposBebida (listaTipos);
			resultado += "\n" + listarBebidas (listaBebidas);
			resultado += "\n" + listarBares (listaBares);
			resultado += "\n" + listarBebedores (bebedores);
			resultado += "\n" + listarGustan (listaGustan);
			resultado += "\n" + listarVisitan (listaVisitan);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += "\nBuscando toda la informaciÃ³n del bebedor con id " + bdor1 + ":\n";
			resultado += bdor2 != null ? "El bebedor es: " + bdor2.toStringCompleto() + "\n" : "Ese bebedor no existe\n";	
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += gustanEliminados + " Gustan eliminados\n";
			resultado += bebidasEliminadas + " Bebidas eliminadas\n";
			resultado += tiposEliminados + " Tipos de Bebida eliminados\n";
			resultado += bebedorVisitasEliminados [0] + " Bebedores eliminados y " + bebedorVisitasEliminados [1] +" Visitas eliminadas\n";
			resultado += baresEliminados + " Bares eliminados\n";
			resultado += "\n\n************ ATENCIÃ“N - ATENCIÃ“N - ATENCIÃ“N - ATENCIÃ“N ************ \n";
			resultado += "\nRecuerde que -1 registros borrados significa que hubo un problema !! \n";
			resultado += "\nREVISE EL LOG DE PARRANDEROS Y EL DE DATANUCLEUS \n";
			resultado += "\nNO OLVIDE LIMPIAR LA BASE DE DATOS \n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
}

    /**
     * DemostraciÃ³n de creaciÃ³n, consulta de TODA LA INFORMACIÃ–N de un bebedor y borrado de un bebedor y sus visitas
     * Si hay posibilidades de alguna incoherencia con esta operaciÃ³n SE BORRA LO AQUELLO QUE SEA POSIBLE, 
     * PERO CONSERVANDO LA COHERENCIA DE LA BASE DE DATOS
     * Incluye el manejo de tipos de bebida
     * Incluye el manejo de bebidas
     * Incluye el manejo de bares
     * Incluye el manejo de la relaciÃ³n sirven
     * Incluye el manejo de la relaciÃ³n gustan
     * Incluye el borrado de un bebedor y todas sus visitas v2
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos queda con las tuplas que no se pudieron borrar
     */
    public void demoEliminarBebedorYVisitas_v2 ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados.
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			VOBebedor bdor1 = parranderos.adicionarBebedor ("Pepito", "BogotÃ¡", "Alto");
			VOBar bar1 = parranderos.adicionarBar ("Los Amigos", "BogotÃ¡", "Bajo", 2);
			boolean errorTipoBebida = false;
			VOTipoBebida tipoBebida = parranderos.adicionarTipoBebida ("Vino tinto");
			if (tipoBebida == null)
			{
				tipoBebida = parranderos.darTipoBebidaPorNombre ("Vino tinto");
				errorTipoBebida = true;
			}
			VOBebida bebida1 = parranderos.adicionarBebida ("120", tipoBebida.getId (), 10);
			
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "diurno");
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "nocturno");
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "todos");
			parranderos.adicionarGustan (bdor1.getId (), bebida1.getId ());

			List <VOTipoBebida> listaTipos = parranderos.darVOTiposBebida();
			List <VOBebida> listaBebidas = parranderos.darVOBebidas();
			List <VOBar> listaBares = parranderos.darVOBares ();
			List <VOBebedor> bebedores = parranderos.darVOBebedores();
			List <VOGustan> listaGustan = parranderos.darVOGustan();
			List <VOVisitan> listaVisitan = parranderos.darVOVisitan();

			VOBebedor bdor2 = parranderos.darBebedorCompleto(bdor1.getId ());

			// No se elimina la tupla de GUSTAN para estudiar la coherencia de las operaciones en la base de daatos
			long gustanEliminados = 0;
			long bebidasEliminadas = parranderos.eliminarBebidaPorNombre ("120");
			long tiposEliminados = parranderos.eliminarTipoBebidaPorNombre ("Vino tinto");
			long [] bebedorVisitasEliminados = parranderos.eliminarBebedorYVisitas_v2 (bdor1.getId ());
			long baresEliminados = parranderos.eliminarBarPorNombre ("Los Amigos");

			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de creaciÃ³n y listado de toda la informaciÃ³n de un bebedor\n";
			resultado += "Y DE BORRADO DE BEBEDOR Y VISITA,S cuando el bebedor aÃºn estÃ¡ referenciado cuando se quiere borrar\n";
			resultado += "v2: El bebedor no se puede borrar, pero sus visitas SÃ�";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			if (errorTipoBebida)
			{
				resultado += "*** Exception creando tipo de bebida !!\n";
				resultado += "*** Es probable que ese tipo de bebida ya existiera y hay restricciÃ³n de UNICIDAD sobre el nombre del tipo de bebida\n";
				resultado += "*** Revise el log de parranderos para mÃ¡s detalles\n";
			}
			resultado += "\n" + listarTiposBebida (listaTipos);
			resultado += "\n" + listarBebidas (listaBebidas);
			resultado += "\n" + listarBares (listaBares);
			resultado += "\n" + listarBebedores (bebedores);
			resultado += "\n" + listarGustan (listaGustan);
			resultado += "\n" + listarVisitan (listaVisitan);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += "\nBuscando toda la informaciÃ³n del bebedor con id " + bdor1 + ":\n";
			resultado += bdor2 != null ? "El bebedor es: " + bdor2.toStringCompleto() + "\n" : "Ese bebedor no existe\n";	
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += gustanEliminados + " Gustan eliminados\n";
			resultado += bebidasEliminadas + " Bebidas eliminadas\n";
			resultado += tiposEliminados + " Tipos de Bebida eliminados\n";
			resultado += bebedorVisitasEliminados [0] + " Bebedores eliminados y " + bebedorVisitasEliminados [1] +" Visitas eliminadas\n";
			resultado += baresEliminados + " Bares eliminados\n";
			resultado += "\n\n************ ATENCIÃ“N - ATENCIÃ“N - ATENCIÃ“N - ATENCIÃ“N ************ \n";
			resultado += "\nRecuerde que -1 registros borrados significa que hubo un problema !! \n";
			resultado += "\nREVISE EL LOG DE PARRANDEROS Y EL DE DATANUCLEUS \n";
			resultado += "\nNO OLVIDE LIMPIAR LA BASE DE DATOS \n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
}

    /**
     * DemostraciÃ³n de la modificaciÃ³n: Cambiar la ciudad de un bebedor
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
    */
    public void demoCambiarCiudadBebedor ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			VOBebedor bdor1 = parranderos.adicionarBebedor ("Pepito", "BogotÃ¡", "Alto");
			
			List<VOBebedor> bebedores1 = parranderos.darVOBebedores ();
			long bebedoresActualizados = parranderos.cambiarCiudadBebedor (bdor1.getId (), "MedellÃ­n");
			List<VOBebedor> bebedores2 = parranderos.darVOBebedores ();
			
			long bebedoresEliminados = parranderos.eliminarBebedorPorNombre ("Pepito");

			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de modificaciÃ³n de la ciudad de un bebedor\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			resultado += "\n" + listarBebedores (bebedores1);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += bebedoresActualizados + " Bebedores modificados\n";
			resultado += "\n" + listarBebedores (bebedores2);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += bebedoresEliminados + " Bebedores eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * DemostraciÃ³n de la consulta: Dar la informaciÃ³n de los bebedores y del nÃºmero de bares que visita cada uno
     * Incluye el manejo de los bares
     * Incuye el manejo de la relaciÃ³n visitan
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    public void demoBebedoresYNumVisitasRealizadas ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			VOBar bar1 = parranderos.adicionarBar ("Los Amigos1", "BogotÃ¡", "Bajo", 2);
			VOBar bar2 = parranderos.adicionarBar ("Los Amigos2", "BogotÃ¡", "Bajo", 3);
			VOBar bar3 = parranderos.adicionarBar ("Los Amigos3", "BogotÃ¡", "Bajo", 4);
			VOBar bar4 = parranderos.adicionarBar ("Los Amigos4", "MedellÃ­n", "Bajo", 5);
			VOBebedor bdor1 = parranderos.adicionarBebedor ("Pepito", "BogotÃ¡", "Alto");
			VOBebedor bdor2 = parranderos.adicionarBebedor ("Juanito", "BogotÃ¡", "Alto");
			VOBebedor bdor3 = parranderos.adicionarBebedor ("Carlitos", "MedellÃ­n", "Alto");
			VOBebedor bdor4 = parranderos.adicionarBebedor ("Luis", "Cartagena", "Medio");
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "diurno");
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "nocturno");
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "todos");
			parranderos.adicionarVisitan (bdor1.getId (), bar2.getId (), new Timestamp (System.currentTimeMillis()), "diurno");
			parranderos.adicionarVisitan (bdor1.getId (), bar3.getId (), new Timestamp (System.currentTimeMillis()), "diurno");
			parranderos.adicionarVisitan (bdor2.getId (), bar3.getId (), new Timestamp (System.currentTimeMillis()), "diurno");
			parranderos.adicionarVisitan (bdor2.getId (), bar4.getId (), new Timestamp (System.currentTimeMillis()), "diurno");

			List<VOBar> bares = parranderos.darVOBares();
			List<VOBebedor> bebedores = parranderos.darVOBebedores();
			List<VOVisitan> visitan = parranderos.darVOVisitan ();
			List<Object []> bebedoresYNumVisitas = parranderos.darBebedoresYNumVisitasRealizadas ();

			long [] elimBdor1 = parranderos.eliminarBebedorYVisitas_v1 (bdor1.getId ());
			long [] elimBdor2 = parranderos.eliminarBebedorYVisitas_v1 (bdor2.getId ());
			long [] elimBdor3 = parranderos.eliminarBebedorYVisitas_v1 (bdor3.getId ());
			long [] elimBdor4 = parranderos.eliminarBebedorYVisitas_v1 (bdor4.getId ());
			long baresEliminados = parranderos.eliminarBarPorNombre ("Los Amigos1");
			baresEliminados += parranderos.eliminarBarPorNombre ("Los Amigos2");
			baresEliminados += parranderos.eliminarBarPorNombre ("Los Amigos3");
			baresEliminados += parranderos.eliminarBarPorNombre ("Los Amigos4");

			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de dar bebedores y cuÃ¡ntas visitan han realizado\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			resultado += "\n" + listarBares (bares);
			resultado += "\n" + listarBebedores (bebedores);
			resultado += "\n" + listarVisitan (visitan);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += "\n" + listarBebedorYNumVisitas (bebedoresYNumVisitas);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += elimBdor1 [0] + " Bebedores eliminados y " + elimBdor1 [1] +" Visitas eliminadas\n";
			resultado += elimBdor2 [0] + " Bebedores eliminados y " + elimBdor2 [1] +" Visitas eliminadas\n";
			resultado += elimBdor3 [0] + " Bebedores eliminados y " + elimBdor3 [1] +" Visitas eliminadas\n";
			resultado += elimBdor4 [0] + " Bebedores eliminados y " + elimBdor4 [1] +" Visitas eliminadas\n";
			resultado += baresEliminados + " Bares eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * DemostraciÃ³n de la consulta: Para cada ciudad, cuÃ¡ntos bebedores vistan bares
     * Incluye el manejo de bares
     * Incluye el manejo de la relaciÃ³n visitan
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    public void demoBebedoresDeCiudad ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			VOBar bar1 = parranderos.adicionarBar ("Los Amigos1", "BogotÃ¡", "Bajo", 2);
			VOBar bar2 = parranderos.adicionarBar ("Los Amigos2", "BogotÃ¡", "Bajo", 3);
			VOBar bar3 = parranderos.adicionarBar ("Los Amigos3", "BogotÃ¡", "Bajo", 4);
			VOBar bar4 = parranderos.adicionarBar ("Los Amigos4", "MedellÃ­n", "Bajo", 5);
			VOBebedor bdor1 = parranderos.adicionarBebedor ("Pepito", "BogotÃ¡", "Alto");
			VOBebedor bdor2 = parranderos.adicionarBebedor ("Juanito", "MedellÃ­n", "Alto");
			VOBebedor bdor3 = parranderos.adicionarBebedor ("Pedrito", "MedellÃ­n", "Alto");
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "diurno");
			parranderos.adicionarVisitan (bdor1.getId (), bar2.getId (), new Timestamp (System.currentTimeMillis()), "diurno");
			parranderos.adicionarVisitan (bdor1.getId (), bar3.getId (), new Timestamp (System.currentTimeMillis()), "diurno");
			parranderos.adicionarVisitan (bdor2.getId (), bar3.getId (), new Timestamp (System.currentTimeMillis()), "diurno");
			parranderos.adicionarVisitan (bdor1.getId (), bar4.getId (), new Timestamp (System.currentTimeMillis()), "diurno");

			List<VOBar> bares = parranderos.darVOBares();
			List<VOBebedor> bebedores = parranderos.darVOBebedores();
			List<VOVisitan> visitan = parranderos.darVOVisitan ();
			long bebedoresBogota = parranderos.darCantidadBebedoresCiudadVisitanBares ("BogotÃ¡");
			long bebedoresMedellin = parranderos.darCantidadBebedoresCiudadVisitanBares ("MedellÃ­n");

			long [] elimBdor1 = parranderos.eliminarBebedorYVisitas_v1 (bdor1.getId ());
			long [] elimBdor2 = parranderos.eliminarBebedorYVisitas_v1 (bdor2.getId ());
			long elimBdor3 = parranderos.eliminarBebedorPorId (bdor3.getId ());
			long baresEliminados = parranderos.eliminarBarPorNombre ("Los Amigos1");
			baresEliminados += parranderos.eliminarBarPorNombre ("Los Amigos2");
			baresEliminados += parranderos.eliminarBarPorNombre ("Los Amigos3");
			baresEliminados += parranderos.eliminarBarPorNombre ("Los Amigos4");

			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de dar cantidad de bebedores de una ciudad que vistan baresn\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			resultado += "\n" + listarBares (bares);
			resultado += "\n" + listarBebedores (bebedores);
			resultado += "\n" + listarVisitan (visitan);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += "\nBebedores de BogotÃ¡: " + bebedoresBogota;
			resultado += "\nBebedores de MedellÃ­n: " + bebedoresMedellin;
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += elimBdor1 [0] + " Bebedores eliminados y " + elimBdor1 [1] +" Visitas eliminadas\n";
			resultado += elimBdor2 [0] + " Bebedores eliminados y " + elimBdor2 [1] +" Visitas eliminadas\n";
			resultado += elimBdor3 + " Bebedores eliminados\n";
			resultado += baresEliminados + " Bares eliminados\n";
			resultado += "\n Demo terminada";

			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	/* ****************************************************************
	 * 			Demos de Gustan
	 *****************************************************************/
    /**
     * DemostraciÃ³n de creaciÃ³n, consulta y borrado de la relaciÃ³n Gustan
     * Incluye el manejo de Bebedores
     * Incluye el manejo de Bebidas
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    public void demoGustan ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			boolean errorTipoBebida = false;
			VOTipoBebida tipoBebida = parranderos.adicionarTipoBebida ("Vino tinto");
			if (tipoBebida == null)
			{
				tipoBebida = parranderos.darTipoBebidaPorNombre ("Vino tinto");
				errorTipoBebida = true;
			}
			VOBebida bebida1 = parranderos.adicionarBebida ("120", tipoBebida.getId (), 10);
			VOBebedor bdor1 = parranderos.adicionarBebedor ("Pepito", "BogotÃ¡", "Alto");
			parranderos.adicionarGustan (bdor1.getId (), bebida1.getId ());

			List <VOTipoBebida> listaTiposBebida = parranderos.darVOTiposBebida ();
			List <VOBebida> listaBebidas = parranderos.darVOBebidas ();
			List <VOBebedor> listaBebedores = parranderos.darVOBebedores ();
			List <VOGustan> listaGustan = parranderos.darVOGustan();
			long gustanEliminados = parranderos.eliminarGustan (bdor1.getId (), bebida1.getId ());
			long bebidasEliminadas = parranderos.eliminarBebidaPorNombre ("120");
			long tbEliminados = parranderos.eliminarTipoBebidaPorNombre ("Vino tinto");
			long bebedoresEliminados = parranderos.eliminarBebedorPorNombre ("Pepito");
			
			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de creaciÃ³n y listado de Gustan\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			if (errorTipoBebida)
			{
				resultado += "*** Exception creando tipo de bebida !!\n";
				resultado += "*** Es probable que ese tipo de bebida ya existiera y hay restricciÃ³n de UNICIDAD sobre el nombre del tipo de bebida\n";
				resultado += "*** Revise el log de parranderos para mÃ¡s detalles\n";
			}
			resultado += "\n" + listarTiposBebida (listaTiposBebida);
			resultado += "\n" + listarBebidas (listaBebidas);
			resultado += "\n" + listarBebedores (listaBebedores);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += "\n" + listarGustan (listaGustan);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += gustanEliminados + " Gustan eliminados\n";
			resultado += bebidasEliminadas + " Bebidas eliminadas\n";
			resultado += tbEliminados + " Tipos de bebida eliminados\n";
			resultado += bebedoresEliminados + " Bebedores eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	/* ****************************************************************
	 * 			Demos de Sirven
	 *****************************************************************/
    /**
     * DemostraciÃ³n de creaciÃ³n, consulta y borrado de la relaciÃ³n Sirven
     * Incluye el manejo de Bares
     * Incluye el manejo de tipos de bebida
     * Incluye el manejo de Bebidas
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    public void demoSirven ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			boolean errorTipoBebida = false;
			VOTipoBebida tipoBebida = parranderos.adicionarTipoBebida ("Vino tinto");
			if (tipoBebida == null)
			{
				tipoBebida = parranderos.darTipoBebidaPorNombre ("Vino tinto");
				errorTipoBebida = true;
			}
			VOBebida bebida1 = parranderos.adicionarBebida ("120", tipoBebida.getId (), 10);
			VOBar bar1 = parranderos.adicionarBar ("Los Amigos1", "BogotÃ¡", "Bajo", 2);
			parranderos.adicionarSirven (bar1.getId (), bebida1.getId (), "diurno");

			List <VOTipoBebida> listaTiposBebida = parranderos.darVOTiposBebida ();
			List <VOBebida> listaBebidas = parranderos.darVOBebidas ();
			List <VOBar> listaBares = parranderos.darVOBares ();
			List <VOSirven> listaSirven = parranderos.darVOSirven();
			
			long sirvenEliminados = parranderos.eliminarSirven (bar1.getId (), bebida1.getId ());
			long bebidasEliminadas = parranderos.eliminarBebidaPorNombre ("120");
			long tbEliminados = parranderos.eliminarTipoBebidaPorNombre ("Vino tinto");
			long baresEliminados = parranderos.eliminarBarPorNombre ("Los Amigos1");
			
			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de creaciÃ³n y listado de Sirven\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			if (errorTipoBebida)
			{
				resultado += "*** Exception creando tipo de bebida !!\n";
				resultado += "*** Es probable que ese tipo de bebida ya existiera y hay restricciÃ³n de UNICIDAD sobre el nombre del tipo de bebida\n";
				resultado += "*** Revise el log de parranderos para mÃ¡s detalles\n";
			}
			resultado += "\n" + listarTiposBebida (listaTiposBebida);
			resultado += "\n" + listarBebidas (listaBebidas);
			resultado += "\n" + listarBares (listaBares);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += "\n" + listarSirven (listaSirven);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += sirvenEliminados + " Sirven eliminados\n";
			resultado += bebidasEliminadas + " Bebidas eliminadas\n";
			resultado += tbEliminados + " Tipos de bebida eliminados\n";
			resultado += baresEliminados + " Bares eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	/* ****************************************************************
	 * 			Demos de Visitan
	 *****************************************************************/
    /**
     * DemostraciÃ³n de creaciÃ³n, consulta y borrado de la relaciÃ³n Visitan
     * Incluye el manejo de Bebedores
     * Incluye el manejo de Bares
     * Muestra la traza de la ejecuciÃ³n en el panelDatos
     * 
     * Pre: La base de datos estÃ¡ vacÃ­a
     * Post: La base de datos estÃ¡ vacÃ­a
     */
    public void demoVisitan ( )
    {
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			// ATENCIÃ“N: En una aplicaciÃ³n real, los datos JAMÃ�S estÃ¡n en el cÃ³digo
			VOBar bar1 = parranderos.adicionarBar ("Los Amigos1", "BogotÃ¡", "Bajo", 2);
			VOBebedor bdor1 = parranderos.adicionarBebedor ("Pepito", "BogotÃ¡", "Alto");
			parranderos.adicionarVisitan (bdor1.getId (), bar1.getId (), new Timestamp (System.currentTimeMillis()), "diurno");

			List <VOBar> listaBares = parranderos.darVOBares ();
			List <VOBebedor> listaBebedores = parranderos.darVOBebedores ();
			List <VOVisitan> listaVisitan = parranderos.darVOVisitan();
			long visitanEliminados = parranderos.eliminarVisitan (bdor1.getId (), bar1.getId ());
			long baresEliminados = parranderos.eliminarBarPorNombre ("Los Amigos1");
			long bebedoresEliminadas = parranderos.eliminarBebedorPorNombre ("Pepito");
			
			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "Demo de creaciÃ³n y listado de Visitan\n\n";
			resultado += "\n\n************ Generando datos de prueba ************ \n";
			resultado += "\n" + listarBares (listaBares);
			resultado += "\n" + listarBebedores (listaBebedores);
			resultado += "\n\n************ Ejecutando la demo ************ \n";
			resultado += "\n" + listarVisitan (listaVisitan);
			resultado += "\n\n************ Limpiando la base de datos ************ \n";
			resultado += visitanEliminados + " Visitan eliminados\n";
			resultado += bebedoresEliminadas + " Bebedores eliminadas\n";
			resultado += baresEliminados + " Bares eliminados\n";
			resultado += "\n Demo terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	/* ****************************************************************
	 * 			MÃ©todos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de BancAndes
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecuciÃ³n
	 */
	public void limpiarLogParranderos ()
	{
		// EjecuciÃ³n de la operaciÃ³n y recolecciÃ³n de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecuciÃ³n
	 */
	public void limpiarLogDatanucleus ()
	{
		// EjecuciÃ³n de la operaciÃ³n y recolecciÃ³n de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el nÃºmero de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
    		// EjecuciÃ³n de la demo y recolecciÃ³n de los resultados
			long eliminados [] = parranderos.limpiarParranderos();
			
			// GeneraciÃ³n de la cadena de caracteres con la traza de la ejecuciÃ³n de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Gustan eliminados\n";
			resultado += eliminados [1] + " Sirven eliminados\n";
			resultado += eliminados [2] + " Visitan eliminados\n";
			resultado += eliminados [3] + " Bebidas eliminadas\n";
			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
			resultado += eliminados [5] + " Bebedores eliminados\n";
			resultado += eliminados [6] + " Bares eliminados\n";
			resultado += "\nLimpieza terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/**
	 * Muestra la presentaciÃ³n general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de BancAndes
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual BancAndes.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de BancAndes
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD BancAndes.pdf");
	}
	
	/**
	 * Muestra el script de creaciÃ³n de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para BancAndes
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentaciÃ³n Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
    /**
     * Muestra la informaciÃ³n acerca del desarrollo de esta apicaciÃ³n
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(BogotÃ¡	- Colombia)\n";
		resultado += " * Departamento	de	IngenierÃ­a	de	Sistemas	y	ComputaciÃ³n\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versiÃ³n 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: BancAndes Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author GermÃ¡n Bravo\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += " * Revisado por: Claudia JimÃ©nez, Christian Ariza\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);
    }
    

	/* ****************************************************************
	 * 			MÃ©todos privados para la presentaciÃ³n de resultados y otras operaciones
	 *****************************************************************/
    /**
     * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una lÃ­nea por cada tipo de bebida
     * @param lista - La lista con los tipos de bebida
     * @return La cadena con una lÃ­ea para cada tipo de bebida recibido
     */
    private String listarTiposBebida(List<VOTipoBebida> lista) 
    {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (VOTipoBebida tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de bebidas recibida: una lÃ­nea por cada bebida
     * @param lista - La lista con las bebidas
     * @return La cadena con una lÃ­ea para cada bebida recibida
     */
    private String listarBebidas (List<VOBebida> lista) 
    {
    	String resp = "Las bebidas existentes son:\n";
    	int i = 1;
        for (VOBebida beb : lista)
        {
        	resp += i++ + ". " + beb.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de bebedores recibida: una lÃ­nea por cada bebedor
     * @param lista - La lista con los bebedores
     * @return La cadena con una lÃ­ea para cada bebedor recibido
     */
    private String listarBebedores (List<VOBebedor> lista) 
    {
    	String resp = "Los bebedores existentes son:\n";
    	int i = 1;
        for (VOBebedor bdor : lista)
        {
        	resp += i++ + ". " + bdor.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de bares recibida: una lÃ­nea por cada bar
     * @param lista - La lista con los bares
     * @return La cadena con una lÃ­ea para cada bar recibido
     */
    private String listarBares (List<VOBar> lista) 
    {
    	String resp = "Los bares existentes son:\n";
    	int i = 1;
        for (VOBar bar : lista)
        {
        	resp += i++ + ". " + bar.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de gustan recibida: una lÃ­nea por cada gusta
     * @param lista - La lista con los gustan
     * @return La cadena con una lÃ­ea para cada gustan recibido
     */
    private String listarGustan (List<VOGustan> lista) 
    {
    	String resp = "Los gustan existentes son:\n";
    	int i = 1;
        for (VOGustan serv : lista)
        {
        	resp += i++ + ". " + serv.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de sirven recibida: una lÃ­nea por cada sirven
     * @param lista - La lista con los sirven
     * @return La cadena con una lÃ­ea para cada sirven recibido
     */
    private String listarSirven (List<VOSirven> lista) 
    {
    	String resp = "Los sirven existentes son:\n";
    	int i = 1;
        for (VOSirven serv : lista)
        {
        	resp += i++ + ". " + serv.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de visitan recibida: una lÃ­nea por cada visitan
     * @param lista - La lista con los visitan
     * @return La cadena con una lÃ­ea para cada visitan recibido
     */
    private String listarVisitan (List<VOVisitan> lista) 
    {
    	String resp = "Los visitan existentes son:\n";
    	int i = 1;
        for (VOVisitan vis : lista)
        {
        	resp += i++ + ". " + vis.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de parejas de nÃºmeros recibida: una lÃ­nea por cada pareja
     * @param lista - La lista con las pareja
     * @return La cadena con una lÃ­ea para cada pareja recibido
     */
    private String listarBaresYBebidas (List<long[]> lista) 
    {
    	String resp = "Los bares y el nÃºmero de bebidas que sirven son:\n";
    	int i = 1;
        for ( long [] tupla : lista)
        {
			long [] datos = tupla;
	        String resp1 = i++ + ". " + "[";
			resp1 += "idBar: " + datos [0] + ", ";
			resp1 += "numBebidas: " + datos [1];
	        resp1 += "]";
	        resp += resp1 + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la lista de parejas de objetos recibida: una lÃ­nea por cada pareja
     * @param lista - La lista con las parejas (Bebedor, numero visitas)
     * @return La cadena con una lÃ­nea para cada pareja recibido
     */
    private String listarBebedorYNumVisitas (List<Object[]> lista) 
    {
    	String resp = "Los bebedores y el nÃºmero visitas realizadas son:\n";
    	int i = 1;
        for (Object [] tupla : lista)
        {
			VOBebedor bdor = (VOBebedor) tupla [0];
			int numVisitas = (int) tupla [1];
	        String resp1 = i++ + ". " + "[";
			resp1 += bdor + ", ";
			resp1 += "numVisitas: " + numVisitas;
	        resp1 += "]";
	        resp += resp1 + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la descripciÃ³n de la excepcion e, haciendo Ã©nfasis en las excepcionsde JDO
     * @param e - La excepciÃ³n recibida
     * @return La descripciÃ³n de la excepciÃ³n, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicaciÃ³n
	 * @param e - La excepciÃ³n generada
	 * @return La cadena con la informaciÃ³n de la excepciÃ³n y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecuciÃ³n\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para mÃ¡s detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parÃ¡metro con la aplicaciÃ³n por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			MÃ©todos de la InteracciÃ³n
	 *****************************************************************/
    /**
     * MÃ©todo para la ejecuciÃ³n de los eventos que enlazan el menÃº con los mÃ©todos de negocio
     * Invoca al mÃ©todo correspondiente segÃºn el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazBancAndesDemo.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este mÃ©todo ejecuta la aplicaciÃ³n, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por lÃ­nea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazBancAndesDemo interfaz = new InterfazBancAndesDemo( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
