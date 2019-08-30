package com.kenect.kenectspringtest.controller;

import com.kenect.kenectspringtest.AbstractTest;
import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.model.Phone;
import com.kenect.kenectspringtest.testmodel.Phones;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

public class PhoneRestControllerTest extends AbstractTest {
    @Override
    @Before
    public void initMock() {
        super.initMock();
    }

    @Test
    public void getPhones() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postUri = String.format("/contacts/%d/phones", contactDB.getId());
        Phone phone = new Phone();
        phone.setNumber("1234567890");
        MvcResult mvcPost = mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(phone))).andReturn();
        assertEquals(200, mvcPost.getResponse().getStatus());

        String getUri = "/contacts/1/phones";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Phones contacts = super.mapFromJson(content, Phones.class);
        assertTrue(contacts._embedded.phoneList.length > 0);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", postContactsUri, contactDB.getId()))).andReturn();
    }

    @Test
    public void getPhone() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postUri = String.format("/contacts/%d/phones", contactDB.getId());
        Phone phone = new Phone();
        phone.setNumber("1234567890");
        MvcResult mvcPost = mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(phone))).andReturn();
        assertEquals(200, mvcPost.getResponse().getStatus());
        Phone dbEntity = super.mapFromJson(mvcPost.getResponse().getContentAsString(), Phone.class);

        String getEntityUri = String.format("%s/%d", postUri, dbEntity.getId());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getEntityUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Phone entity = super.mapFromJson(content, Phone.class);
        assertEquals(entity.getId(), dbEntity.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", postContactsUri, contactDB.getId()))).andReturn();
    }

    @Test
    public void updatePhone() throws Exception {
        String phoneToUpdate = "0987654321";
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jhon Smith");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postUri = String.format("/contacts/%d/phones", contactDB.getId());
        Phone phone = new Phone();
        phone.setNumber("1234567890");
        MvcResult mvcPost = mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(phone))).andReturn();
        assertEquals(200, mvcPost.getResponse().getStatus());
        Phone dbEntity = super.mapFromJson(mvcPost.getResponse().getContentAsString(), Phone.class);
        dbEntity.setNumber(phoneToUpdate);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(dbEntity))).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String getEntityUri = String.format("%s/%s", postUri, dbEntity.getId());
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getEntityUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString();
        Phone entity = super.mapFromJson(content, Phone.class);
        assertEquals(entity.getId(), dbEntity.getId());
        assertEquals(entity.getNumber(), phoneToUpdate);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", postContactsUri, contactDB.getId()))).andReturn();
    }


    @Test
    public void deletePhones() throws Exception {
        String postContactsUri = "/contacts";
        Contact contact = new Contact();
        contact.setName("Jack March");
        MvcResult mvcPostContact = mockMvc.perform(MockMvcRequestBuilders.post(postContactsUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(contact))).andReturn();
        assertEquals(200, mvcPostContact.getResponse().getStatus());
        Contact contactDB = super.mapFromJson(mvcPostContact.getResponse().getContentAsString(), Contact.class);

        String postUri = String.format("/contacts/%d/phones", contactDB.getId());
        Phone phone = new Phone();
        phone.setNumber("1234567890");
        MvcResult mvcPost = mockMvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(phone))).andReturn();
        assertEquals(200, mvcPost.getResponse().getStatus());
        Phone dbEntity = super.mapFromJson(mvcPost.getResponse().getContentAsString(), Phone.class);

        String deleteUri = String.format("/phones/%s", dbEntity.getId());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(deleteUri)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(postUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString();
        Phones contentDB = super.mapFromJson(content, Phones.class);
        assertNull(contentDB._embedded);

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s", postContactsUri, contactDB.getId()))).andReturn();
    }
}
