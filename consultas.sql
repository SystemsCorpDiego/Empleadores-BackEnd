----------------------
--CONSULTAS: Usuario EMPRESA y a la EMPRESA
select * FROM public.empresa_contacto where empresa_id = (select id from empresa 
														  where cuit = '30501126965' --'30604456475'
														 )
select * FROM public.empresa_domicilio where empresa_id = (select id from empresa 
														   where cuit = '30501126965' --'30604456475'
														 )
--Consulta Empresa
	select * FROM public.empresa  where cuit = '30501126965'--(empresaId=21) --'30604456475'

--Empresa Restringida
	select * from empresa_restringida


-----------------
		
--BORRAR Usuario EMPRESA y a la EMPRESA
delete FROM public.empresa_contacto
where empresa_id = (select id from empresa where cuit = '20233725006')
delete FROM public.empresa_domicilio
where empresa_id = (select id from empresa where cuit = '20233725006')
delete FROM public.empresa 
where cuit = '20233725006'
delete from usuario_clave
where id = (select id from usuario where nombre = '20233725006')
delete from usuario 
where nombre = '20233725006'


-------------------------------------------------

--Usuario Empresa
	select * from usuario 
	--where id = 31
	where descripcion = '1usuario' '30501126965' --id=30
-- Usuario Empresa
	select ue.empresa_id, e.cuit, e.razon_social, ue.usuario_id, u.descripcion,
			(select max(1) from usuario_rol z where z.usuario_id = u.id and z.rol_id = 4 ) rolEmpresa
	from   usuario_empresa ue, empresa e, usuario u
	where ue.empresa_id = e.id
	and   ue.usuario_id = u.id 

select * from empresa_domicilio where empresa_id = 21
select * from provincia

-- Roles del usuario 
	select * from usuario_rol
	where usuario_id=2 30
-- Usuario x Roles
	select ur.rol_id, r.descripcion, ur.usuario_id, u.descripcion, ur.* 
	from usuario_rol ur, rol r , usuario u
	where ur.rol_id = r.id
	and    u.id = ur.usuario_id
	and usuario_id = (select id from usuario where descripcion = '1usuario' --'30501126965' --'30604456475'
   )


select * from usuario_clave where id = (select id from usuario where descripcion = '30501126965' --'30604456475'
														 )

-----------------------------------------------------
 
-- Usuario Interno
select up.*, u.descripcion
from   usuario_persona up, usuario u 
where up.usuario_id = u.id  
 

-- Usuario Interno con Roles
select up.*, u.descripcion, ur.rol_id
from   usuario_persona up, usuario u, usuario_rol ur
where up.usuario_id = u.id 
and   ur.usuario_id = u.id

					  
					  
-----------------------
update usuario_clave
set clave = '$2a$10$oXp3Q4oXdjUlSWdvFwLqD.5VGvml5wuLKykGgwV6iCykB0f/wz33i' -- prueba123
-----------------------

  
------------------------------------------------------------------------------------------------------
--ROLES y PERMISOS					  
select * from rol
select * from funcionalidad

select r.id, r.descripcion, f.codigo, f.descripcion  
from   rol_funcionalidad rf, rol r,funcionalidad f
where  r.id = rf.rol_id
and    rf.funcionalidad_codigo = f.codigo
------------------------------------------------------------------------------------------------------
 

					  
select * from usuario_persona
where usuario_id = 25

					   
	select
        usuario1_.id as col_0_0_,
        usuario1_.descripcion as col_1_0_,
        usuario1_.habilitado as col_2_0_,
        usuario1_.ultimo_login as col_3_0_,
        usuario1_.previo_login as col_4_0_,
        usuarioper0_.nombre as col_5_0_,
        usuarioper0_.apellido as col_6_0_,
        usuarioper0_.email as col_7_0_ 
    from
        usuario_persona usuarioper0_ cross 
    join
        usuario usuario1_ 
    where
        usuarioper0_.usuario_id=usuario1_.id
					  
select * from rol

select * from usuario_rol
where usuario_id = 25
in (11, 7, 8, 3, 4, 5, 6)
delete  from  usuario_rol  where usuario_id in (19, 18, 20, 4, 5,6,7,8)
delete  from  usuario_clave  where id  in (19, 18, 20,4, 5,6,7,8)
delete from usuario_persona where usuario_id  in (19, 18, 20,4, 5,6,7,8)
delete from usuario where id  in (18, 19, 20,4, 5,6,7,8)

insert into usuario_rol
(usuario_id, rol_id, baja)
values
(1, 4, false) --empleador


insert into usuario_rol
(rol_id, usuario_id, baja)
values
(7, 2, false);


update usuario
set habilitado = true 
where id = 26

select * from usuario
					  

					  
					  
select * from usuario where descripcion = 'admin'

select * from usuario_clave where id = (select id from usuario 
										where nombre = '20233725006')
select * from usuario_rol where usuario_id = (select id from usuario  
											  where nombre = '20233725006')
					  
----------------------------------------------------------------------------------------------------------
--					  				DDJJ
----------------------------------------------------------------------------------------------------------

--Consultas JAVA de MIs DDJJ					  
	  select d.id, d.estado, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, a.aporte,  sum(a.importe) as importe 
			 from ddjj d 
				inner join empresa e on e.id = d.empresa_id  
				inner join ddjj_deta dd on d.id = dd.ddjj_id  
				left join ddjj_deta_aporte a on dd.id = a.ddjj_deta_id 
			   left join aporte ap on ap.codigo = a.aporte 
			 where d.empresa_id = 21
			 group by d.id, d.estado, d.empresa_id, e.cuit, e.razon_social, d.periodo, d.secuencia, ap.orden, a.aporte 
			 order by d.periodo, d.secuencia desc, ap.orden

					  
select count(*) FROM ddjj --24
where empresa_id = 21 --8
					  
select * FROM ddjj 
where empresa_id = 21 --8
order by id desc

select * , (select count(1) from ddjj_deta d2 where d2.ddjj_id = d.id) as canti_emple
FROM ddjj d
where empresa_id = 21 --8
order by id desc

select * FROM ddjj d
where d.empresa_id = 21 --8
and id = 89					  
and not exists (select 1 from ddjj_deta d2 where d2.ddjj_id = d.id)
and not exists (select 1 from ddjj_deta d2 
				join ddjj_deta_aporte a on a.ddjj_deta_id = d2.id)					  
					  
					  
select * FROM ddjj where periodo::date = '2024-01-01'

select * FROM ddjj where id = 82

select * FROM ddjj_deta where ddjj_id = 82
					  
update ddjj
set estado = 'PE'
 where id = 83					  

select * from ddjj_deta_aporte
					  where ddjj_deta_id in (select id from ddjj_deta where ddjj_id = 83)
					  
--Consultas de DDJJ
	select * FROM ddjj where periodo >= '2023-01-01'
	and  periodo <= '2023-03-01'

	select * FROM ddjj  --where id = 25
	order by id desc

delete from ddjj  where id = 28
--Consulta DDJJ por partes desde un id
	select * FROM ddjj  --where id = 77
				 where empresa_id = 21
			order by id desc
					  
	select * FROM ddjj_deta  where ddjj_id = 82
	select * FROM ddjj_deta_aporte  where ddjj_deta_id in (select id FROM ddjj_deta  where ddjj_id = 82)
	
-- ----------					  
--AFILIADO
	select * from afiliado
-- ----------
					
					  
-- ----------					  
--APORTES u orden de columna					  
	select * from aporte
	--
	update aporte 
	set orden = 4
	where codigo = 'UOMACU'
-- ----------					  

					  
					  
select * FROM ddjj_deta  where afiliado_cuil_titular = '20275889645'


--Consulta DDJJ de una empresa x periodo 
	select distinct periodo, count(1), max(id)
	FROM ddjj
	where empresa_id = 21
	group by 1


	select * --distinct periodo, count(1), max(id)
	FROM ddjj
	where empresa_id = 21
	and periodo = '2024-01-01'
	order by periodo
	
	

	select d.id, d.estado, d.empresa_id, d.periodo, d.secuencia, 
		   a.aporte,  sum(a.importe) as importe 
	from ddjj d 
		inner join ddjj_deta dd on d.id = dd.ddjj_id  
		left join ddjj_deta_aporte a on dd.id = a.ddjj_deta_id 
	where d.empresa_id = 32
	group by d.id, d.estado, d.empresa_id, d.periodo, d.secuencia, a.aporte 
	order by 1 desc
					  
					  

	
select presentada_fecha, TO_CHAR(presentada_fecha, 'DD/MM/YYYY'), 
		CASE WHEN estado ='PR' THEN current_date  ELSE presentada_fecha END
from ddjj

--Estado PENDIENTE					  
update ddjj d 
set estado = 'PE',  
    presentada_fecha = null, -- CASE WHEN ?2='PR' THEN sysdate ELSE presentada_fecha END
	secuencia = null
where d.id in ( 77)
	
					  
update ddjj
set estado = 'PR'
where id = 42


			select *--max(COALESCE(secuencia, 0) ) 
					  from ddjj d 
			where d.empresa_id  = (select empresa_id from ddjj t where id = 77) 
			and periodo = ?2 

------------------------------------------------------------------------------
--								 BOLETAS
------------------------------------------------------------------------------
select * from boleta_pago
select * from boleta_pago_ajuste

select id , empresa_id, periodo, aporte, vigencia, importe,
	   (select sum(importe) from boleta_pago_ajuste x where x.ajuste_id = a.id ) as usado
from ajuste a
where id = 14	

					  
-----------------------------
-- AJUSTES POR USAR					  
-----------------------------
select id , empresa_id, periodo, aporte, vigencia, importe,
	   0 as usado
from ajuste a
WHERE importe>0
and not exists (select 1 from boleta_pago_ajuste x where x.ajuste_id = a.id )					  
UNION 
select id , empresa_id, periodo, aporte, vigencia, importe,
	   (select sum(importe) from boleta_pago_ajuste x where x.ajuste_id = a.id ) as usado
from ajuste a
WHERE importe<0
AND   importe < (select sum(importe) from boleta_pago_ajuste x where x.ajuste_id = a.id ) 
			
					  
					  
					  
select * from boleta_pago_ajuste x 
where x.ajuste_id = a.id 		

update ajuste
set periodo = (periodo::date || ' 03:00:00')::timestamp,
    vigencia = (vigencia::date || ' 03:00:00')::timestamp					  

					  select * --(periodo::date || ' 03:00:00')::timestamp
					  from ajuste
---------------------------------------
--		BORRAR BOLETAS
---------------------------------------
delete from boleta_pago			
where ddjj_id is not null
			
delete from boleta_pago_ajuste

update ddjj
set estado = 'PR'
where estado = 'BG'

					  
------------------------------------------------------------------------------------------------------------					  
--LOCALIDAD
	select * 
    from
        localidad localidad0_ 
    left outer join
        provincia provincia1_ 
            on localidad0_.id_provincia=provincia1_.id_provincia 

	select * from provincia
	select * from localidad
					  

--RAMO
select * from ramo					  

	
------------------------------------------------------------------------------------------------------------
					  Seteo de Aportes Vigentes para un periodo
------------------------------------------------------------------------------------------------------------

					  
select  a.codigo, a.descripcion,  s.desde,
	    a.entidad as entidad_aporte, s.entidad as entidad_seteo, 
	    s.socio, s.calculo_tipo, s.calculo_valor, s.calculo_base, 
	    s.camara, s.camara_categoria, s.camara_antiguedad
from   aporte_seteo s, aporte a,
	   (
		select  s.aporte, max(s.desde) desde
		from   aporte_seteo s
		where s.desde <= '2023-11-01'
		and   (s.hasta IS NULL OR s.hasta >='2023-11-01')
		group by 	s.aporte ) x
where a.codigo = s.aporte
and   s.aporte = x.aporte 			
and   s.desde = x.desde	

 select id, aporte, desde, hasta, entidad, socio, calculo_tipo as calculoTipo, calculo_valor as calculoValor,
	    calculo_base as calculoBase, camara, camara_categoria as camaraCategoria, camara_antiguedad as camaraAntiguedad
 FROM aporte_seteo
			
select * from tablaescalasalarialjornales					  
select  s.*
from   aporte_seteo s, 
	   (
		select  s.aporte, max(s.desde) desde
		from   aporte_seteo s
		where s.desde <= '2024-01-01'
		and   (s.hasta IS NULL OR s.hasta >='2024-01-01')
		group by 	s.aporte ) x
where s.aporte = x.aporte 			
and   s.desde = x.desde	
					  
------------------------------------------------------------------------------------------------------------
select * from boleta_pago

delete from boleta_pago where ddjj_id = 83

select * from ddjj where id = 77					  
------------------------------------------------------------------------------------------------------------
					  
insert into ajuste
(empresa_id, periodo, aporte, importe, vigencia)
values
(21, '2023-08-01 03:00:00'  )
					  
	
					  select * from aporte
					  
					  select * from ajuste 
					  
					  select * from aporte

	update ajuste 
	set boleta_pago_id = 1
	where id = 2

	select * from boleta_pago


	select *
	from afip_interes

					  update afip_interes
					  set indice = indice/100
					  
------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------
select * from aporte


 

select * from publicacion
order by 1 desc

delete from feriado
select * from feriado
order by 1



  

select * from publicacion
select * from feriado
select * from rol
  

  
select * from feriado

select * from aporte

select * from aporte_seteo
					  

  -- busco Escala Salarial vigente x Periodo.-
 select * from tablaescalasalarialjornales a
 where a.camara = 'FAIM' and a.antiguedaddesde = 0
 and a.fechadesde = (select max(fechadesde)
				   from   tablaescalasalarialjornales x
				   where  x.camara = a.camara and x.antiguedaddesde = a.antiguedaddesde
				   and    x.fechadesde < '2024-01-01'
				  )
				  
 select * from aporte_seteo a
 where a.aporte = 'ANTIMACS'
 and   a.desde  = (select max(desde)
				   from   aporte_seteo x
				   where  a.aporte = x.aporte
				   and    x.desde < '2024-01-01'
				  )

select * from v_empresa_usuario

select count (*) from tablaescalasalarialjornales
select * from tablaescalasalarialjornales


select *
from  tablaescalasalarialjornales a
where fechadesde = (select max(fechadesde) from tablaescalasalarialjornales x where x.camara = a.camara)
and COALESCE(catf,0) > 0

SELECT j.camara, 'A'::text AS categoria, j.fechadesde
FROM tablaescalasalarialjornales j
where COALESCE(cata,0) > 0
group by j.camara, j.fechadesde
order by j.fechadesde		   
UNION

select *
from (
select id, camara, 'A' categoria, antiguedaddesde, fechadesde, cata importe
from tablaescalasalarialjornales j
where j.cata is not null
union
select id, camara, 'B', antiguedaddesde, fechadesde, catb
from tablaescalasalarialjornales j
where j.catb is not null
union
select id, camara, 'C', antiguedaddesde, fechadesde, catc
from tablaescalasalarialjornales j
where j.catc is not null
union
select id, camara, 'D', antiguedaddesde, fechadesde, catd
from tablaescalasalarialjornales j
where j.catd is not null
union
select id, camara, 'E', antiguedaddesde, fechadesde, cate
from tablaescalasalarialjornales j
where j.cate is not null
union
select id, camara, 'F', antiguedaddesde, fechadesde, catf
from tablaescalasalarialjornales j
where catf is not null
) aux --order by 2, 3, 4
where aux.categoria = 'A'

select *
from tablaescalasalarialjornales j
where j.catf = 0

select *
from tablaescalasalarialjornales j
where camara = 'CAENA'
and fechadesde = (select max(fechadesde) from tablaescalasalarialjornales x
		where x.camara = j.camara )
order by fechadesde

select *
from v_escala_salarial 
where vigencia > (current_date -3000)

 

current_date -1000


select * from v_camara
select * from v_camara_categoria

select * from v_escala_salarial e
where  camara = 'FAIM'
and    vigencia = (select max(vigencia) from v_escala_salarial x where e.camara = x.camara )
order by categoria , antiguedad, vigencia


select * from funcionalidad


  
select * from ddjj order by periodo asc, id

					  
-----------------------------------------------------
-----------------------------------------------------					  
	do
	$$
	DECLARE
		--tablaResultado public.ddjj%ROWTYPE;
		tablaResultado record;
		user_id integer;
	BEGIN
		user_id = 0;
		for tablaResultado in SELECT * FROM ddjj WHERE id <>1 loop
			update ddjj
			set secuencia = user_id
			where id = tablaResultado.id;
			user_id = user_id+1;
		end loop;
	END;
	$$
-----------------------------------------------------
-----------------------------------------------------

					  select * from afiliado where cuil = 27446744955
select * from afiliado where cuil_titular in ( 23279737229, 20294465996 )
select * from ddjj where id = 37
select * from ddjj_deta 
where ddjj_id = 37
order by 1

					  
--where ddjj_id = 37
where uoma_socio is not null

select * from ddjj_deta_aporte a 
where  a.ddjj_deta_id in(
	select x.id from ddjj_deta x where ddjj_id = 37 ) 


===============================================================
ddjj-totales: custom query 
--------------------
select d.id, d.estado, d.empresa_id, d.periodo, d.secuencia, 
	   a.aporte,  sum(a.importe) as importe
from ddjj d
	inner join ddjj_deta dd on d.id = dd.ddjj_id 
	inner join ddjj_deta_aporte a on dd.id = a.ddjj_deta_id 
where d.empresa_id = 21
group by d.id, d.estado, d.empresa_id, d.periodo, d.secuencia, a.aporte
order by 1
===============================================================
select * from empresa_domicilio where empresa_id = 21
select * from ddjj where id = 37

select * from ddjj_deta where ddjj_id = 37

update ddjj_deta 
set empresa_domicilio_id = 41 
where ddjj_id = 37

"27367071112"
"27418918786"
 

  
=================================
REPORTE:
select TO_CHAR(d.periodo, 'mm/yyyy') periodo, d.secuencia, e.cuit, e.razon_social,	   
       dd.afiliado_cuil_titular cuil, a.apellido ||' '|| a.nombre apellidoNombre,
	   dd.camara, dd.categoria, TO_CHAR(dd.ingreso, 'dd/mm/yyyy') ingreso, ed.planta, dd.remunerativo, dd.no_remunerativo,
	   dda.aporte, dda.importe
from ddjj d
join empresa e on d.empresa_id = e.id
join ddjj_deta dd on dd.ddjj_id = d.id
join empresa_domicilio ed on dd.empresa_domicilio_id = ed.id
join afiliado a on dd.afiliado_cuil_titular = a.cuil_titular and dd.afiliado_inte = a.inte
join ddjj_deta_aporte dda on dda.ddjj_deta_id = dd.id
where d.id = 1
order by cuil, dda.aporte

  
			
		 
select * FROM aporte_seteo
select * FROM aporte_seteo

select *  FROM aporte a
where exists ( select * FROM aporte_seteo s
			   where  s.aporte = a.codigo
			   and    s.desde < NOW()
			   and    COALESCE(s.hasta, NOW()) >= NOW()
			  )

select *  FROM aporte_seteo a
where  a.desde < NOW()
and    COALESCE(a.hasta, NOW()) >=  NOW()
and desde = (select max(desde) FROM aporte_seteo t
			where  desde < NOW()
			and    COALESCE(hasta, NOW()) >=  NOW()
			and t.aporte = a.aporte
			)  

select empresa_id, periodo, max(COALESCE(secuencia, 0) ) 
from ddjj d 
group by empresa_id, periodo
order by empresa_id


select max(COALESCE(secuencia, 0) ) from ddjj d 
where d.empresa_id  = (select empresa_id from ddjj t where id = 67)



select * from ddjj

select * from ddjj_deta

select * from ddjj_deta_aporte

select * from publicacion


select * from aporte
select * from banco_sucursal
select * from banco_convenio
select * from banco_convenio_mov_tipo 

select * 
from   banco_convenio_mov_tipo t
inner join banco_convenio c on c.id = t.convenio_id

banco_convenio_mov_tipo

select * from boleta_pago

select COALESCE(max(secuencia),0)+1 from boleta_pago where empresa_id = 1
banco_convenio


select * from banco_sucursal
select * from banco_convenio
select
        bancoconve0_.id as id1_4_0_,
        bancoconve0_.codigo as codigo2_4_0_,
        bancoconve0_.cuenta as cuenta3_4_0_,
        bancoconve0_.sucursal_id as sucursal4_4_0_,
        bancosucur1_.codigo as codigo1_6_1_,
        bancosucur1_.banco_id as banco_id3_6_1_,
        bancosucur1_.descripcion as descripc2_6_1_,
        banco2_.id as id1_3_2_,
        banco2_.descripcion as descripc2_3_2_ 
    from
        banco_convenio bancoconve0_ 
    left outer join
        banco_sucursal bancosucur1_ 
            on bancoconve0_.sucursal_id=bancosucur1_.codigo 
    left outer join
        banco banco2_ 
            on bancosucur1_.banco_id=banco2_.id 
    where
        bancoconve0_.id=1
		
select *
from ddjj
where id in (15, 16 , 17 , 18, 19, 21, 23,24,25,26,27,28,29 , 30, 32, 31, 33, 34, 35, 36 ) 

select *
from ddjj_deta d
where ddjj_id = 77

select *
from ddjj_deta_aporte d
where ddjj_deta_id in(select id from ddjj_deta d where ddjj_id = 77 ) 


select *
from ddjj d
	inner join empresa e on e.id = d.empresa_id
--where empresa_id=21
where e.cuit =  '30501126965'
--order by periodo desc
and d.periodo --BETWEEN CAST ('2023-01-01 00:00:00' AS TIMESTAMP) AND CAST ('2023-04-01 00:00:00' AS TIMESTAMP) 
            BETWEEN CAST ('2023-01-01 00:00:00' AS TIMESTAMP) AND CAST ('2023-05-01 00:00:00' AS TIMESTAMP)

SELECT EXTRACT(CENTURY FROM TIMESTAMP '2000-12-16 12:21:13');
SELECT timestamptz '2013-07-01 12:00:00'

 
select * from aporte
select * from boleta_pago
select * from ajuste
select * from empresa

select b.ddjj_id as ddjjId, d.periodo, d.secuencia, b.id, b.aporte, a.descripcion, b.importe, 
	   b.intencion_pago, b.forma_pago,
	   null as fechaPago, null as importeRecibido
from boleta_pago b
	inner join ddjj d on d.id = b.ddjj_id
	inner join aporte a on a.codigo = b.aporte


					  
================================================================================
					  QUERYs Jasper Boleta
================================================================================
	-- REPORTE 				  
	select to_char(current_date, 'DD/MM/YYYY') as impresion,
			 to_char(b.fechavencimiento, 'DD/MM/YYYY') as vencimiento ,
			to_char( b.fechapago, 'DD/MM/YYYY')  as pago,  e.cuit, e.razon_soc,
			 to_char(decl.periodo, 'MM') as periodo_mes,
			 to_char(decl.periodo, 'YYYY') as periodo_anio,
			decl.numerosecuencia, totales.total + b.interes  as subtotal,
			decl.numerosecuencia, totales.total + b.interes +coalesce( (select sum(capital+interes) from ajuste a, ajuste_boleta ab where a.id = ab.ajustes_id and ab.boletasdeaplicacion_id = b.id) ,0) as total, totales.cantidad,
			b.numerosecuencia as nro_boleta,
			(select count(*) from ajuste_boleta where boletasdeaplicacion_id = cast($P{boleta_id} as integer)) as cant_ajustes, //se usa para saber si se imprime el SUBREPORTE
			b.nrobep
	from boleta b
		inner join (
			select  boleta_id , sum(capital) as total, count(*) as cantidad
			from detalleboleta
			where boleta_id = cast($P{boleta_id} as integer)
			group by boleta_id
		) totales 
		on b.id = totales.boleta_id
		
	    inner join declaracionjurada decl on b.declaracionjurada_id = decl.id
	    inner join empresa e on decl.empresa_cuit = e.cuit and decl.empresa_sucursal = e.sucursal
 

					  
	-- SUBREPORTE 				  
	(select capital+interes as importe, descripcion,  to_char(periodo_origen, 'MM') as periodo_mes,  
			to_char(periodo_origen, 'YYYY') as periodo_anio ,
			coalesce(
				(select sum(capital+interes) from ajuste a2, ajuste_boleta ab2 where a2.id = ab2.ajustes_id and ab2.boletasdeaplicacion_id =cast($P{boleta_id} as integer))
			,0) as total
	 from ajuste a, ajuste_boleta ab
	 where a.id = ab.ajustes_id
	 and ab.boletasdeaplicacion_id = cast($P{boleta_id} as integer)
	 order by id
	 limit 3)
	union all
	 select importe, descripcion, periodo_mes, periodo_anio, total from (select sum(capital+interes) as importe, cast('AJUSTES' as character varying) as descripcion, to_char(max(periodo_origen), 'MM') as periodo_mes,  to_char(max(periodo_origen), 'YYYY') as periodo_anio ,
		coalesce(
			(select sum(capital+interes) from ajuste a2, ajuste_boleta ab2 where a2.id = ab2.ajustes_id and ab2.boletasdeaplicacion_id =cast($P{boleta_id} as integer))
			,0) as total
	 from ajuste a, ajuste_boleta ab
	 where  a.id = ab.ajustes_id
	 and a.id not in (select a2.id   from ajuste a2, ajuste_boleta ab2
					  where a2.id = ab2.ajustes_id
					  and ab2.boletasdeaplicacion_id = cast($P{boleta_id} as integer)
					  order by a2.id
					  limit 3)
	 and ab.boletasdeaplicacion_id = cast($P{boleta_id} as integer)) aux 
	 where importe is not null
	union all
	 select 0 as importe, '-', '-','-',0 
	 where not exists (select 1 from ajuste_boleta where boletasdeaplicacion_id = cast($P{boleta_id} as integer))

================================================================================
					  --	VERSION NUEVA 
================================================================================	
	-- REPORTE 				  
    select * from  funcionalidad
    select * from  rol
					  
	select * from  ajuste 				  
	select * from  boleta_pago b				  
	select * from  empresa
	select * from ddjj
	select * from ddjj_deta
					  select * from ddjj_deta_aporte
					  
	select case when d.secuencia = 0 then 'Original' 
			    when d.secuencia is null then 'Pendiente' 
		   else 'Rectif. Nro ' || d.secuencia  end as tipo
    from ddjj d
	
    select b.id, b.secuencia, b.aporte,
		   to_char(d.periodo, 'YYYY') as periodo_anio,
		   to_char(d.periodo, 'MM') as periodo_mes,
		   b.importe,
		   'DDJJ' as concepto,
		   (select count(1) from ddjj_deta d where d.ddjj_id = b.ddjj_id
		   and exists (select 1 from ddjj_deta_aporte a where a.ddjj_deta_id = d.id and a.aporte = b.aporte)
			) as cantidad
	from  boleta_pago b
		  inner join ddjj d on b.ddjj_id = d.id
	union
	select b.id, b.secuencia, b.aporte, 
		   to_char(a.periodo, 'YYYY') as periodo_anio,
		   to_char(a.periodo, 'MM') as periodo_mes,
		   a.importe,
		   'Ajuste periodo' as concepto,
		   1 as cantidad
	from  ajuste a
		  inner join boleta_pago b on a.boleta_pago_id = b.id		  
	union
    select b.id, b.secuencia, b.aporte,
		   '--' as periodo_anio,
		   '--' as periodo_mes,
		   b.importe,
		   'Acta Nro ' || b.acta_nro || ' - ' || b.descripcion as concepto,
		   1 as cantidad
	from  boleta_pago b
	where b.ddjj_id IS NULL
					  
					  
	
					  select *
	from  boleta_pago b
	where b.ddjj_id IS NULL					  
					  
	select to_char(current_date, 'DD/MM/YYYY') as impresion,
			 to_char(b.fechavencimiento, 'DD/MM/YYYY') as vencimiento ,
			to_char( b.fechapago, 'DD/MM/YYYY')  as pago,  e.cuit, e.razon_soc,
			 to_char(decl.periodo, 'MM') as periodo_mes,
			 to_char(decl.periodo, 'YYYY') as periodo_anio,
			decl.numerosecuencia, totales.total + b.interes  as subtotal,
			decl.numerosecuencia, totales.total + b.interes +coalesce( (select sum(capital+interes) from ajuste a, ajuste_boleta ab where a.id = ab.ajustes_id and ab.boletasdeaplicacion_id = b.id) ,0) as total, totales.cantidad,
			b.numerosecuencia as nro_boleta,
			(select count(*) from ajuste_boleta where boletasdeaplicacion_id = cast($P{boleta_id} as integer)) as cant_ajustes, //se usa para saber si se imprime el SUBREPORTE
			b.nrobep
	from boleta b
		inner join (
			select  boleta_id , sum(capital) as total, count(*) as cantidad
			from detalleboleta
			where boleta_id = cast($P{boleta_id} as integer)
			group by boleta_id
		) totales 
		on b.id = totales.boleta_id
		
	    inner join declaracionjurada decl on b.declaracionjurada_id = decl.id
	    inner join empresa e on decl.empresa_cuit = e.cuit and decl.empresa_sucursal = e.sucursal
 

================================================================================
================================================================================
select * from ajuste					  
					  
select * from boleta_pago where aporte = 'UOMACS'
select * from boleta_pago_ajuste 
					  
insert into boleta_pago_ajuste 
( boleta_pago_id, ajuste_id, importe )
values
( 32, 1, 15000 )

					  
					  
select * from ddjj
select * from ddjj_deta where ddjj_id = 82

select * from afiliado
					  

					  
					  
					  
select d.id, d.estado, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, 
		a.aporte,  sum(a.importe) as importe 
from ddjj d 
	inner join empresa e on e.id = d.empresa_id  
	inner join ddjj_deta dd on d.id = dd.ddjj_id  
	left join ddjj_deta_aporte a on dd.id = a.ddjj_deta_id 
	left join aporte ap on ap.codigo = a.aporte 
where d.empresa_id = 21
group by d.id, d.estado, d.empresa_id, e.cuit, e.razon_social, d.periodo, d.secuencia, a.aporte , ap.orden
order by d.periodo, d.secuencia desc, ap.orden
					  
select * from aporte	where ddjj = true		
					  select distinct codigo from aporte	where ddjj = true				
"UOMACU"
"AMTIMACS"
"UOMACS"
"UOMAAS"
"ART46"

select * from ddjj_deta
					  
select * from aporte_seteo s where aporte = 'ART46' order by desde
					  
update aporte_seteo 
set    hasta = '1989-12-31'
where aporte = 'ART46'
and id = 2

select  s.id, a.codigo, a.descripcion,  s.desde,
	    a.entidad as entidad_aporte, s.entidad as entidad_seteo, 
	    s.socio, s.calculo_tipo, s.calculo_valor, s.calculo_base, 
	    s.camara, s.camara_categoria, s.camara_antiguedad
from   aporte_seteo s, aporte a,
	   (
		select  s.aporte, max(s.desde) desde
		from   aporte_seteo s
		where s.desde <= '2024-01-01'
		and   (s.hasta IS NULL OR s.hasta >='2024-01-01')
		group by s.aporte ) x
where a.codigo = s.aporte
and   s.aporte = x.aporte 			
and   s.desde = x.desde					  
 
update aporte_seteo 
set calculo_valor = 200
where id = 4
					  
select * from empresa 
order by id desc
					  35 - "30647270685" - "MOLINERO SUAREZ Y CIA S A"
select * from usuario 
where descripcion = '30647270685' --34  
order by id desc

select * from usuario_rol
where usuario_id = 34  
select * from rol

					  
					  select * from funcionalidad
					  select * from rol_funcionalidad order by rol
					  select * from rol
					  
					  select * from v_escala_salarial
					  
select * 
FROM afip_interes i 
--where '2024-05-10' BETWEEN i.desde and COALESCE(i.hasta, '2024-05-10') 
order by 2

					  insert into afip_interes
					  values
					  ()
do
$$
DECLARE
	tablaResultado record;
	user_id integer;
	fechaPago timestamp := '2023-05-19';
 
BEGIN
	
	for tablaResultado in 
	    SELECT i.* FROM afip_interes i
		where fechaPago BETWEEN i.desde and COALESCE(i.hasta, fechaPago) 	
	loop
					  
		RAISE NOTICE 'id registro: %', tablaResultado.id;
					  
	end loop;
END;
$$

select * from ajuste		
					  select * from aporte_seteo
					  
select * from rol					  
					  
					  
 select * FROM ajuste a 
 where a.empresa_id = 21
 and     a.aporte          = 'UOMAAS' 
 and     a.vigencia     <= '2024-01-01'
 and     (a.importe > 0 OR
		  a.importe <  (select COALESCE(sum(bpa.importe),0) from boleta_pago_ajuste bpa 
								 where bpa.ajuste_id = a.id ) 
		  )
 order by a.importe desc 					  
					  
					  
delete from  boleta_pago_ajuste bpa 
select COALESCE(sum(bpa.importe),0) from boleta_pago_ajuste bpa 
								 where bpa.ajuste_id = 1					  

					  
select * from afiliado 
					  
select * from ddjj where id = 86
   update ddjj set estado = 'PE' where id = 86
					  
select * from boleta_pago
where ddjj_id = 85 --86
order by 1					  
   delete from boleta_pago				 where ddjj_id = 86
select * from boleta_pago_ajuste					  
   delete from boleta_pago_ajuste
					  
					
 BoletaPagoAjuste
select * from ajuste					  

					  
select * from aporte
select * from banco_sucursal
select * from banco_convenio

					  
select CASE WHEN  COALESCE(max(id), 0)=0 THEN false ELSE true END
from boleta_pago_ajuste					  
where ajuste_id = 99


select * from ddjj where id = 87 --85
select * from ddjj_deta where ddjj_id = 87 --85

					  
select *
from			ddjj_deta_aporte		  
select *
from afiliado
					  
select d.afiliado_cuil_titular cuil, a.apellido, a.nombre, d.remunerativo, ap.importe capital 
from   ddjj_deta d
		join afiliado a on a.cuil_titular = d.afiliado_cuil_titular and a.inte=0
		join ddjj_deta_aporte ap on ap.ddjj_deta_id = d.id		
where  d.ddjj_id = 86
and    ap.aporte = 'ART46'					  

					  

select * from usuario 
where descripcion = '7usuario'
					  
select * from usuario 
where id in ( 16, --"30501126965" 
		      23,  -- "2usuario"
			  35,  -- 7usuario
			  36    --'30708161817'
			)
					  
		
					  select * from usuario_clave
					  where id in (16, 23, 35, 36)
update usuario_clave
set clave = '$2a$10$oXp3Q4oXdjUlSWdvFwLqD.5VGvml5wuLKykGgwV6iCykB0f/wz33i' -- prueba123

select id from ddjj 
where empresa_id = 21
and estado in ('BG', 'PR')	
order by periodo desc, secuencia desc
LIMIT 2					  
select * from aporte_seteo					  

calculo_tipo => PO-porcentaje EN-Entero
calculo_valor => aca esta el indice porcentual o el valor entero que se cobra
calculo_base => RE-Remunerativo, PJ-Paritaria por Jornal, PS-Paritaria Salarial
camara, camata_categoria y camara_antiguedad => definen el registro dentro de la tabla "tablaescalasalarialjornal" o de "tablaescalasalarialSueldo"
					  
					  
select e.cuit, e.razon_social, 
	   b.secuencia as boleta_nro, 
	   ap.entidad || ' - ' || ap.descripcion  as tipo,
	   b.intencion_pago, b.interes,
	   (select sum(importe) from boleta_pago_ajuste bpa 
	    where bpa.boleta_pago_id = b.id) as ajuste,
	   d.secuencia as ddjj_nro, d.periodo, d.presentada_fecha,
	   a.cuil, a.apellido, a.nombre, 
	   dd.remunerativo, da.importe as Capita
from  boleta_pago b
	  join ddjj_deta dd on b.ddjj_id = dd.ddjj_id
	  join ddjj_deta_aporte da on da.ddjj_deta_id = dd.id and da.aporte = b.aporte
	  join ddjj d on d.id = b.ddjj_id
	  join afiliado a on a.cuil_titular = dd.afiliado_cuil_titular
	  join empresa e on e.id = b.empresa_id
	  join aporte ap on ap.codigo = b.aporte
where b.id = 104
					  
					  
select * from ajuste order by aporte
select * from boleta_pago_ajuste order by 2 asc
delete  from boleta_pago_ajuste 
delete from boleta_pago				
					  
update ddjj
set estado = 'PR'
where estado = 'BG'

select * from ddjj
select * from ddjj_deta					  
select * from ddjj_deta_aporte
select * from aporte
select * from empresa

-- COnsulta de Boletas con sus Ajustes y los importe reales del ajuste
select b.id, b.secuencia boleta_nro, b.aporte, b.forma_pago, b.importe, b.interes  ,
					  ba.id, ba.ajuste_id, ba.importe as importe_ajuste_boleta,
					  a.importe as importe_ajuste
					  from boleta_pago b			  
 inner join  boleta_pago_ajuste ba on ba.boleta_pago_id = b.id
 inner join ajuste a on a.id = ba.ajuste_id					  

select *  from boleta_pago where id = 119
					  
select * from boleta_pago_ajuste
where boleta_pago_id = 119

					  
select * from   afiliado a
where exists ( select * from ddjj_deta d
			  join boleta_pago b on d.ddjj_id = b.ddjj_id
			  where d.afiliado_cuil_titular = a.cuil_titular
			 )					  

select * from ddjj
where periodo = '2024-02-01 03:00:00'		

select * from ddjj_deta						  
where ddjj_id = 82

update  ddjj_deta	
set remunerativo = remunerativo+50000000
where ddjj_id = 82
					  
select * from ddjj_deta_aporte
where ddjj_deta_id in ( 216, 217, 218 ) 
					  

select * -- intencion_pago, TO_CHAR(intencion_pago, 'DD/MM/YYYY') 					  
from boleta_pago	
					  

select * from ajuste
where vigencia <= '2024-03-01'
and empresa_id = 21					  
					  
select * from ddjj where id=86					  
					  
select * from ddjj_deta where ddjj_id=86	
					  
----------------------------------------------------------------------------------
					  ------------------------------------------------------------------------------
					  ------------------------------------------------------------------------------------------
					  
					  
					  
					  
select * from boleta_pago 
select * from boleta_pago_ajuste


select secuencia FROM ddjj d
where d.empresa_id = 21
and   d.periodo = (select d2.periodo from ddjj d2 where id = 86)
and   id > 86
order by 1 desc

and exists (select 1 from ddjj d2
			where id > 86
and   d.periodo    = '2024-01-01 03:00:00'
and   id > 86
			

and exists (select 1 from ddjj d2
		    where 
		   
		   )

select * FROM ddjj
where empresa_id = 21
and id= 87


select
        d.id,
        d.estado,
        d.empresa_id,
        e.cuit,
        e.razon_social,
        d.periodo,
        d.secuencia,
        a.aporte,
        sum(a.importe) as importe  
    from
        ddjj d  
    inner join
        empresa e 
            on e.id = d.empresa_id   
    inner join
        ddjj_deta dd 
            on d.id = dd.ddjj_id   
    left join
        ddjj_deta_aporte a 
            on dd.id = a.ddjj_deta_id    
    left join
        aporte ap 
            on ap.codigo = a.aporte  
    where
        d.empresa_id = 21
		AND D.ID = 87
        and d.periodo BETWEEN CAST(? AS TIMESTAMP)  and CAST(? AS TIMESTAMP)   
    group by
        d.id,
        d.estado,
        d.empresa_id,
        e.cuit,
        e.razon_social,
        d.periodo,
        d.secuencia,
        ap.orden,
        a.aporte  
    order by
        d.periodo desc,
        d.secuencia desc,
        ap.orden asc 
		
		
 select d.id, d.estado, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, 
	   a.aporte,  sum(a.importe) as importe ,
	   (select max(id) from ddjj d2
		where d2.empresa_id = d.empresa_id
		and   d2.periodo = d.periodo 
		and   d2.id >= d.id) as ddjj_vigente
 from ddjj d 
	inner join empresa e on e.id = d.empresa_id  
	inner join ddjj_deta dd on d.id = dd.ddjj_id 
	left join ddjj_deta_aporte a on dd.id = a.ddjj_deta_id 
   left join aporte ap on ap.codigo = a.aporte 
 where d.empresa_id = 21
 --and d.periodo BETWEEN CAST(?2 AS TIMESTAMP)  and CAST(?3 AS TIMESTAMP)  
 group by d.id, d.estado, d.empresa_id, e.cuit, e.razon_social, d.periodo, d.secuencia, ap.orden, a.aporte 
 order by d.periodo desc, d.secuencia desc, ap.orden asc

			
			select secuencia FROM ddjj d
where d.empresa_id = 21
and   d.periodo = (select d2.periodo from ddjj d2 where id = 86)
and   id > 86
order by 1 desc
			
alter table boleta_pago
add vencimiento 
			
			select * from boleta_pago
			
			select * from empresa
			
			select * from usuario_persona
			
select * from ajuste
select * from boleta_pago_ajuste

			
			
			
	select * FROM ajuste a 
	where a.empresa_id = 21
	and     a.aporte          = 'ART46' 
	and     a.vigencia::date     <= '2024-02-01' 
	and     ( (    a.importe > 0 
			   AND NOT EXISTS(SELECT 1 from boleta_pago_ajuste bpa 
							  where bpa.ajuste_id = a.id ) 
			  )
			 OR a.importe < (select COALESCE(sum(bpa.importe),0) 
							 from boleta_pago_ajuste bpa 
							 where bpa.ajuste_id = a.id ) )
	order by a.importe desc			
			
			
			select * from boleta_pago_ajuste
			
			
select b.id, b.secuencia, b.aporte, 
		   to_char(d.periodo, 'YYYY') as periodo_anio,
		   to_char(d.periodo, 'MM') as periodo_mes,
		   b.interes as importe,
		   'Interes por mora' as concepto,
		   1 as cantidad
	from  boleta_pago b
		  inner join ddjj d on b.ddjj_id = d.id
		  inner join aporte a on a.codigo = b.aporte
	where COALESCE(b.interes,0)>0			
	and b.id =  cast($P{boletaId} as integer) 
			
	update boleta_pago
	set interes =  3063.06
	where id = 145			
			
			
			
			
select * from (
 select b.id, b.secuencia, b.aporte, 
		   to_char(d.periodo, 'YYYY') as periodo_anio,
		   to_char(d.periodo, 'MM') as periodo_mes,
		   b.importe,
		   'DDJJ - '  || a.entidad || ' - ' || a.descripcion as concepto,
		   (select count(1) from ddjj_deta d where d.ddjj_id = b.ddjj_id
		   and exists (select 1 from ddjj_deta_aporte a where a.ddjj_deta_id = d.id and a.aporte = b.aporte)
			) as cantidad, 1 as orden
	from  boleta_pago b
		  inner join ddjj d on b.ddjj_id = d.id
		  inner join aporte a on a.codigo = b.aporte
	where b.id =  145
	union
    select b.id, b.secuencia, b.aporte, 
		   to_char(d.periodo, 'YYYY') as periodo_anio,
		   to_char(d.periodo, 'MM') as periodo_mes,
		   b.interes as importe,
		   'Intereses por mora ' as concepto,
		   2 as cantidad, 2 as orden
	from  boleta_pago b
		  inner join ddjj d on b.ddjj_id = d.id
		  inner join aporte a on a.codigo = b.aporte
	where b.id =  145
	and COALESCE(b.interes,0)>0	
	union
	select b.id, b.secuencia, b.aporte, 
		   to_char(a.periodo, 'YYYY') as periodo_anio,
		   to_char(a.periodo, 'MM') as periodo_mes,
		   bpa.importe,
		   'Ajuste periodo' as concepto,
		   1 as cantidad, 3 as orden
	from  ajuste a
		  inner join boleta_pago_ajuste bpa on a.id = ajuste_id 
		  inner join boleta_pago b on bpa.boleta_pago_id = b.id
    where b.id = 145
) a
order by orden asc, periodo_anio, periodo_mes		

			
			select * from ddjj_deta
			select * from ddjj_deta_aporte
			select * from boleta_pago
			select * from boleta_pago_ajuste


			
			
			select id from ddjj  
			where empresa_id = 21 
			and estado in ('BG', 'PR') 
			and to_date(to_char(periodo, 'YYYY/MM/DD'), 'YYYY/MM/DD') = '2024-05-01'
			order by periodo desc, secuencia desc 
			LIMIT 1

select * from afip_interes order by 2
update  afip_interes
			set hasta = '2024-03-01 03:00:00'
where id = 15
			
select * from aporte_seteo

			
--aportes vigentes
			
 select s.id, s.aporte, s.desde, s.hasta, s.entidad, s.socio, s.calculo_tipo as calculoTipo, s.calculo_valor as calculoValor, 
				    s.calculo_base as calculoBase, s.camara, s.camara_categoria as camaraCategoria, s.camara_antiguedad as camaraAntiguedad 
			 FROM aporte_seteo s, 
			        ( select  s.aporte, max(s.desde) desde from aporte_seteo s
			          where s.desde <= '2024-02-01 03:00:00' and (s.hasta IS NULL OR s.hasta >= '2024-02-01 03:00:00' ) 
			          group by 	s.aporte ) x 
			 where s.aporte = x.aporte 
			 and     s.desde  = x.desde 			

update aporte_seteo
			set camara = 'DJ'
			where aporte = 'ART46'

			

select * from v_escala_salarial			

select * from tablaescalasalarialsueldos
			union
select * from tablaescalasalarialjornales
			
			select * from ddjj where id = 1
			
select max(d.id)
from ddjj d
	join (select * --max(id) 
			from ddjj d2 
			where id = 1) t on d.empresa_id = t.empresa_id and d.periodo = t.periodo

select * from boleta_pago			
			select empresa_id, id, planta from empresa_domicilio
select * from aporte			
			
			
select b from BoletaPago b 
where ddjjId IS NULL 
AND b.empresa.id = 21 
and b.intencionDePago between '2023-07-01' and '2023-06-30' 

			select * from boleta_pago 
			where ddjj_id = 102
select * from boleta_pago  where id = 156
update boleta_pago set bep = null 
			where			id = 206

			------------------------------------------------------
			--    BORRAR BOLETA de una DDJJ
			------------------------------------------------------
			select * from boleta_pago 
			where ddjj_id = 105
			
			delete from boleta_pago_ajuste
			where boleta_pago_id in (select id from boleta_pago 
			where ddjj_id = 105)
			
			delete from boleta_pago 
			where ddjj_id = 105
			
			update ddjj
			set estado = 'PR'
			where id = 105
			------------------------------------------------------
			------------------------------------------------------
			
'30501126965'
select * from usuario 	
			
select * from usuario_persona
			
select * from banco_convenio_mov_tipo
			select * from rol
			
			
select * from ddjj
where id = 105

select * from afiliado 
			
select * from afip_interes
			select * from ajuste
			
			select * from boleta_pago where ddjj_id = 105
			update  boleta_pago set forma_pago = 'VENTANILLA' where id = 210
			
			select * from ddjj where id = 210
			
			update ddjj set periodo = (periodo::date || ' 00:00:00')::timestamp
			
			select * from usuario 
			where descripcion = '33717134449'
			
			select * from empresa_restringida
			
			
			select * from  ddjj
			order by 1 desc
			
			select * from  feriado
			order by fecha desc
			
			update feriado
			set fecha = fecha - interval '1 year'
			where id > 40
			
			
select * from empresa_domicilio	

			
--------------------------------
update  empresa_contacto set valor 	= 'buenodiegomartin@gmail.com' where valor 	= 'buenodiegomartin@gmail.com.ar'

select * from usuario_persona where email = 'buenodiegomartin@gmail.com'

			"buenodiegomartin@gmail.com.ar"
select * from empresa_contacto 
--order by 1 desc			
where valor = 'buenodiegomartin@gmail.com'
			
update empresa_contacto 
set valor = 'buenodiegomartin@gmail.comXXXX'
where valor = 'buenodiegomartin@gmail.com'

			select * from empresa_contacto
			where valor = 'buenodiegomartin@gmail.com'
			select * from empresa where id=68
			
	select * from usuario u
	where DESCRIPCION =			'30710667825'

	update usuario 
	set    habilitado  = false
	where  DESCRIPCION = '30710667825'

			
select * from ddjj
			
select * from usuario u
where exists ( 
		select * from empresa e
		where e.cuit = u.descripcion			 
		and not exists ( select * from ddjj d where d.empresa_id = e.id)
			 ) 

-------------------
			
delete from usuario_clave u
where id in  ( 
		select u.id from empresa e, usuario u
		where e.cuit = u.descripcion			 
		and not exists ( select * from ddjj d where d.empresa_id = e.id)
			 )		
			
			
delete from usuario_rol
where usuario_id in ( 
		select u.id from empresa e, usuario u
		where e.cuit = u.descripcion			 
		and not exists ( select * from ddjj d where d.empresa_id = e.id)
			 )			


select * from  usuario u
where exists  ( 
		select u.id from empresa e
		where e.cuit = u.descripcion			 
		and not exists ( select * from ddjj d where d.empresa_id = e.id)
			 )					