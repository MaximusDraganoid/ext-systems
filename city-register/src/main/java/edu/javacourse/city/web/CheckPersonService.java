package edu.javacourse.city.web;

import edu.javacourse.city.dao.PersonCheckDao;
import edu.javacourse.city.domain.PersonResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/check")
public class CheckPersonService {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON) //задет header accept(или  content type)и преобразует выходной параметр метода в
    // .json. Но для того, чтобы такое преобразование выполнилось необходимо, чтобы было подключен ряд библиотек.
    public PersonResponse checkPerson(@PathParam("id") int simpleId,
                                      @QueryParam("name") String simpleName) {
        PersonResponse personResponse = new PersonResponse();

        return personResponse;
    }
}
