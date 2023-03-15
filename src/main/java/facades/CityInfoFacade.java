package facades;

import dtos.CityInfoDTO;
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

    // Get city from personDTO
    public CityInfoDTO getCityFromPerson (PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personDTO.getId());
        try {
            return new CityInfoDTO(em.find(CityInfo.class, person.getAddress().getCityInfo().getId()));
        } finally {
            em.close();
        }
    }

    // Get all zip codes
    public List<CityInfoDTO> getAllZipCodes () {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class);
            List<CityInfo> cityInfoList = query.getResultList();
            return CityInfoDTO.getDTOs(cityInfoList);
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        CityInfoFacade facade = CityInfoFacade.getCityInfoFacade(emf);
        System.out.println(facade.getAllZipCodes().size());
    }

}
