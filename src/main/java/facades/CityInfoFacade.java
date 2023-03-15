package facades;

import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.CityInfo;
import entities.Person;
import lombok.NoArgsConstructor;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@NoArgsConstructor
public class CityInfoFacade {

    private static CityInfoFacade instance;
    private static EntityManagerFactory emf;

    public static CityInfoFacade getCityInfoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<CityInfoDTO> getAllCities () {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class);
            List<CityInfo> cityInfoList = query.getResultList();
            return CityInfoDTO.getDTOs(cityInfoList);
        } finally {
            em.close();
        }
    }

    // Creating a new city
    public CityInfoDTO create (CityInfoDTO cityInfoDTO) {
        CityInfo cityInfo = new CityInfo(cityInfoDTO.getZipCode(), cityInfoDTO.getCity());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cityInfo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CityInfoDTO(cityInfo);
    }

    // Edit city
    public CityInfoDTO editCity (CityInfoDTO cityInfoDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            CityInfo cityInfo = em.find(CityInfo.class, cityInfoDTO.getId());
            System.out.println(cityInfo);
            if (cityInfoDTO.getCity() != null) {
                cityInfo.setCity(cityInfoDTO.getCity());
            }

            if (cityInfoDTO.getZipCode() != null) {
                cityInfo.setZipCode(cityInfoDTO.getZipCode());
            }
            System.out.println(cityInfo);
            em.getTransaction().commit();
            return new CityInfoDTO(cityInfo);
        } finally {
            em.close();
        }
    }

    // Delete city
    public CityInfoDTO deleteCity(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            CityInfo cityInfo = em.find(CityInfo.class, id);
            em.remove(cityInfo);
            em.getTransaction().commit();
            return new CityInfoDTO(cityInfo);
        } finally {
            em.close();
        }
    }
}
