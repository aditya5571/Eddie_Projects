package app.dao;

import app.controller.utils.ViewUtil;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance;

import java.util.Map;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RequestDAO_CheckRequestSource {

    // Declaring private variables
    private RequestDAO requestDAO;

    @BeforeAll
    void initialise() {
        requestDAO = new RequestDAO();
    }



    /*
    BASIC TEST CASES
     */

    @Test
    void checkRequestSource_True_IfSimpleEmailString() {
        assertTrue(requestDAO.checkRequestSource("abc@gmail.com", "Australia"),
                "Simple email string should return true");
    }

    @Test
    void checkRequestSource_True_IfEmailStringContainsNumbers() {
        assertTrue(requestDAO.checkRequestSource("123@gmail.com", "Australia"),
                "Email string containing numbers should return true");
    }

    @Test
    void checkRequestSource_True_IfMixedEmailString() {
        assertTrue(requestDAO.checkRequestSource("drillbit5000@gmail.com", "Australia"),
                "Mixed email string should return true");
    }

    @Test
    void checkRequestSource_True_IfImproperEmailString() {
        assertTrue(requestDAO.checkRequestSource("..@gmail.com", "Australia"),
                "Improper string should return true");
    }

    @Test
    void checkRequestSource_False_IfDifferentCountryString() {
        assertFalse(requestDAO.checkRequestSource("abc@gmail.com", "Canada"),
                "Other country source should return false");
    }

    @Test
    void checkRequestSource_False_IfIncorrectCountryString() {
        assertFalse(requestDAO.checkRequestSource("abc@gmail.com", "asiit22"),
                "Other country source should return false");
    }

    @Test
    void checkRequestSource_False_IfCorrectCountryStringInCapital() {
        assertFalse(requestDAO.checkRequestSource("abc@gmail.com", "AUSTRALIA"),
                "Overcapitalizing should return false");
    }



    /*
    EDGE/BORDERLINE TEST CASES
     */

    @Test
    void checkRequestSource_False_IfConfusingEmailString() {
        assertFalse(requestDAO.checkRequestSource(".%6$3#.@gmail.com", "Australia"),
                "Confusing email string should return false");
    }

    @Test
    void checkRequestSource_False_IfAllFieldsIncorrect() {
        assertFalse(requestDAO.checkRequestSource("#robot%%).@gmail.com", "Mexico"),
                "Both sources incorrect should return false");
    }

    @Test
    void checkRequestSource_throwsException_IfAllFieldsNull() {
        assertThrows(
                NullPointerException.class,
                () -> requestDAO.checkRequestSource(null, null),
                "Null sources should throw (NullPointerException)");
    }

}
