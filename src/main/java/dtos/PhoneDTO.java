package dtos;

import entities.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PhoneDTO {

    private String number;
    private String description;

    public static List<PhoneDTO> getDTOs(List<Phone> phones){
        List<PhoneDTO> phoneDTOs = new ArrayList();
        phones.forEach(phone -> phoneDTOs.add(new PhoneDTO(phone)));
        return phoneDTOs;
    }

    public PhoneDTO(Phone phone) {
        this.number = phone.getNumber();
        this.description = phone.getDescription();
    }

}
