package uniandes.isis2304.bancAndes.negocio;

public class Accion implements VOAccion{
	private Integer cantidad;
	private Integer cantRestric;
	private Usuario usuario;
	
	public Accion(Integer cantidad, Integer cantRestric, Usuario usuario) {
		super();
		this.cantidad = cantidad;
		this.cantRestric = cantRestric;
		this.usuario = usuario;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Integer getCantRestric() {
		return cantRestric;
	}
	public void setCantRestric(Integer cantRestric) {
		this.cantRestric = cantRestric;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Accion [cantidad=" + cantidad + ", cantRestric=" + cantRestric + ", usuario=" + usuario + "]";
	}
}
