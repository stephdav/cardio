package com.sopra.agile.cardio.ui.pebble;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import spark.ModelAndView;
import spark.utils.IOUtils;

public class FixedPebbleEngineTest {

    private FixedPebbleEngine templateEngine;

    @Before
    public void setUp() throws Exception {
        templateEngine = new FixedPebbleEngine();
    }

    @Test
    public void testRenderModelAndView() throws IOException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Cardio");

        String viewName = "templates/test.peb";
        ModelAndView modelAndView = new ModelAndView(model, viewName);

        String expected = getFileFromResource("./test.html");
        Assert.assertEquals(expected, templateEngine.render(modelAndView));

    }

    private String getFileFromResource(String path) throws IOException {
        InputStream stream = FixedPebbleEngineTest.class.getClassLoader().getResourceAsStream(path);
        try {
            return IOUtils.toString(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
}
