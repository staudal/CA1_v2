package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Hobby;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@NoArgsConstructor
public class HobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Creating a new Hobby
    public HobbyDTO create(HobbyDTO hobbyDTO) {
        Hobby hobby = new Hobby(hobbyDTO.getName(), hobbyDTO.getDescription());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }

    // Get all hobbies by personDTO
    public List<HobbyDTO> getAllHobbiesByPerson(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h JOIN h.persons p WHERE p.id = :id", Hobby.class);
            query.setParameter("id", personDTO.getId());
            List<Hobby> hobbies = query.getResultList();
            return HobbyDTO.getDTOs(hobbies);
        } finally {
            em.close();
        }
    }

    // Get all hobbies
    public List<HobbyDTO> getAllHobbies() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h", Hobby.class);
            List<Hobby> hobbies = query.getResultList();
            return HobbyDTO.getDTOs(hobbies);
        } finally {
            em.close();
        }
    }

}
