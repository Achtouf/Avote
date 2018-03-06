package fr.lyon1.avote.persistence;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DepartmentDAOTest {
    @Test
    public void getDepartmentByPostalCode() throws Exception {
        String lyon1 = "69001";
        assertEquals("69", lyon1.substring(0, 2));
    }

}