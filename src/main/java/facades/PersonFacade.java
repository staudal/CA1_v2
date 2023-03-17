package facades;

import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
import errorhandling.PersonNotFoundException;
import lombok.NoArgsConstructor;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<PersonDTO> getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            return PersonDTO.getDTOs(query.getResultList());
        } finally {
            em.close();
        }
    }

    // Creating a new Person
    public PersonDTO create(PersonDTO personDTO) {
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    // Edit person
    public PersonDTO editPerson(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, personDTO.getId());
            if (personDTO.getFirstName() != null) {
                person.setFirstName(personDTO.getFirstName());
            }
            if (personDTO.getLastName() != null) {
                person.setLastName(personDTO.getLastName());
            }
            if (personDTO.getEmail() != null) {
                person.setEmail(personDTO.getEmail());
            }
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    // Delete person
    public PersonDTO deletePerson(Long id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException("Could not delete, provided id does not exist");
            }
            em.remove(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    // Get persons by hobby
    public List<PersonDTO> getPersonsByHobby(String hobby) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbies h WHERE h.name = :hobby", Person.class);
            query.setParameter("hobby", hobby);
            return PersonDTO.getDTOs(query.getResultList());
        } finally {
            em.close();
        }
    }

    // Add hobby to person
    public PersonDTO addHobbyToPerson(Long personId, Long hobbyId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, personId);
            Hobby hobby = em.find(Hobby.class, hobbyId);
            person.getHobbies().add(hobby);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }
}
