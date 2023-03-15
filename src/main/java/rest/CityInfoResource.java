package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import facades.CityInfoFacade;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("city")
public class CityInfoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final CityInfoFacade FACADE =  CityInfoFacade.getCityInfoFacade(EMF);
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
        return Response.ok().header("Access-Control-Allow-Origin", "*").entity(GSON.toJson(FACADE.getAllCities())).build();
    }

    @POST
    @Path("/add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addCity(String city) {
        CityInfoDTO cityDTO = GSON.fromJson(city, CityInfoDTO.class);
        CityInfoDTO newCity = FACADE.create(cityDTO);
        return Response.ok().entity(GSON.toJson(newCity)).build();
    }

    @PUT
    @Path("/edit/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editCity(@PathParam("id") Long id, String city) {
        CityInfoDTO cityDTO = GSON.fromJson(city, CityInfoDTO.class);
        cityDTO.setId(id);
        CityInfoDTO newCity = FACADE.editCity(cityDTO);
        return Response.ok().entity(GSON.toJson(newCity)).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteCity(@PathParam("id") Long id) {
        CityInfoDTO cityDTO = FACADE.deleteCity(id);
        return Response.ok().entity(GSON.toJson(cityDTO)).build();
    }
}
