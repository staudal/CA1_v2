package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyDTO;
import facades.HobbyFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("hobby")
public class HobbyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final HobbyFacade FACADE =  HobbyFacade.getHobbyFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllHobbies() {
        List<HobbyDTO> hobbyDTOList = FACADE.getAllHobbies();
        return GSON.toJson(hobbyDTOList);
    }

    @POST
    @Path("/add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addHobby(String hobby) {
        HobbyDTO hobbyDTO = GSON.fromJson(hobby, HobbyDTO.class);
        HobbyDTO newHobbyDTO = FACADE.create(hobbyDTO);
        return GSON.toJson(newHobbyDTO);
    }

    @PUT
    @Path("/edit/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String editHobby(@PathParam("id") Long id, String hobby) {
        HobbyDTO hobbyDTO = GSON.fromJson(hobby, HobbyDTO.class);
        hobbyDTO.setId(id);
        HobbyDTO newHobby = FACADE.editHobby(hobbyDTO);
        return GSON.toJson(newHobby);
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String deleteHobby(@PathParam("id") Long id) {
        HobbyDTO hobbyDTO = FACADE.deleteHobby(id);
        return GSON.toJson(hobbyDTO);
    }
}
