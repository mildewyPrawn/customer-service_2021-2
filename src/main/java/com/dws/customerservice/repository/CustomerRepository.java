package com.dws.customerservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dws.customerservice.dto.Customer;
import com.dws.customerservice.dto.Region;
import com.dws.customerservice.dto.RespuestaApi;

@Repository
public class CustomerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Customer> getCustomers(){
        List<Customer> customers = new ArrayList<>();
        customers = jdbcTemplate.query("SELECT * FROM customer;", new RowMapper<Customer>() {
			
                @Override
                public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Customer customer = new Customer();
                    customer.setId(rs.getInt(1));
                    customer.setNombre(rs.getString(2));
                    customer.setApellidos(rs.getString(3));
                    customer.setRfc(rs.getString(4));
                    customer.setCorreo(rs.getString(5));
                    customer.setId_region(rs.getInt(6));
				
                    return customer;
                }
            });
		
        return customers;
    }

    public Customer getCustomer(String rfc) {
        Customer customer = new Customer();
        customer = jdbcTemplate.queryForObject("SELECT * FROM customer c, region r WHERE c.id_region = r.id AND c.rfc = '" + rfc + "';", new RowMapper<Customer>() {

                @Override
                public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Customer customer = new Customer();
                    customer.setId(rs.getInt(1));
                    customer.setNombre(rs.getString(2));
                    customer.setApellidos(rs.getString(3));
                    customer.setRfc(rs.getString(4));
                    customer.setCorreo(rs.getString(5));
                    customer.setId_region(rs.getInt(6));

                    return customer;
                }
            });

        return customer;
    }

    public RespuestaApi createCustomer(Customer customer) {
        jdbcTemplate.update("INSERT INTO customer (nombre, apellidos, rfc, correo, id_region) VALUES("
                            + "'" + customer.getNombre() + "',"
                            + "'" + customer.getApellidos() + "',"
                            + "'" + customer.getRfc() + "',"
                            + "'" + customer.getCorreo() + "',"
                            + "'" + customer.getId_region() + "'"
                            + ");");
        RespuestaApi msg = new RespuestaApi();
        msg.setMessage("El cliente ha sido registrado.");

        return msg;
    }

    public RespuestaApi updateCustomer(Customer customer, int id) {
        jdbcTemplate.update("UPDATE customer SET "
                            + "nombre = '" + customer.getNombre() + "',"
                            + "apellidos = '" + customer.getApellidos() + "',"
                            + "rfc = '" + customer.getRfc() + "',"
                            + "correo = '" + customer.getCorreo() + "',"
                            + "id_region = '" + customer.getId_region() + "' "
                            + "WHERE id = " + id + ";");
        RespuestaApi msg = new RespuestaApi();
        msg.setMessage("El cliente ha sido actualizado.");

        return msg;
    }

    public RespuestaApi deleteCustomer(int id) {
        jdbcTemplate.update("DELETE FROM customer WHERE id = " + id + ";");
        RespuestaApi msg = new RespuestaApi();
        msg.setMessage("El cliente ha sido eliminado.");

        return msg;
    }
    
}
