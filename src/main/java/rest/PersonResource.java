package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Person;
import errorhandling.PersonNotFoundException;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPersons())).build();
    }

    @GET
    @Path("/hobby/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsByHobby(@PathParam("hobby") String hobby) {
        List<PersonDTO> personDTOs = FACADE.getPersonsByHobby(hobby);
        return Response.ok().entity(GSON.toJson(personDTOs)).build();
    }

    @POST
    @Path("/add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addHobby(String person) {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO newPerson = FACADE.create(personDTO);
        return Response.ok().entity(GSON.toJson(newPerson)).build();
    }

    @PUT
    @Path("/edit/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editPerson(@PathParam("id") Long id, String person) {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        personDTO.setId(id);
        PersonDTO newPerson = FACADE.editPerson(personDTO);
        return Response.ok().entity(GSON.toJson(newPerson)).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePerson(@PathParam("id") Long id) throws PersonNotFoundException {
        PersonDTO personDTO = FACADE.deletePerson(id);
        return Response.ok().entity(GSON.toJson(personDTO)).build();
    }
}
