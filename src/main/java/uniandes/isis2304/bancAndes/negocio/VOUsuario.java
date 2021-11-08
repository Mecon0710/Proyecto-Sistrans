package uniandes.isis2304.bancAndes.negocio;

import java.util.ArrayList;

public interface VOUsuario {

	public String getLogin();
	public void setLogin(String login);
	public String getPalabClave();
	public void setPalabClave(String palabClave);
	public String getDocumTipo();
	public void setDocumTipo(String documTipo);
	public Integer getDocumNum();
	public void setDocumNum(Integer documNum);
	public String getNombre();
	public void setNombre(String nombre);
	public String getNacionalidad();
	public void setNacionalidad(String nacionalidad);
	public String getDireccFisica();
	public void setDireccFisica(String direccFisica);
	public String getDireccElectro();
	public void setDireccElectro(String direccElectro);
	public Integer getTelefono();
	public void setTelefono(Integer telefono);
	public String getCiudad();
	public void setCiudad(String ciudad);
	public String getDepartamento();
	public void setDepartamento(String departamento);
	public Integer getCodPostal();
	public void setCodPostal(Integer codPostal);
	public ArrayList<Operacion> getOperaciones();
	public void setOperaciones(ArrayList<Operacion> operaciones);
	public ArrayList<Accion> getAcciones();
	public void setAcciones(ArrayList<Accion> acciones);
	@Override
	public String toString();
}
