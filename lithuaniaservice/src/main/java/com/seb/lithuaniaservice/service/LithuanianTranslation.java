package com.seb.lithuaniaservice.service;


import com.seb.translationcore.pojo.TranslationPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Response will be always same which is translation of "Hello". So I'm creating the object once and using it unless it's removed from the memory.
 * I could have make an abstraction of getTranslation method and translation object but I don't want all translation classes change one object(translation string value in this case) everytime.
 * Rather I prefered to create static objects for each translation classes.(There will be only 3 translation object in our case.)
 */
public class LithuanianTranslation {
    private static TranslationPojo translation;
    private static final Logger logger = LoggerFactory.getLogger(LithuanianTranslation.class);
    private static final String LITHUANIAN_TRANSLATION = "Labas";

    private LithuanianTranslation() {
    }

    public static TranslationPojo getTranslation() {
        if (translation == null) {
            logger.info("Instance Object for Lithuanian Translation has not been created yet. I'm creating now.");
            translation = new TranslationPojo(LITHUANIAN_TRANSLATION);
        }
        return translation;
    }
}
