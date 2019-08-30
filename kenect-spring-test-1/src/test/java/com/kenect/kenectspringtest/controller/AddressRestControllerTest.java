package com.kenect.kenectspringtest.controller;

import com.kenect.kenectspringtest.AbstractTest;
import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.model.Address;
import com.kenect.kenectspringtest.testmodel.Addresses;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

public class AddressRestControllerTest extends AbstractTest {
    @Override
    @Before
    public void initMock() {
        super.initMock();
    }

    @Test
    public void getAddresses() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postUri = String.format("/contacts/%d/addresses", contactDB.getId());
        Address address = new Address();
        address.setStreet("sesame");
        MvcResult mvcPost = mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(address))).andReturn();
        assertEquals(200, mvcPost.getResponse().getStatus());

        String getUri = "/contacts/1/addresses";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Addresses contacts = super.mapFromJson(content, Addresses.class);
        assertTrue(contacts._embedded.addressList.length > 0);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", postContactsUri, contactDB.getId()))).andReturn();
    }

    @Test
    public void getAddress() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postUri = String.format("/contacts/%d/addresses", contactDB.getId());
        Address address = new Address();
        address.setStreet("sesame");
        MvcResult mvcPost = mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(address))).andReturn();
        assertEquals(200, mvcPost.getResponse().getStatus());
        Address dbEntity = super.mapFromJson(mvcPost.getResponse().getContentAsString(), Address.class);

        String getEntityUri = String.format("%s/%d", postUri, dbEntity.getId());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getEntityUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Address entity = super.mapFromJson(content, Address.class);
        assertEquals(entity.getId(), dbEntity.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", postContactsUri, contactDB.getId()))).andReturn();
    }

    @Test
    public void updateAddress() throws Exception {
        String streetToUpdate = "new street";
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postUri = String.format("/contacts/%d/addresses", contactDB.getId());
        Address address = new Address();
        address.setStreet("sesame");
        MvcResult mvcPost = mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(address))).andReturn();
        assertEquals(200, mvcPost.getResponse().getStatus());
        Address dbEntity = super.mapFromJson(mvcPost.getResponse().getContentAsString(), Address.class);
        dbEntity.setStreet(streetToUpdate);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(dbEntity))).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String getEntityUri = String.format("%s/%s", postUri, dbEntity.getId());
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getEntityUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString();
        Address entity = super.mapFromJson(content, Address.class);
        assertEquals(entity.getId(), dbEntity.getId());
        assertEquals(entity.getStreet(), streetToUpdate);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", postContactsUri, contactDB.getId()))).andReturn();
    }


    @Test
    public void deleteAddress() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jack March");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postUri = String.format("/contacts/%d/addresses", contactDB.getId());
        Address address = new Address();
        address.setStreet("sesame");
        MvcResult mvcPost = mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(address))).andReturn();
        assertEquals(200, mvcPost.getResponse().getStatus());
        Address dbEntity = super.mapFromJson(mvcPost.getResponse().getContentAsString(), Address.class);

        String deleteUri = String.format("/addresses/%s", dbEntity.getId());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(deleteUri)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(postUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString();
        Addresses contentDB = super.mapFromJson(content, Addresses.class);
        assertNull(contentDB._embedded);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", postContactsUri, contactDB.getId()))).andReturn();
    }
}
