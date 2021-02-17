package com.morlimoore.storedprocedures.dao;

import com.morlimoore.storedprocedures.dao.util.UserDaoUtil;
import com.morlimoore.storedprocedures.models.Page;
import com.morlimoore.storedprocedures.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao extends AbstractDao<User> {
    private SimpleJdbcCall findUserByEmail;

    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        create = new SimpleJdbcCall(jdbcTemplate).withProcedureName("create_user").withReturnValue();
//        update = new SimpleJdbcCall(jdbcTemplate).withProcedureName("update_user").withReturnValue();
//        delete = new SimpleJdbcCall(jdbcTemplate).withProcedureName("delete_user").withReturnValue();
//        find = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_client")
//                .returningResultSet(SINGLE_RESULT, new ClientDaoUtil());
        findAll = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_all_users")
                .returningResultSet(RESULT_COUNT, new RowCountMapper())
                .returningResultSet(MULTIPLE_RESULT, new UserDaoUtil());
        findUserByEmail = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_user_by_email")
                .returningResultSet(SINGLE_RESULT, new UserDaoUtil());
    }

    @Override
    public User create(User user) {
        SqlParameterSource in = new UserDaoUtil().getClientPropertyMapSqlParameterSource(user);
        Map<String, Object> m = create.execute(in);
        long id = (long) m.get("id");
        user.setId(id);
        return user;
    }

    public List<User> findAll() throws DataAccessException {
        return findAll(0, 0).getContent();
    }

    public Page<User> findAll(Integer pageNum, Integer pageSize) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("page_num", pageNum)
                .addValue("page_size", pageSize == 0 ? null : pageSize);
        Map<String, Object> m = findAll.execute(in);
        List<User> content = (List<User>) m.get(MULTIPLE_RESULT);
        Long count = ((List<Long>) m.get(RESULT_COUNT)).get(0);
        return new Page<>(count, content);
    }

}

/*

@Repository
public class ClientDao extends AbstractDao<Client> {
    SimpleJdbcCall updateClientSecret, findByClientName,
            findByClientNameOrClientId, findByClientOwner, findAllByClientOwner;

    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        create = new SimpleJdbcCall(jdbcTemplate).withProcedureName("create_client").withReturnValue();
        update = new SimpleJdbcCall(jdbcTemplate).withProcedureName("update_client").withReturnValue();
        delete = new SimpleJdbcCall(jdbcTemplate).withProcedureName("delete_client").withReturnValue();
        find = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_client")
                .returningResultSet(SINGLE_RESULT, new ClientDaoUtil());
        findByClientName = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_client_by_name")
                .returningResultSet(SINGLE_RESULT, new ClientDaoUtil());
        findAll = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_clients")
                .returningResultSet(RESULT_COUNT, new RowCountMapper())
                .returningResultSet(MULTIPLE_RESULT, new ClientDaoUtil());
        updateClientSecret = new SimpleJdbcCall(jdbcTemplate).withProcedureName("update_client_secret")
                .withReturnValue();
        findByClientNameOrClientId = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_clients_by_client_id_or_client_name")
                .returningResultSet(RESULT_COUNT, new RowCountMapper())
                .returningResultSet(MULTIPLE_RESULT, new ClientDaoUtil());
        findByClientOwner = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_user_client")
                .returningResultSet(SINGLE_RESULT, new ClientDaoUtil());
        findAllByClientOwner = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_user_clients")
                .returningResultSet(RESULT_COUNT, new RowCountMapper())
                .returningResultSet(MULTIPLE_RESULT, new ClientDaoUtil());
    }

    @Override
    public Client create(Client client) throws DataAccessException {
        SqlParameterSource in = new ClientDaoUtil().getClientPropertyMapSqlParameterSource(client);
        Map<String, Object> m = create.execute(in);
        int id = (Integer) m.get("id");
        client.setId(id);
        return client;
    }

    @Override
    public void update(Client client) throws DataAccessException {
        SqlParameterSource in = new ClientDaoUtil().getClientPropertyMapSqlParameterSourceForUpdate(client);
        update.execute(in);
    }

    public void delete(String clientId) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource().addValue("client_id", clientId);
        delete.execute(in);
    }

    public Client find(String clientId) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("client_id", clientId);
        Map<String, Object> m = find.execute(in);
        List<Client> result = (List<Client>) m.get(SINGLE_RESULT);
        return result.isEmpty() ? null : result.get(0);
    }

    public Client findClientByClientName(String clientName) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("client_name", StringUtils.trim(clientName));
        Map<String, Object> m = findByClientName.execute(in);
        List<Client> result = (List<Client>) m.get(SINGLE_RESULT);
        return result.isEmpty() ? null : result.get(0);
    }

    public Page<Client> findClientsByClientNameOrClientId(QueryRequest queryRequest) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("page_num", queryRequest.getPageNum())
                .addValue("page_size", queryRequest.getPageSize() == 0 ? null : queryRequest.getPageSize())
                .addValue("search_value", StringUtils.trim(queryRequest.getSearchValue()))
                .addValue("username", queryRequest.getClientOwner());
        Map<String, Object> m = findByClientNameOrClientId.execute(in);
        List<Client> content = (List<Client>) m.get(MULTIPLE_RESULT);
        Long count = ((List<Long>) m.get(RESULT_COUNT)).get(0);
        return new Page<>(count, content);
    }

    public void updateClientSecret(String clientId, String clientSecret, String clientSecretEnc) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("client_id", clientId)
                .addValue("client_secret", clientSecret)
                .addValue("client_secret_enc", clientSecretEnc);
        updateClientSecret.execute(in);
    }

    public Client findClientByOwner(String clientId, String username) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("client_id", clientId)
                .addValue("username", username);
        Map<String, Object> m = findByClientOwner.execute(in);
        List<Client> result = (List<Client>) m.get(SINGLE_RESULT);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Client> findClientsByOwner(String username) throws DataAccessException {
        return findClientsByOwner(0, 0, username).getContent();
    }

    public Page<Client> findClientsByOwner(Integer pageNum, Integer pageSize, String username) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("page_num", pageNum)
                .addValue("page_size", pageSize == 0 ? null : pageSize)
                .addValue("username", username);
        Map<String, Object> m = findAllByClientOwner.execute(in);
        List<Client> content = (List<Client>) m.get(MULTIPLE_RESULT);
        Long count = ((List<Long>) m.get(RESULT_COUNT)).get(0);
        return new Page<>(count, content);
    }
}


 */