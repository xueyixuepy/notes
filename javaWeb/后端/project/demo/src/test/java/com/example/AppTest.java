package com.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        System.out.println("hello test");
    }
    @Test 
    public void getGender(){
        App app = new App();
        String gender = app.getGender("123456789123456789");
        System.out.println(gender);
        Assertions.assertEquals("女",gender);
    }
    @ParameterizedTest
    @ValueSource(strings = {"123456789123456789","123456789123456799","123456789123456788"})
    public void getGender(String ids){
        App app = new App();
        String gender = app.getGender(ids);
        System.out.println(gender);
    }
    @ParameterizedTest
    @CsvSource({
        "123456789123456789, 女",
        "110101199001011234, 男",
        "440101199505056789, 女"
    })
    public void testGetGender(String idCard, String expectedGender) {
        App app = new App();
        String result = app.getGender(idCard);
        assertEquals(expectedGender, result);
    }
}
