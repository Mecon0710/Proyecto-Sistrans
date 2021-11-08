package uniandes.isis2304.bancAndes.negocio;

import java.util.ArrayList;


public class Usuario implements VOUsuario{
	
	protected String login;
	protected String palabClave;
	protected String documTipo;
	protected Integer documNum;
	protected String nombre;
	protected String nacionalidad;
	protected String direccFisica;
	protected String direccElectro;
	protected Integer telefono;
	protected String ciudad;
	protected String departamento;
	protected Integer codPostal;
	protected ArrayList<Operacion> operaciones;
	protected ArrayList<Accion> acciones;
	
	public Usuario(String login, String palabClave, String documTipo, Integer documNum, String nombre,
			String nacionalidad, String direccFisica, String direccElectro, Integer telefono, String ciudad,
			String departamento, Integer codPostal) {
		super();
		this.login = login;
		this.palabClave = palabClave;
		this.documTipo = documTipo;
		this.documNum = documNum;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.direccFisica = direccFisica;
		this.direccElectro = direccElectro;
		this.telefono = telefono;
		this.ciudad = ciudad;
		this.departamento = departamento;
		this.codPostal = codPostal;
		this.operaciones = new ArrayList<Operacion>();
		this.acciones = new ArrayList<Accion>();
		
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPalabClave() {
		return palabClave;
	}
	public void setPalabClave(String palabClave) {
		this.palabClave = palabClave;
	}
	public String getDocumTipo() {
		return documTipo;
	}
	public void setDocumTipo(String documTipo) {
		this.documTipo = documTipo;
	}
	public Integer getDocumNum() {
		return documNum;
	}
	public void setDocumNum(Integer documNum) {
		this.documNum = documNum;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getDireccFisica() {
		return direccFisica;
	}
	public void setDireccFisica(String direccFisica) {
		this.direccFisica = direccFisica;
	}
	public String getDireccElectro() {
		return direccElectro;
	}
	public void setDireccElectro(String direccElectro) {
		this.direccElectro = direccElectro;
	}
	public Integer getTelefono() {
		return telefono;
	}
	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public Integer getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(Integer codPostal) {
		this.codPostal = codPostal;
	}
	public ArrayList<Operacion> getOperaciones() {
		return operaciones;
	}
	public void setOperaciones(ArrayList<Operacion> operaciones) {
		this.operaciones = operaciones;
	}
	public ArrayList<Accion> getAcciones() {
		return acciones;
	}
	public void setAcciones(ArrayList<Accion> acciones) {
		this.acciones = acciones;
	}
	@Override
	public String toString() {
		return "Usuario [login=" + login + ", palabClave=" + palabClave + ", documTipo=" + documTipo + ", documNum="
				+ documNum + ", nombre=" + nombre + ", nacionalidad=" + nacionalidad + ", direccFisica=" + direccFisica
				+ ", direccElectro=" + direccElectro + ", telefono=" + telefono + ", ciudad=" + ciudad
				+ ", departamento=" + departamento + ", codPostal=" + codPostal + "]";
	}

	
	
	

}
