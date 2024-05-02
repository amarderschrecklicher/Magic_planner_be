package ba.unsa.etf.cehajic.hcehajic2.appback.material;

import java.util.Arrays;

public class Material {

    private Long id;
    private String name;
    private String contentType;
    private byte[] data;

    // Constructor
    public Material() {
    }

    public Material(String name, String contentType, byte[] data) {
        this.name = name;
        this.contentType = contentType;
        this.data = data;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // toString method
    @Override
    public String toString() {
        return "MaterialRequestDTO{" +
                "name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }


}
