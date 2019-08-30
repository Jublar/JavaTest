package com.kenect.kenectspringtest.controller;

import com.kenect.kenectspringtest.AbstractTest;
import com.kenect.kenectspringtest.constants.Constants;
import com.kenect.kenectspringtest.dto.SearchPayLoad;
import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.testmodel.Contacts;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

public class ContactRestControllerTest extends AbstractTest {

    @Override
    @Before
    public void initMock() {
        super.initMock();
    }

    @Test
    public void getContacts() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        String inputJson = super.mapToJson(contact);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        Contact contactDB = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Contact.class);

        String getContactsUri = "/contacts";
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getContactsUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Contacts contacts = super.mapFromJson(content, Contacts.class);
        assertTrue(contacts._embedded.contactList.length > 0);

        String deleteUri = String.format("/contacts/%d", contactDB.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete(deleteUri)).andReturn();
    }

    @Test
    public void getContactsById() throws Exception {
        String postContactsUri = "/contacts";
        Contact newContact = new Contact();
        newContact.setName("Jhon Smith");
        String inputJson = super.mapToJson(newContact);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        Contact contactDB = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Contact.class);

        String getContactsUri = String.format("/contacts/%d", contactDB.getId());
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getContactsUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        contactDB = super.mapFromJson(content, Contact.class);
        assertTrue(contactDB != null);

        String deleteUri = String.format("/contacts/%d", contactDB.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete(deleteUri)).andReturn();
    }

    @Test
    public void getContactNotFound() throws Exception {
        String postContactsUri = "/contacts";
        Contact newContact = new Contact();
        newContact.setName("Jhon Smith");
        String inputJson = super.mapToJson(newContact);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        Contact contactDB = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Contact.class);

        Long idToFound = contactDB.getId() + 1;
        String getContactsUri = String.format("/contacts/%d", idToFound);
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getContactsUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(String.format(Constants.MSG_NO_ELEMENT, idToFound), content);

        String deleteUri = String.format("/contacts/%d", contactDB.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete(deleteUri)).andReturn();
    }

    @Test
    public void search() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        String inputJson = super.mapToJson(contact);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        Contact contactDB = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Contact.class);

        String postSearchUri = "/contacts/search";
        SearchPayLoad search = new SearchPayLoad();
        search.setQuery("Jhon");
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(postSearchUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(search))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Contacts contacts = super.mapFromJson(content, Contacts.class);
        assertTrue(contacts._embedded.contactList.length > 0);

        String deleteUri = String.format("/contacts/%d", contactDB.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete(deleteUri)).andReturn();
    }

    @Test
    public void putContact() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        String inputJson = super.mapToJson(contact);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        Contact contactDB = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Contact.class);
        assertEquals(contactDB.getName(), contact.getName());

        String putContactsUri = "/contacts";
        contact.setId(contactDB.getId());
        contact.setName("Michael");
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(putContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        contactDB = super.mapFromJson(content, Contact.class);
        assertEquals(contactDB.getName(), contact.getName());

        String deleteUri = String.format("/contacts/%d", contactDB.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete(deleteUri)).andReturn();
    }

    @Test
    public void delete() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        String inputJson = super.mapToJson(contact);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Contact contactDB = super.mapFromJson(content, Contact.class);

        String getContactsUri = "/contacts";
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getContactsUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        content = mvcResult.getResponse().getContentAsString();
        Contacts contacts = super.mapFromJson(content, Contacts.class);
        assertTrue(contacts._embedded.contactList.length > 0);

        String deleteUri = String.format("/contacts/%d", contactDB.getId());
        SearchPayLoad search = new SearchPayLoad();
        search.setQuery("Jhon");
        mockMvc.perform(MockMvcRequestBuilders.delete(deleteUri)).andReturn();

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getContactsUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        content = mvcResult.getResponse().getContentAsString();
        contacts = super.mapFromJson(content, Contacts.class);
        assertNull(contacts._embedded);
    }
}
