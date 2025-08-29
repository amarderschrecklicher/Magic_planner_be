package ba.unsa.etf.cehajic.hcehajic2.appback.manager;

import java.util.Objects;

public final class ManagerMapper {

    private ManagerMapper() {}

    public static Manager fromRequest(ManagerRequestDTO dto) {
        if (dto == null) return null;

        Manager m = new Manager();
        m.setName(trim(dto.getName()));
        m.setSurname(trim(dto.getSurname()));
        m.setEmail(lower(trim(dto.getEmail())));
        m.setKidMale(Boolean.TRUE.equals(dto.getKidMale()));
        m.setDateOfBirth(dto.getDateOfBirth());
         m.setUsername(trim(dto.getUsername()));
        return m;
    }


    public static ManagerRequestDTO toRequestDto(Manager m) {
        if (m == null) return null;
        ManagerRequestDTO dto = new ManagerRequestDTO();
        dto.setName(m.getName());
        dto.setSurname(m.getSurname());
        dto.setUsername(m.getUsername());
        dto.setEmail(m.getEmail());
        dto.setId(Objects.requireNonNull(m.getId()));
        dto.setKidMale(m.getKidMale());
        dto.setDateOfBirth(m.getDateOfBirth());
        return dto;
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static String lower(String s) { return s == null ? null : s.toLowerCase(); }
}
