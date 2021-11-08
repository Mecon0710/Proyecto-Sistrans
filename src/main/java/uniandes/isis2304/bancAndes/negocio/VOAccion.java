package uniandes.isis2304.bancAndes.negocio;

public interface VOAccion {
	
	public void setCantidad(Integer cantidad);
	
	public Integer getCantRestric();
	
	public void setCantRestric(Integer cantRestric);
	
	public Usuario getUsuario();
	
	public void setUsuario(Usuario usuario);
	
	@Override
	public String toString();
}
