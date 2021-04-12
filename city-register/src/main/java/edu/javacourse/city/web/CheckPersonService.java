package edu.javacourse.city.web;

import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;

@Path("/check")
public class CheckPersonService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) //задет header accept(или  content type)и преобразует выходной параметр метода в
    // .json. Но для того, чтобы такое преобразование выполнилось необходимо, чтобы было подключен ряд библиотек.
    public PersonResponse checkPerson(PersonRequest request) {
        System.out.println(request.toString());
        PersonResponse pr = new PersonResponse();
        return pr;
    }
}
