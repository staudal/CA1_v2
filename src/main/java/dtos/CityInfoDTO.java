package dtos;

import entities.CityInfo;
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
public class CityInfoDTO {

    private int zipCode;
    private String city;

    public static List<CityInfoDTO> getDTOs(List<CityInfo> cityInfos){
        List<CityInfoDTO> cityInfoDTOs = new ArrayList();
        cityInfos.forEach(cityInfo -> cityInfoDTOs.add(new CityInfoDTO(cityInfo)));
        return cityInfoDTOs;
    }

    public CityInfoDTO(CityInfo cityInfo) {
        this.zipCode = cityInfo.getZipCode();
        this.city = cityInfo.getCity();
    }

}
