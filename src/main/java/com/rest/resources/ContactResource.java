package com.rest.resources;


import javax.annotation.security.PermitAll;
import javax.validation.ConstraintViolation;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.validation.Validator;

import com.rest.api.*;
import com.rest.auth.User;
import com.rest.dao.ContactDAO;
import io.dropwizard.auth.Auth;
import org.skife.jdbi.v2.DBI;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;


@Path("/contact")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactDAO contactDao;
    private final Validator validator;

    public ContactResource(DBI jdbi, Validator validator) {
        contactDao = jdbi.onDemand(ContactDAO.class);
        this.validator = validator;
    }

    @GET    // retrieve information about the contact with the provided id
    @PermitAll
    @Path("/{id}")
    /*@Produces(MediaType.APPLICATION_JSON)*/
    public Response getContact(@PathParam("id") int id, @Auth User user) {

        Contact contact = contactDao.getContactById(id);
        return Response.ok(contact).build();
    }


    @POST   //create new contact
    public Response createContact( Contact contact) throws URISyntaxException {

        //Validate the new data
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);

        if (violations.size() > 0) {

            //constraint violation present

            ArrayList<String> validationMessages = new ArrayList<>();
            for (ConstraintViolation<Contact> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() +":" + violation.getMessage());
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        }
        else {

            //no constraint violations

            int newContactId = contactDao.createContact(
                               contact.getFirstName(),
                               contact.getLastName(),
                               contact.getPhone());
            return Response.created(new URI(String.valueOf(newContactId))).build();
        }

    }

    @PUT    // update the contact with the provided ID
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateContact(@PathParam("id") int id, Contact contact) {

        //Validate the new data
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);

        if (violations.size() > 0) {

            //constraint violation present

            ArrayList<String> validationMessages = new ArrayList<>();
            for (ConstraintViolation<Contact> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() +":" + violation.getMessage());
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        }
        else {
            contactDao.updateContact(id, contact.getFirstName(),
                                         contact.getLastName(),
                                         contact.getPhone());
            return Response.ok(new Contact(id, contact.getFirstName(),
                                               contact.getLastName(),
                                               contact.getPhone())).build();
        }
    }

    @DELETE    // delete the contact with the provided id
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        contactDao.deleteContact(id);
        return Response.noContent().build();
    }




}