package com.kenect.kenectspringtest.controller;

import com.kenect.kenectspringtest.AbstractTest;
import com.kenect.kenectspringtest.model.EmailAddress;
import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.testmodel.Emails;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmailRestControllerTest extends AbstractTest {
    @Override
    @Before
    public void initMock() {
        super.initMock();
    }

    @Test
    public void getMails() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postEmailUri = String.format("/contacts/%d/emails", contactDB.getId());
        EmailAddress email = new EmailAddress();
        email.setEmail("jhon.smit@corp.com");
        MvcResult mvcPostEmail = mockMvc.perform(MockMvcRequestBuilders.post(postEmailUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(email))).andReturn();
        assertEquals(200, mvcPostEmail.getResponse().getStatus());

        String getMailsUri = "/contacts/1/emails";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getMailsUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Emails contacts = super.mapFromJson(content, Emails.class);
        assertTrue(contacts.getContent().length > 0);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", postContactsUri, contactDB.getId()))).andReturn();
    }

    @Test
    public void getMail() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postUri = String.format("/contacts/%d/emails", contactDB.getId());
        EmailAddress email = new EmailAddress();
        email.setEmail("jhon.smit@corp.com");
        MvcResult mvcPostEmail = mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(email))).andReturn();
        assertEquals(200, mvcPostEmail.getResponse().getStatus());
        EmailAddress dbEntity = super.mapFromJson(mvcPostEmail.getResponse().getContentAsString(), EmailAddress.class);

        String getEntityUri = String.format("%s/%d", postUri, dbEntity.getId());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getEntityUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        EmailAddress entity = super.mapFromJson(content, EmailAddress.class);
        assertEquals(entity.getId(), dbEntity.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", postContactsUri, contactDB.getId()))).andReturn();
    }

    @Test
    public void updateMail() throws Exception {
        String mailToUpdate = "smith@corp.com";
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postUri = String.format("/contacts/%d/emails", contactDB.getId());
        EmailAddress email = new EmailAddress();
        email.setEmail("jhon.smit@corp.com");
        MvcResult mvcPostEmail = mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(email))).andReturn();
        assertEquals(200, mvcPostEmail.getResponse().getStatus());
        EmailAddress dbEntity = super.mapFromJson(mvcPostEmail.getResponse().getContentAsString(), EmailAddress.class);
        dbEntity.setEmail(mailToUpdate);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(dbEntity))).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String getEntityUri = String.format("%s/%s", postUri, dbEntity.getId());
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getEntityUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString();
        EmailAddress entity = super.mapFromJson(content, EmailAddress.class);
        assertEquals(entity.getId(), dbEntity.getId());
        assertEquals(entity.getEmail(), mailToUpdate);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", postContactsUri, contactDB.getId()))).andReturn();
    }


    @Test
    public void deleteMail() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jack March");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postUri = String.format("/contacts/%d/emails", contactDB.getId());
        EmailAddress email = new EmailAddress();
        email.setEmail("jhon.smit@corp.com");
        MvcResult mvcPostEmail = mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(email))).andReturn();
        assertEquals(200, mvcPostEmail.getResponse().getStatus());
        EmailAddress dbEntity = super.mapFromJson(mvcPostEmail.getResponse().getContentAsString(), EmailAddress.class);

        String deleteUri = String.format("/emails/%s", dbEntity.getId());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(deleteUri)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(postUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString();
        Emails contentDB = super.mapFromJson(content, Emails.class);
        assertEquals(contentDB.getContent().length, 0);
    }
}
