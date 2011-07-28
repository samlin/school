package com.lxitedu.student.dao.kauimpl;

//this is samlin add 2010-4-19
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import fi.devtrain.kauklahti.AutoConfigurator;
import fi.devtrain.kauklahti.Mapper;
import fi.devtrain.kauklahti.Table;
import fi.devtrain.kauklahti.WriterResult;

public abstract class KauSuperDAO<BT> {

  protected static AutoConfigurator configurator = new AutoConfigurator("com.lxitedu.bean.*");
  protected JdbcTemplate            jdbcTemplate = getJdbcTemplate();

  private JdbcTemplate getJdbcTemplate() {
    return new JdbcTemplate(getDataSource());
  }

  protected abstract DataSource getDataSource();

  public KauSuperDAO() {
    super();
  }

  public abstract Table getKauTable();

  public static Mapper getKauMapper() {
    final Mapper mapper = new Mapper();
    return mapper;
  }

  protected void kauAdd(BT obj) {
    List<WriterResult> statements = getKauMapper().writeInsert(obj, getKauTable());
    for (WriterResult writerResult : statements) {
      String sql = writerResult.getSql();
      System.out.println("KauSuperDAO.kauAdd()" + sql);
      jdbcTemplate.update(sql.toString(), writerResult.getParams());
    }
  }

  protected void kauDelete(BT obj) {
    List<WriterResult> statements = getKauMapper().writeDelete(obj, getKauTable());
    for (WriterResult writerResult : statements) {
      jdbcTemplate.update(writerResult.getSql().toString(), writerResult.getParams());
    }
  }

  protected void kauUpdate(BT obj) {
    List<WriterResult> statements = getKauMapper().writeUpdate(obj, getKauTable());
    for (WriterResult writerResult : statements) {
      jdbcTemplate.update(writerResult.getSql().toString(), writerResult.getParams());
    }
  }

  public List<BT> kauGetList() {
    String query = getKauMapper().writeSelect(getKauTable());
    ResultSetExtractor mapperExtractor = new ResultSetExtractor() {
      public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        return getKauMapper().extract(resultSet, getKauTable());
      }
    };
    List<BT> guitars = (List) jdbcTemplate.query(query, new Object[0], mapperExtractor);
    return guitars;

  }

  public List<BT> kauGetListByWhere(String whereSql) {
    String query = getKauMapper().writeSelect(getKauTable());
    query = query + " where " + whereSql;
    ResultSetExtractor mapperExtractor = new ResultSetExtractor() {
      public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        return getKauMapper().extract(resultSet, getKauTable());
      }
    };
    List<BT> guitars = (List) jdbcTemplate.query(query, new Object[0], mapperExtractor);
    return guitars;

  }

}