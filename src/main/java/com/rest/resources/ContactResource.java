package com.rest.resources;


import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.rest.api.*;
import com.rest.dao.ContactDAO;
import org.skife.jdbi.v2.DBI;

import java.net.URI;
import java.net.URISyntaxException;


@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)

public class ContactResource {

    private final ContactDAO contactDao;

    public ContactResource(DBI jdbi) {
        contactDao = jdbi.onDemand(ContactDAO.class);
    }

    @GET    // retrieve information about the contact with the provided id
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContact(@PathParam("id") int id) {

        Contact contact = contactDao.getContactById(id);
        return Response.ok(contact).build();
    }


    @POST   //create new contact
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createContact( Contact contact) throws URISyntaxException {
            int newContactId = contactDao.createContact(
                                contact.getFirstName(),
                                contact.getLastName(),
                                contact.getPhone());
            return Response.created(new URI(String.valueOf(newContactId))).build();

    }

    @PUT    // update the contact with the provided ID
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateContact(@PathParam("id") int id, Contact contact) {
        contactDao.updateContact(id, contact.getFirstName(),
                                     contact.getLastName(),
                                     contact.getPhone());
        return Response.ok(new Contact(id, contact.getFirstName(),
                                           contact.getLastName(),
                                           contact.getPhone())).build();
    }

    @DELETE    // delete the contact with the provided id
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        contactDao.deleteContact(id);
        return Response.noContent().build();
    }



}