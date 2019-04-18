package com.seb.pelicanapp.filter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.seb.pelicanapp.service.JsonKeyManipulatorService;
import com.seb.pelicanapp.service.LetterCapitalizorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static java.nio.charset.StandardCharsets.UTF_8;
/**
 * This filter is being used in purpose of manipulating responses from language services.

 */

@Component
public class DataFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(DataFilter.class);
    private static final String FILTER_TYPE = "post"; // Execute this filter after routing.
    private static final int FILTER_ORDER = 1; // Execute this filter first.
    private static final boolean SHOULD_FILTER = true;
    private static final String PATH_ERROR = "The path you've requested not found.";

    private final LetterCapitalizorService letterCapitalizorService;
    private final JsonKeyManipulatorService jsonKeyManipulatorService;

    @Autowired
    public DataFilter(LetterCapitalizorService letterCapitalizorService, JsonKeyManipulatorService jsonKeyManipulatorService) {
        this.letterCapitalizorService = letterCapitalizorService;
        this.jsonKeyManipulatorService = jsonKeyManipulatorService;
    }


    @Override
    public String filterType() {
        return FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext context = getCurrentContext();
            InputStream stream = context.getResponseDataStream();
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8")); // Fetch body from the response
            ObjectNode manipulatedBody = jsonKeyManipulatorService.replaceKey(body, "translation", "data"); // Replace translation key of json to data.
            String translationService = context.getZuulResponseHeaders().get(0).second(); // Get first header(translation-service) value of response

            //Check if header value equals the estonia. Then manipulate first letter of response data.
            if (translationService.equals("estonia")) {
                logger.info("I'm capitalizing the first letter of translation data responded from Estonia Service.");
                manipulatedBody = letterCapitalizorService.capitalizeLetter(manipulatedBody, "data", 0); // Assign manipulated object to body as string type.
            }
            context.setResponseDataStream(new ByteArrayInputStream(manipulatedBody.toString().getBytes(UTF_8))); // Set context's data stream from body
        } catch (Exception e) {
            /*
                Catch runtime exception.
             */
            logger.error(e.toString());
            getCurrentContext().setResponseDataStream(new ByteArrayInputStream(PATH_ERROR.getBytes(UTF_8)));
        }
        return null;
    }
}
