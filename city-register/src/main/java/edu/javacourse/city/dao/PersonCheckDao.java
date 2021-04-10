package edu.javacourse.city.dao;

import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;

import java.sql.*;

public class PersonCheckDao {
    //COLLATE "en_US.UTF-8"
    //upper
    public static final String SQL_REQUEST = "select temporal from cr_address_person ap " +
            "inner join cr_person p on p.person_id = ap.person_id " +
            "inner join cr_address a on a.address_id = ap.address_id " +
            "where " +
            "CURRENT_DATE  >= ap.start_date AND (CURRENT_DATE <= ap.end_date OR ap.end_date is null) " +
            "AND upper(p.sur_name) = upper(?) " +
            "AND upper(p.given_name) = upper(?) " +
            "AND upper(p.patronymic) = upper(?) " +
            "AND p.date_of_birth = ? " +
            "AND a.street_code = ? " +
            "AND upper(a.building) = upper(?) ";

    public PersonCheckDao() {
        try {
            //так как в противном случае ПОЧЕМУ-ТО не можем достучаться до бд
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //метод, который проверяет есть ли такая личность в бд, т.е. проживает ли она по указанному адресу
    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
        PersonResponse response = new PersonResponse();

        /*
        подготавливаем запрос к дальнейшей работе, т.к. у нас возможны ситуации, когда не приходят данные по номеру
        квартиры и корпусу
         */
        String sql = SQL_REQUEST;
        if (request.getExtension() != null) {
            sql += "AND upper(a.extension) = upper(?) ";
        } else {
            sql += "AND a.extension is null ";
        }

        if (request.getApartment() != null) {
            sql += "AND upper(a.apartment) = upper(?) ";
        } else {
            sql += "AND a.apartment is null";
        }

        try(Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            int count = 1;
            stmt.setString(count++, request.getSurName());
            stmt.setString(count++, request.getGivenName());
            stmt.setString(count++, request.getPatronymicName());
            stmt.setDate(count++, java.sql.Date.valueOf(request.getDateOfBirthday()));
            stmt.setInt(count++, request.getStreetCode());
            stmt.setString(count++, request.getBuilding());
            if (request.getExtension() != null) {
                stmt.setString(count++, request.getExtension());
            }
            if (request.getApartment() != null) {
                stmt.setString(count++, request.getApartment());
            }


            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                response.setRegistered(true);
                response.setTemporal(resultSet.getBoolean("temporal"));
            }

        } catch (SQLException ex) {
            throw new PersonCheckException(ex);
        }

        return response;
    }

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:postgresql://localhost/city_register",
                "postgres",
                "12345");
    }
}
