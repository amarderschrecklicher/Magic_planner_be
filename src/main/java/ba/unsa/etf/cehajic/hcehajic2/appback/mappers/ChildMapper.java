package ba.unsa.etf.cehajic.hcehajic2.appback.mappers;


import ba.unsa.etf.cehajic.hcehajic2.appback.dtos.ChildRequestDTO;
import ba.unsa.etf.cehajic.hcehajic2.appback.models.Manager;
import ba.unsa.etf.cehajic.hcehajic2.appback.models.Child;

public class ChildMapper {

    public static Child toEntity(ChildRequestDTO dto, Manager manager) {
        if (dto == null) {
            return null;
        }

        return Child.builder()
                .id(dto.getId())
                .Name(dto.getName())
                .Surname(dto.getSurname())
                .kidMale(dto.getKidMale())
                .dateOfBirth(dto.getDateOfBirth())
                .qualities(dto.getQualities())
                .preferences(dto.getPreferences())
                .special(dto.getSpecial())
                .manager(manager)
                .email(dto.getEmail())
                .Password(dto.getPassword())
                .build();
    }


    public static ChildRequestDTO toDto(Child child) {
        if (child == null) {
            return null;
        }

        ChildRequestDTO dto = new ChildRequestDTO();
        dto.setId(child.getId());
        dto.setName(child.getName());
        dto.setSurname(child.getSurname());
        dto.setKidMale(child.getKidMale());
        dto.setDateOfBirth(child.getDateOfBirth());
        dto.setQualities(child.getQualities());
        dto.setPreferences(child.getPreferences());
        dto.setSpecial(child.getSpecial());
        dto.setManagerId(child.getManager() != null ? child.getManager().getId() : null);
        dto.setEmail(child.getEmail());
        dto.setPassword(child.getPassword());
        return dto;
    }
}
