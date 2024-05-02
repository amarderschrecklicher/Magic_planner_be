package ba.unsa.etf.cehajic.hcehajic2.appback.material;

import java.util.Arrays;

import javax.persistence.*;

@Entity
@Table
public class Material {

    @Id
    @SequenceGenerator(
            name = "material_sequence_new",
            sequenceName = "material_sequence_new",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "material_sequence_new"
    )
    private Long id;
    private String name; // Name of the media file
    private String contentType; // Content type (e.g., image/jpeg, video/mp4)
    private byte[] data; // Binary data of the media file

    // Add constructors
    public Material() {
    }

    public Material(String name, String contentType, byte[] data) {
        this.name = name;
        this.contentType = contentType;
        this.data = data;
    }

    // Add getters and setters for all attributes
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    // Add toString method
    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
   
}
