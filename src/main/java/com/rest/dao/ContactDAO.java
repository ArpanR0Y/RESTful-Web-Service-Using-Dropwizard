package com.rest.dao;

import com.rest.api.Contact;
import com.rest.dao.mappers.ContactMapper;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

//Data Access Object provides an abstract interface to a DB
public interface ContactDAO {

    //GET
    @Mapper(ContactMapper.class)
    @SqlQuery("select * from contact where id = :id")
    Contact getContactById (@Bind("id") int id);

    //POST
    @GetGeneratedKeys
    @SqlUpdate("insert into contact (id, firstName, lastName, phone) values (NULL, :firstName, :lastName, :phone)")
    int createContact(@Bind("firstName") String firstName,
                      @Bind("lastName") String lastName,
                      @Bind("phone") String phone);

    //PUT
    @SqlUpdate("update contact set firstName = :firstName, lastName = :lastName, phone = :phone where id = :id")
            void updateContact(@Bind("id") int id,
                               @Bind("firstName") String firstName,
                               @Bind("lastName") String lastName,
                               @Bind("phone") String phone);

    //DELETE
    @SqlUpdate("delete from contact where id = :id")
    void deleteContact(@Bind("id") int id);
}