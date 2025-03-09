package ec.edu.espe.subasta.dto;

public class AutoDTO {
    private Integer idAuto;
    private String marca;
    private String modelo;
    private Integer año;
    private Double precioBase;
    private String estado;
    private Integer subastaId;
    private Integer vendedorId;

    public AutoDTO() {}

    public AutoDTO(Integer idAuto, String marca, String modelo, Integer año, Double precioBase, String estado, Integer subastaId, Integer vendedorId) {
        this.idAuto = idAuto;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.precioBase = precioBase;
        this.estado = estado;
        this.subastaId = subastaId;
        this.vendedorId = vendedorId;
    }

    // Getters y Setters
    public Integer getIdAuto() { return idAuto; }
    public void setIdAuto(Integer idAuto) { this.idAuto = idAuto; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Integer getAño() { return año; }
    public void setAño(Integer año) { this.año = año; }

    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getSubastaId() { return subastaId; }
    public void setSubastaId(Integer subastaId) { this.subastaId = subastaId; }

    public Integer getVendedorId() { return vendedorId; }
    public void setVendedorId(Integer vendedorId) { this.vendedorId = vendedorId; }
}
