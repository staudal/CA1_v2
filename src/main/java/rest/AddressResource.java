package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AddressDTO;
import facades.AddressFacade;
import facades.HobbyFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("address")
public class AddressResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final AddressFacade FACADE =  AddressFacade.getAddressFacade(EMF);
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
        return Response.ok().entity(GSON.toJson(FACADE.getAllAddresses())).build();
    }

    @POST
    @Path("/add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addAddress(String address) {
        AddressDTO addressDTO = GSON.fromJson(address, AddressDTO.class);
        AddressDTO newAddress = FACADE.create(addressDTO);
        return Response.ok().entity(GSON.toJson(newAddress)).build();
    }

    @PUT
    @Path("/edit/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editAddress(@PathParam("id") Long id, String address) {
        AddressDTO addressDTO = GSON.fromJson(address, AddressDTO.class);
        addressDTO.setId(id);
        AddressDTO newAddress = FACADE.editAddress(addressDTO);
        return Response.ok().entity(GSON.toJson(newAddress)).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteAddress(@PathParam("id") Long id) {
        AddressDTO addressDTO = FACADE.deleteAddress(id);
        return Response.ok().entity(GSON.toJson(addressDTO)).build();
    }
}
