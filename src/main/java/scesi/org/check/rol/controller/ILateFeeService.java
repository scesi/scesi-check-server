package scesi.org.check.rol.controller;

import scesi.org.check.latefee.model.projection.ILateFeeProjection;

import java.util.List;

public interface ILateFeeService {
    List<ILateFeeProjection> getAllLateFees();

    Boolean changeTypeLateFeeById(Long id);
}
