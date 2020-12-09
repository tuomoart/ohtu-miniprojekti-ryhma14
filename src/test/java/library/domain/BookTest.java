/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

/**
 *
 * @author ikpa
 */
public class BookTest {
    private Book book;
    private List<String> stringArr;
    
    public BookTest() {
        
    }
    
    @Before
    public void setUp() {
        book = new Book("testi", "b-39", "pertti perttinen", "2002", "865", "0000-000-00-0", false);
        stringArr = new ArrayList();
        stringArr.add("testi");
        stringArr.add("pertti perttinen");
        stringArr.add("2002");
        stringArr.add("865");
        stringArr.add("0000-000-00-0");
        stringArr.add("b-39");
        stringArr.add("false");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testToStringList() {
        assertEquals(book.toStringList(), stringArr);
    }

    
}
