package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;

@Repository
public interface ConvenioDdjjRepository    extends JpaRepository< ConvenioDdjj, Integer> {

	public ConvenioDdjj findByConvenioIdAndDdjjId(Integer convenioId, Integer ddjjId);
	public List<ConvenioDdjj> findByConvenioId(Integer convenioId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "CALL convenio_ddjj_alta(?1, ?2);", nativeQuery = true)
	public void alta(@Param("p_convenio_id")  Integer convenioId, @Param("p_ddjj_id")  Integer p_ddjj_id);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "CALL convenio_ddjj_baja(?1);", nativeQuery = true)
	public void baja(@Param("p_convenio_ddjj_id")  Integer convenioDDJJId);
	
}
