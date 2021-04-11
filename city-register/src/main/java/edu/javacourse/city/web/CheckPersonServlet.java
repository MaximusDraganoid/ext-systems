package edu.javacourse.city.web;

import edu.javacourse.city.PoolConnectionBuilder;
import edu.javacourse.city.dao.PersonCheckDao;
import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "CheckPersonServlet", urlPatterns = {"/checkPerson"})
public class CheckPersonServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CheckPersonServlet.class);

    private PersonCheckDao dao;

    //для инициализации этого объекта воспользуемся методом init(), который будет вызван 1 раз во всем жизненном цикле
    //приложения => создаем наше dao 1 единственный раз
    @Override
    public void init() throws ServletException {
        logger.info("servlet is created");
        dao = new PersonCheckDao();
        dao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //указываем, что будем считывать входные данные запроса в формате UTF-8 (для корректной обработки данных запросов)
        req.setCharacterEncoding("UTF-8");

        String surName = req.getParameter("surname");

        PersonRequest pr = new PersonRequest();
        pr.setSurName(surName);
        pr.setGivenName("Павел");
        pr.setPatronymicName("Николаевич");
        pr.setDateOfBirthday(LocalDate.of(1995, 3, 8));
        pr.setStreetCode(1);
        pr.setBuilding("10");
        pr.setApartment("121");
        pr.setExtension("2");

        try {
            PersonResponse ps = dao.checkPerson(pr);
            if (ps.isRegistered()) {
                resp.getWriter().write("Registered");
            } else {
                resp.getWriter().write("not Registered");
            }
        } catch (Exception e) {
            resp.getWriter().write(e.getMessage());
        }
    }
}
