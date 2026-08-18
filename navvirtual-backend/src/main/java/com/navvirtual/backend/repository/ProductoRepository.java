package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(Producto.Categoria categoria);
    List<Producto> findByStandId(Long standId);
    List<Producto> findByVendedorId(Long vendedorId);
    List<Producto> findByBuffetId(Long buffetId);
}