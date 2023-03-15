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

    // Adding a Hobby to a Person
    public void addHobby(Long personId, Long hobbyId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, personId);
            Hobby hobby = em.find(Hobby.class, hobbyId);
            person.getHobbies().add(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Find all persons
    public List<PersonDTO> getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            return PersonDTO.getDTOs(query.getResultList());
        } finally {
            em.close();
        }
    }

    // Get information about a person given a phone number
    public PersonDTO getPersonByPhone(String phone) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.phones ph WHERE ph.number = :phone", Person.class);
            query.setParameter("phone", phone);

            return new PersonDTO(query.getSingleResult());
        } catch (Exception e) {
            throw new PersonNotFoundException("No person with phone number " + phone + " found");
        } finally {
            em.close();
        }
    }

    // Gets the number of persons in the database
    public int getPersonCount() {
        Set<Person> persons = new HashSet<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            persons.addAll(query.getResultList());
        } finally {
            em.close();
        }
        return persons.size();
    }

    // Get the number of people given a hobby
    public int getPersonCountByHobby(String hobby) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbies h WHERE h.name = :hobby", Person.class);
            query.setParameter("hobby", hobby);
            return query.getResultList().size();
        } finally {
            em.close();
        }
    }

    // Get persons living in a given zip code
    public List<PersonDTO> getPersonsByZipCode(int zipCode) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.address a WHERE a.cityInfo.zipCode = :zipCode", Person.class);
            query.setParameter("zipCode", zipCode);
            return PersonDTO.getDTOs(query.getResultList());
        } finally {
            em.close();
        }
    }

    // Get persons with a given hobby
    public List<PersonDTO> getPersonsWithHobby(String hobby) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbies h WHERE h.name = :hobby", Person.class);
            query.setParameter("hobby", hobby);
            List<Person> persons = query.getResultList();
            return PersonDTO.getDTOs(persons);
        } finally {
            em.close();
        }
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

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade facade = getPersonFacade(emf);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(2L);
        personDTO.setFirstName("Jens");
        personDTO.setLastName("Petersen");
        facade.editPerson(personDTO);
    }

}
