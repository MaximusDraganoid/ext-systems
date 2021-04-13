package edu.javacourse.city.web;

import edu.javacourse.city.PoolConnectionBuilder;
import edu.javacourse.city.dao.PersonCheckDao;
import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.time.LocalDate;

@Path("/check")
@Singleton
public class CheckPersonService {

    private static final Logger logger = LoggerFactory.getLogger(CheckPersonService.class);

    private PersonCheckDao dao;

    @PostConstruct
    public void init() {
        dao = new PersonCheckDao();
        dao.setConnectionBuilder(new PoolConnectionBuilder());
        logger.info("OBJECT CREATED");
    }

    @PreDestroy
    public void destroy() {
        logger.info("OBJECT DESTROYED");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) //задет header accept(или  content type)и преобразует выходной параметр метода в
    // .json. Но для того, чтобы такое преобразование выполнилось необходимо, чтобы было подключен ряд библиотек.
    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException, UnsupportedEncodingException {

        //почему то слетает кодировка при конвертировании json в объект и приходится вот так танцевать с бубном
        //todo : решить вопрос с кодировкой
        request.setSurName(new String(request.getSurName().getBytes("windows-1251"), "UTF-8"));
        request.setGivenName(new String(request.getGivenName().getBytes("windows-1251"), "UTF-8"));
        request.setPatronymicName(new String(request.getPatronymicName().getBytes("windows-1251"), "UTF-8"));

        logger.info(request.toString());
        PersonResponse pr = dao.checkPerson(request);
        return pr;
    }
}
