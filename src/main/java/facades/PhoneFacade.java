package facades;

import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.Person;
import entities.Phone;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@NoArgsConstructor
public class PhoneFacade {

    private static PhoneFacade instance;
    private static EntityManagerFactory emf;

    public static PhoneFacade getPhoneFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PhoneFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Creating a new Phone
    public PhoneDTO create(PhoneDTO phoneDTO) {
        Phone phone = new Phone(phoneDTO.getNumber(), phoneDTO.getDescription());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(phone);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PhoneDTO(phone);
    }

    // Get all phones
    public List<PhoneDTO> getAllPhones() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p", Phone.class);
            List<Phone> phones = query.getResultList();
            return PhoneDTO.getDTOs(phones);
        } finally {
            em.close();
        }
    }

    // Edit phone
    public PhoneDTO editPhone(PhoneDTO phoneDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Phone phone = em.find(Phone.class, phoneDTO.getId());
            if (phoneDTO.getNumber() != null) {
                phone.setNumber(phoneDTO.getNumber());
            }

            if (phoneDTO.getDescription() != null) {
                phone.setDescription(phoneDTO.getDescription());
            }

            em.getTransaction().commit();
            return new PhoneDTO(phone);
        } finally {
            em.close();
        }
    }

    // Delete phone
    public PhoneDTO deletePhone(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Phone phone = em.find(Phone.class, id);
            em.remove(phone);
            em.getTransaction().commit();
            return new PhoneDTO(phone);
        } finally {
            em.close();
        }
    }

}
