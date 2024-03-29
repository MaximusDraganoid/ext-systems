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
            "AND p.sur_name = ? " +
            "AND p.given_name = ? " +
            "AND p.patronymic = ? " +
            "AND p.date_of_birth = ? " +
            "AND a.street_code = ? " +
            "AND a.building = ? ";

    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
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
            sql += "AND a.extension = ? ";
        } else {
            sql += "AND a.extension is null ";
        }

        if (request.getApartment() != null) {
            sql += "AND a.apartment = ? ";
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
        return connectionBuilder.getConnection();
                /*
                DriverManager.getConnection("jdbc:postgresql://localhost/city_register",
                "postgres",
                "12345");
                 */
    }
}
