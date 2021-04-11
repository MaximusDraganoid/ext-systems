package edu.javacourse.city;

import edu.javacourse.city.dao.ConnectionBuilder;
import edu.javacourse.city.web.CheckPersonServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnectionBuilder implements ConnectionBuilder {

    /*
    фактически мы при помощи файлов context.xml и web.xml зарегистрировали ресурс "пул подключений" на нашем серврее и теперь
    при необходимости можем обращатсья к нему. Чтобы воспользоваться локальными ресурсами нашего сервера, нужно использовать
    локальный контекст

     */
    private DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(PoolConnectionBuilder.class);

    public PoolConnectionBuilder() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/cityRegister");

        } catch (NamingException e) {
            logger.error("", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
