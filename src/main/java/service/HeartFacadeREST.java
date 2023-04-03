/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cst8218.jusk0003.heartbeat.Heart;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Implementation of a RESTful API. It contains logic for the business layer and
 * facilitates HTTP requests and responses from the application. It is a stateless
 * EJB. It extends the AbstractFacade class, which allows for CRUD-like operations on
 * Heart objects in the database.
 * 
 * @author Daniel Juskevicius
 */
@Stateless
@Path("cst8218.jusk0003.heartbeat.heart")
public class HeartFacadeREST extends AbstractFacade<Heart> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public HeartFacadeREST() {
        super(Heart.class);
    }

    /**
     * Creates or updates a Heart object. If there is no ID listed in the POST request, then
     * we must be creating a new Heart object. If an ID is provided in the body of the POST
     * request, then the Heart that corresponds to that ID is updated with the values
     * provided in the POST request body. Any values that are not provided in the POST request
     * body are not overwritten and persist.
     * 
     * @param heart The Heart Bean to be POSTed.
     * @return A response depending on the status of the POST request, either CREATED, OK, or BAD_REQUEST.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createHeart(Heart heart) {
        if (heart.getId() == null) { // must be a new heart
            super.create(heart);
            return Response.status(Response.Status.CREATED).build();
        }
        else if (heart.getId() != null && super.find(heart.getId()) != null) { // must be editing an existing heart
            heart.updates(super.find(heart.getId()));
            return Response.status(Response.Status.OK).build();
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Updates an existing Heart object that corresponds to the ID in the URI. If the ID in the URI does not exist
     * in the database or it does not match the ID listed in the POST request body, then a BAD_REQUEST response is
     * returned.
     * 
     * @param id The ID listed in the URI, matching the ID of the Heart to be updated.
     * @param heart The Heart object to be updated.
     * @return A response depending on the status of the POST request, either OK or BAD_REQUEST.
     */
    @POST
    @Path("{id}")
    public Response update(@PathParam("id") Long id, Heart heart) {
        if (super.find(id) == null || !(id.equals(heart.getId()))) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            heart.updates(super.find(heart.getId()));
        }
        return Response.status(Response.Status.OK).entity(heart).build();
    }
    
    /**
     * Prevents a PUT request from being applied to the root resource, as this would
     * change the contents of the entire database.
     * 
     * @return a METHOD_NOT_ALLOWED resource.
     */
    @PUT
    public Response notAllowed() {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
    
    /**
     * Replaces the values of an existing Heart with those provided in the body of the PUT
     * request. If the ID in the URI does not exist in the database or it does not match the ID
     * listed in the POST request body then a BAD_REQUEST response is returned.
     * 
     * @param id The ID of the Heart to be changed.
     * @param heart The heart object to be changed.
     * @return A response depending on the status of the POST request, either BAD_REQUEST or OK.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, Heart heart) {
        if (super.find(id) == null || !(id.equals(heart.getId()))) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        super.edit(heart);
        return Response.status(Response.Status.OK).entity(heart).build();
    }

    /**
     * Deletes an entity from the database with the associated ID.
     * 
     * @param id The ID of the Heart object to be deleted.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     * Gets the Heart associate with the ID.
     * 
     * @param id
     * @return The Heart object associated with that ID.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Heart find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * Gets all Heart objects from the database.
     * 
     * @return A list of all the hearts in the database.
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Heart> findAll() {
        return super.findAll();
    }

    /**
     * Gets the range of Hearts from the database.
     * 
     * @param from The starting point of the range.
     * @param to The end point of the range.
     * @return The list of the hearts inside that range from the database.
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Heart> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * Counts the number of Hearts in the database and returns that value
     * in json.
     * 
     * @return The number of hearts on the database.
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public String countREST() {
        return ("The number of Hearts is: " + String.valueOf(super.count()));
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
