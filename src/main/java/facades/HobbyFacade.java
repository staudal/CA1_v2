package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
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

    // Edit hobby
    public HobbyDTO editHobby(HobbyDTO hobbyDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Hobby hobby = em.find(Hobby.class, hobbyDTO.getId());
            if (hobbyDTO.getName() != null) {
                hobby.setName(hobbyDTO.getName());
            }

            if (hobbyDTO.getDescription() != null) {
                hobby.setDescription(hobbyDTO.getDescription());
            }

            em.getTransaction().commit();
            return new HobbyDTO(hobby);
        } finally {
            em.close();
        }
    }

    // Delete hobby
    public HobbyDTO deleteHobby(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Hobby hobby = em.find(Hobby.class, id);
            em.remove(hobby);
            em.getTransaction().commit();
            return new HobbyDTO(hobby);
        } finally {
            em.close();
        }
    }

    public List<HobbyDTO> getHobbiesByPerson(Long id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h JOIN h.persons p WHERE p.id = :id", Hobby.class);
            query.setParameter("id", id);
            List<Hobby> hobbies = query.getResultList();
            return HobbyDTO.getDTOs(hobbies);
        } finally {
            em.close();
        }
    }
}
