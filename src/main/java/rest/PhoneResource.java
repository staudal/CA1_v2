package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import facades.CityInfoFacade;
import facades.PhoneFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("phone")
public class PhoneResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PhoneFacade FACADE =  PhoneFacade.getPhoneFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllCities() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPhones())).build();
    }

    @POST
    @Path("/add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addPhone(String phone) {
        PhoneDTO phoneDTO = GSON.fromJson(phone, PhoneDTO.class);
        PhoneDTO newPhone = FACADE.create(phoneDTO);
        return Response.ok().entity(GSON.toJson(newPhone)).build();
    }

    @PUT
    @Path("/edit/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editPhone(@PathParam("id") Long id, String phone) {
        PhoneDTO phoneDTO = GSON.fromJson(phone, PhoneDTO.class);
        phoneDTO.setId(id);
        PhoneDTO newPhone = FACADE.editPhone(phoneDTO);
        return Response.ok().entity(GSON.toJson(newPhone)).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePhone(@PathParam("id") Long id) {
        PhoneDTO phoneDTO = FACADE.deletePhone(id);
        return Response.ok().entity(GSON.toJson(phoneDTO)).build();
    }
}
