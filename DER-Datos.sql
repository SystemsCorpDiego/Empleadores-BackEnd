insert into afip_interes
(desde , hasta , indice)
values
('1900-01-01 03:00:00', '1939-12-31 03:00:00', 0.01);

insert into afip_interes
(desde , hasta , indice)
values
('1940-01-01 03:00:00', '1969-12-31 03:00:00', 0.04);


insert into afip_interes
(desde , hasta , indice)
values
('1970-01-01 03:00:00', '1989-12-31 03:00:00', 0.13);

insert into afip_interes
(desde , hasta , indice)
values
('1990-01-01 03:00:00', '2014-12-31 03:00:00', 0.01);


insert into afip_interes
(desde , hasta , indice)
values
('2015-01-01 03:00:00', '2015-12-31 03:00:00', 0.03);


insert into afip_interes
(desde , hasta , indice)
values
('2016-01-01 03:00:00', '2016-12-31 03:00:00', 0.04);
 

insert into afip_interes
(desde , hasta , indice)
values
('2017-01-01 03:00:00', '2017-12-31 03:00:00', 0.07);


insert into afip_interes
(desde , hasta , indice)
values
('2018-01-01 03:00:00', '2018-12-31 03:00:00', 0.11);


insert into afip_interes
(desde , hasta , indice)
values
('2019-01-01 03:00:00', '2019-12-31 03:00:00', 0.12);


insert into afip_interes
(desde , hasta , indice)
values
('2020-01-01 03:00:00', '2020-12-31 03:00:00', 0.13);



insert into afip_interes
(desde , hasta , indice)
values
('2021-01-01 03:00:00', '2021-12-31 03:00:00', 0.14);


insert into afip_interes
(desde , hasta , indice)
values
('2022-01-01 03:00:00', '2022-12-31 03:00:00', 0.15);


insert into afip_interes
(desde , hasta , indice)
values
('2022-01-01 03:00:00', '2022-12-31 03:00:00', 0.15);


insert into afip_interes
(desde , hasta , indice)
values
('2023-01-01 03:00:00', '2023-12-31 03:00:00', 0.16);


insert into afip_interes
(desde , hasta , indice)
values
('2024-01-01 03:00:00', null, 0.17);



insert into afip_vencimiento
(vigencia, dia)
values
('1900-01-01 03:00:00', 7)

insert into afip_vencimiento
(vigencia, dia)
values
('2017-12-01 03:00:00', 9)



insert into banco
(descripcion)
values
('Banco de la Nación Argentina');

select * from banco_sucursal


insert into banco_sucursal
(banco_id, codigo, descripcion)
values
(1, '54', 'Sucursal 54');

insert into banco_sucursal
(banco_id, codigo, descripcion)
values
(1, '46', 'Sucursal 46');

insert into banco_sucursal
(banco_id, codigo, descripcion)
values
(1, '10', 'Sucursal 10');

insert into banco_sucursal
(banco_id, codigo, descripcion)
values
(1, '11', 'Sucursal 11');

select * from banco_sucursal
select * from banco_convenio

insert into banco_convenio
(codigo, cuenta, sucursal_id)
values
('5783', '79781', 1); -- 1-UOMA Boleta Blanca

insert into banco_convenio
(codigo, cuenta, sucursal_id)
values
('5784', '79090', 4); -- 4-UOMA Aporte Solidario

insert into banco_convenio
(codigo, cuenta, sucursal_id)
values
('5785', '78732', 4); -- 5-UOMA Art. 46


insert into banco_convenio
(codigo, cuenta, sucursal_id)
values
('5652', '59538', 3); -- 2-ANTIMA Boleta Blanca y Cuota Social
 

insert into banco_convenio
(codigo, cuenta, sucursal_id)
values
('5782', '79848', 2); --OSPIM Boleta Blanca

select * from aporte
select * from banco_convenio_mov_tipo


insert into banco_convenio_mov_tipo
(convenio_id, aporte, tipo, descripcion)
values
(1, 'UOMAG', '7', 'Generacion de Boleta Blanca'); --UOMA

insert into banco_convenio_mov_tipo
(convenio_id, aporte, tipo, descripcion)
values
(1, 'UOMACS', '2', 'Pago de Cuota Social'); -- UOMA

insert into banco_convenio_mov_tipo
(convenio_id, aporte, tipo, descripcion)
values
(1, 'UOMACU', '3', 'Pago de Cuota Usufructo'); --Convenio UOMA

insert into banco_convenio_mov_tipo
(convenio_id, aporte, tipo, descripcion)
values
(5, 'ART46', '4', 'Pago de Artículo 46'); --Convenio UOMA

insert into banco_convenio_mov_tipo
(convenio_id, aporte, tipo, descripcion)
values
(4, 'UOMAAS', '5', 'Pago de Aporte Solidario'); --Convenio UOMA

insert into banco_convenio_mov_tipo
(convenio_id, aporte, tipo, descripcion)
values
(2, 'AMTIMAG', '8', 'Generacion de Boleta Blanca'); -- AMTIMA

insert into banco_convenio_mov_tipo
(convenio_id, aporte, tipo, descripcion)
values
(2, 'AMTIMACS', '1', 'Cuota Social'); --AMTIMA

insert into banco_convenio_mov_tipo
(convenio_id, aporte, tipo, descripcion)
values
(3, 'OSPIMG', '6', 'Generacion de Boleta Blanca'); --Convenio OSPIM




insert into publicacion
(titulo, cuerpo, vigencia_desde, vigencia_hasta)
values
('Prueba piloto', 'Este es el cuerpo de la prueba', '1900-01-01', '2090-01-01');

insert into publicacion
(titulo, cuerpo, vigencia_desde, vigencia_hasta)
values
('Prueba piloto II', 'Texto de PRUEBA II. hdhdhdhdhdhhd .-', '1900-01-01', '2090-01-01');

insert into publicacion
(titulo, cuerpo, vigencia_desde, vigencia_hasta)
values
('Prueba piloto XXX', 'NO MOSTRAR.-', '1900-01-01', '2000-01-01');

insert into publicacion
(titulo, cuerpo, vigencia_desde, vigencia_hasta)
values
('Prueba piloto SIN FIN', 'Texto de PRUEBA par aa publicacion SIN FIN (fecha hasta null) .-', '1900-01-01', null);

select * from funcionalidad

INSERT INTO funcionalidad (id, descripcion)
VALUES
    (1, 'PUBLICACIONES')
	
INSERT INTO funcionalidad (id, descripcion)
VALUES (2, 'FERIADOS')

INSERT INTO funcionalidad (id, descripcion)
VALUES (3, 'DDJJ')

INSERT INTO funcionalidad (id, descripcion)
VALUES     (4, 'DDJJ_CONSULTA')

INSERT INTO funcionalidad (id, descripcion)
VALUES (5, 'BOLETAS')

INSERT INTO funcionalidad (id, descripcion)
VALUES (6, 'PAGOS')

INSERT INTO funcionalidad (id, descripcion)
VALUES     (7, 'DATOS_EMPRESA')

INSERT INTO funcionalidad (id, descripcion)
VALUES (8, 'BOLETA_BLANCA')

INSERT INTO funcionalidad (id, descripcion)
VALUES     (9, 'GESTION_ROLES')

INSERT INTO funcionalidad (id, descripcion)
VALUES     (10, 'CUITS_RESTRINGIDOS')

INSERT INTO funcionalidad (id, descripcion)
VALUES     (11, 'ROLES')

INSERT INTO funcionalidad (id, descripcion)
VALUES     (12, 'USUARIO_INTERNO')

INSERT INTO funcionalidad (id, descripcion)
VALUES     (13, 'INTERESES_AFIP')

INSERT INTO funcionalidad (id, descripcion)
VALUES     (14,'AJUSTES')


select * from rol
 update rol set id = 6 where id = 11
 
insert into rol
(descripcion, baja)
values
('ROOT', false);

insert into rol
(descripcion, baja)
values
('ADMINISTRADOR', false);

insert into rol
(descripcion, baja)
values
('AUTENTICACION_PARCIAL', false);


insert into rol
(descripcion, baja)
values
('EMPLEADOR', false);

insert into rol
(descripcion, baja)
values
('OSPIM_EMPLEADO', false);


insert into rol
(descripcion, baja)
values
('ADMINISTRATIVO', false);


insert into rol_funcionalidad (rol, funcionalidad, activo)
values
('EMPLEADOR','PUBLICACIONES',true),
('EMPLEADOR','FERIADOS',true),
('EMPLEADOR','DDJJ',true),
('EMPLEADOR','DDJJ_CONSULTA',true),
('EMPLEADOR','BOLETAS',true),
('EMPLEADOR','PAGOS',true),
('EMPLEADOR','DATOS_EMPRESA',true),
('EMPLEADOR','BOLETA_BLANCA',true),
('EMPLEADOR','GESTION_ROLES',true),
('EMPLEADOR','CUITS_RESTRINGIDOS',true),
('EMPLEADOR','ROLES',true),
('EMPLEADOR','USUARIO_INTERNO',true),
('EMPLEADOR','INTERESES_AFIP',true),
('EMPLEADOR', 'AJUSTES',true),

('ADMINISTRATIVO','FERIADOS',true),
('ADMINISTRATIVO','DDJJ',true),
('ADMINISTRATIVO','DDJJ_CONSULTA',true),
('ADMINISTRATIVO','BOLETAS',true),
('ADMINISTRATIVO','PAGOS',true),
('ADMINISTRATIVO','DATOS_EMPRESA',true),
('ADMINISTRATIVO','BOLETA_BLANCA',true),
('ADMINISTRATIVO','GESTION_ROLES',true),
('ADMINISTRATIVO','CUITS_RESTRINGIDOS',true),
('ADMINISTRATIVO','ROLES',true),
('ADMINISTRATIVO','USUARIO_INTERNO',true),
('ADMINISTRATIVO','INTERESES_AFIP',true),
('ADMINISTRATIVO', 'AJUSTES',true),

('ROOT -1','FERIADOS',true),
('ROOT -1','DDJJ',true),
('ROOT -1','DDJJ_CONSULTA',true),
('ROOT -1','BOLETAS',true),
('ROOT -1','PAGOS',true),
('ROOT -1','DATOS_EMPRESA',true),
('ROOT -1','BOLETA_BLANCA',true),
('ROOT -1','GESTION_ROLES',true),
('ROOT -1','CUITS_RESTRINGIDOS',true),
('ROOT -1','ROLES',true),
('ROOT -1','USUARIO_INTERNO',true),
('ROOT -1','INTERESES_AFIP',true),
('ROOT -1', 'AJUSTES',true),

('ADMINISTRADOR','FERIADOS',true),
('ADMINISTRADOR','DDJJ',true),
('ADMINISTRADOR','DDJJ_CONSULTA',true),
('ADMINISTRADOR','BOLETAS',true),
('ADMINISTRADOR','PAGOS',true),
('ADMINISTRADOR','DATOS_EMPRESA',true),
('ADMINISTRADOR','BOLETA_BLANCA',true),
('ADMINISTRADOR','GESTION_ROLES',true),
('ADMINISTRADOR','CUITS_RESTRINGIDOS',true),
('ADMINISTRADOR','ROLES',true),
('ADMINISTRADOR','USUARIO_INTERNO',true),
('ADMINISTRADOR','INTERESES_AFIP',true),
('ADMINISTRADOR', 'AJUSTES',true),

('OSPIM_EMPLEADO','FERIADOS',true),
('OSPIM_EMPLEADO','DDJJ',true),
('OSPIM_EMPLEADO','DDJJ_CONSULTA',true),
('OSPIM_EMPLEADO','BOLETAS',true),
('OSPIM_EMPLEADO','PAGOS',true),
('OSPIM_EMPLEADO','DATOS_EMPRESA',true),
('OSPIM_EMPLEADO','BOLETA_BLANCA',true),
('OSPIM_EMPLEADO','GESTION_ROLES',true),
('OSPIM_EMPLEADO','CUITS_RESTRINGIDOS',true),
('OSPIM_EMPLEADO','ROLES',true),
('OSPIM_EMPLEADO','USUARIO_INTERNO',true),
('OSPIM_EMPLEADO','INTERESES_AFIP',true),
('OSPIM_EMPLEADO', 'AJUSTES',true),

('AUTENTICACION_PARCIAL','FERIADOS',true),
('AUTENTICACION_PARCIAL','DDJJ',true),
('AUTENTICACION_PARCIAL','DDJJ_CONSULTA',true),
('AUTENTICACION_PARCIAL','BOLETAS',true),
('AUTENTICACION_PARCIAL','PAGOS',true),
('AUTENTICACION_PARCIAL','DATOS_EMPRESA',true),
('AUTENTICACION_PARCIAL','BOLETA_BLANCA',true),
('AUTENTICACION_PARCIAL','GESTION_ROLES',true),
('AUTENTICACION_PARCIAL','CUITS_RESTRINGIDOS',true),
('AUTENTICACION_PARCIAL','ROLES',true),
('AUTENTICACION_PARCIAL','USUARIO_INTERNO',true),
('AUTENTICACION_PARCIAL','INTERESES_AFIP',true),
('AUTENTICACION_PARCIAL', 'AJUSTES',true),

('PRUEBA','FERIADOS',true),
('PRUEBA','DDJJ',true),
('PRUEBA','DDJJ_CONSULTA',true),
('PRUEBA','BOLETAS',true),
('PRUEBA','PAGOS',true),
('PRUEBA','DATOS_EMPRESA',true),
('PRUEBA','BOLETA_BLANCA',true),
('PRUEBA','GESTION_ROLES',true),
('PRUEBA','CUITS_RESTRINGIDOS',true),
('PRUEBA','ROLES',true),
('PRUEBA','USUARIO_INTERNO',true),
('PRUEBA','INTERESES_AFIP',true),
('PRUEBA', 'AJUSTES',true),

('ROL 4','FERIADOS',true),
('ROL 4','DDJJ',true),
('ROL 4','DDJJ_CONSULTA',true),
('ROL 4','BOLETAS',true),
('ROL 4','PAGOS',true),
('ROL 4','DATOS_EMPRESA',true),
('ROL 4','BOLETA_BLANCA',true),
('ROL 4','GESTION_ROLES',true),
('ROL 4','CUITS_RESTRINGIDOS',true),
('ROL 4','ROLES',true),
('ROL 4','USUARIO_INTERNO',true),
('ROL 4','INTERESES_AFIP',true),
('ROL 4', 'AJUSTES',true);


select * from usuario
    
insert into usuario
(descripcion, habilitado, dfa_secreto, dfa_habilitado, baja)
values
('admin', true, null, false, false);


insert into usuario
(descripcion, habilitado, dfa_secreto, dfa_habilitado, baja)
values
('root', true, null, false, false);
 

insert into usuario
(descripcion, habilitado, baja)
values
( 'usuario1', true, false);


select * from usuario
    
    
select * from usuario_clave
    delete from usuario_clave

insert into usuario_clave
(id, baja, hash_algorithm, salt, clave)
values
(1, false, 'hashAlgorithm', 'salt', '$2a$10$d2bs1mQx2NLkGHuYIdHwqOxtICveeeihJGnktPRqA9BHhIc7Ah0ZS');


insert into usuario_clave
(id, baja, hash_algorithm, salt, clave)
values
(2, false, 'hashAlgorithm', 'salt', '$2a$10$d2bs1mQx2NLkGHuYIdHwqOxtICveeeihJGnktPRqA9BHhIc7Ah0ZS');


insert into usuario_clave
(id, baja, hash_algorithm, salt, clave)
values
(3, false, 'hashAlgorithm', 'salt', '$2a$10$d2bs1mQx2NLkGHuYIdHwqOxtICveeeihJGnktPRqA9BHhIc7Ah0ZS');

select * from rol  ***XXX

    insert into usuario_rol
(rol_id, usuario_id, baja)
values
(2, 1, false);


insert into usuario_rol
(rol_id, usuario_id, baja)
values
(1, 3, false);

insert into usuario_rol
(rol_id, usuario_id, baja)
values
(2, 1, false);

insert into usuario_rol
(rol_id, usuario_id, baja)
values
(4, 5, false);


insert into usuario_persona
(usuario_id, nombre, apellido, email)
values
( 2, 'pepe', 'Tomato', 'pt@gmail.com');

commit;
--***XXX
select * from empresa
    
    insert  into empresa
    (cuit, razon_social, ramo_id)
    values
    ('30707400753', 'AGRO EMPRESA NORTE S.A.', 4);
    
    select * from usuario
    select * from usuario_empresa
   	 
   	 delete from usuario_empresa where usuario_id = 1
   	 
    insert into usuario
    (descripcion, habilitado, dfa_habilitado, baja)
    values
    ('30707400753', true, false, false);

    select * from ramo
   	 
    insert into usuario_empresa
    (empresa_id, usuario_id)
    values
    (1,5);

    insert into usuario_clave
    (id, baja, hash_algorithm, salt, clave)
    values
    (5, false, 'hashAlgorithm', 'salt', '$2a$10$d2bs1mQx2NLkGHuYIdHwqOxtICveeeihJGnktPRqA9BHhIc7Ah0ZS');



insert into ramo
( descripcion )
values
('Nutrición Animal');

insert into ramo
( descripcion )
values
('Avícola');

insert into ramo
( descripcion )
values
('Molienda de Trigo');

insert into ramo
( descripcion )
values
('Molienda de Maíz');

insert into ramo
( descripcion )
values
('Personal Temporario');

insert into ramo
( descripcion )
values
('Transporte y Distribución');

insert into ramo
( descripcion )
values
('Otros');



insert into aporte
( codigo, descripcion, entidad, ddjj, orden )
values
('ART46', 'Art. 46', 'UOMA', true, 1);


insert into aporte
( codigo, descripcion, entidad, ddjj, orden )
values
('UOMACS', 'Cuota Social', 'UOMA', true, 2);


insert into aporte
( codigo, descripcion, entidad, ddjj, orden )
values
('UOMAAS', 'Aporte Solidario', 'UOMA', true, 3);



insert into aporte
( codigo, descripcion, entidad, ddjj, orden )
values
('UOMACU', 'Cuota Usufructo', 'UOMA', true, 4);


insert into aporte
( codigo, descripcion, entidad, ddjj, orden )
values
('AMTIMACS', 'Cuota Social', 'AMTIMA', true, 5);

--GENERICOS
insert into aporte
( codigo, descripcion, entidad, ddjj, orden )
values
('UOMAG', 'Aporte genérico', 'UOMA', false, 6);
 
insert into aporte
( codigo, descripcion, entidad, ddjj, orden )
values
('AMTIMAG', 'Aporte genérico', 'AMTIMA', false, 7);

insert into aporte
( codigo, descripcion, entidad, ddjj, orden )
values
('OSPIMG', 'Aporte genérico', 'OSPIM', false, 8);


select * from aporte_seteo
 
    


insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('ART46', '1900-01-01 00:00:00', null, null, null, 'PO', 200, 'PJ', 'CAENA', 'E', 0);

insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('ART46', '1900-01-01 00:00:00', null, null, null, 'PO', 200, 'PJ', 'CEPA', 'E', 0);

insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('ART46', '1900-01-01 00:00:00', '2023-02-28 00:00:00', null, null, 'PO', 200, 'PJ', 'FAIM', 'E', 0);

insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('ART46', '2023-03-01 00:00:00', null, null, null, 'PO', 200, 'PJ', 'FAIM', 'D', 0);




insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('UOMACS', '1900-01-01 00:00:00', null, 'UOMA', 'S', 'PO', 2, 'RE', null, null, null);

insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('UOMAAS', '1900-01-01 00:00:00', null, 'UOMA', 'S', 'PO', 2, 'RE',  null, null, null);

insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('UOMACU', '1900-01-01 00:00:00', null, 'UOMA', 'N', 'PO', 2, 'RE', null, null, null);



insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('AMTIMACS', '2022-05-01 00:00:00', '2022-08-31 00:00:00', 'AMTIMA', 'S', 'EN', 3000, null, null, null, null);


insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('AMTIMACS', '2022-09-01 00:00:00', '2022-12-31 00:00:00', 'AMTIMA', 'S', 'EN', 3500, null, null, null, null);


insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('AMTIMACS', '2023-01-01 00:00:00', '2023-05-30 00:00:00', 'AMTIMA', 'S', 'EN', 4500, null, null, null, null);

insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('AMTIMACS', '2023-06-01 00:00:00', '2023-08-31 00:00:00', 'AMTIMA', 'S', 'EN', 6000, null, null, null, null);

insert into aporte_seteo
(aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
('AMTIMACS', '2023-09-01 00:00:00', null, 'AMTIMA', 'S', 'EN', 7800, null, null, null, null);
 
 
 
 

insert into tablaescalasalarialjornales
(id, antiguedaddesde, camara, cata, catb, catc, catd, cate, catf, fechadesde)
values
( 557082, 0, 'FAIM', 368.33, 2797.60, 2526.86, 2256.13, null, null, '2023-12-01' );

insert into tablaescalasalarialjornales
(id, antiguedaddesde, camara, cata, catb, catc, catd, cate, catf, fechadesde)
values
( 557083, 1, 'FAIM', 3099.02, 2825.57, 2552.13, 2278.69, null, null, '2023-12-01' );

insert into tablaescalasalarialjornales
(id, antiguedaddesde, camara, cata, catb, catc, catd, cate, catf, fechadesde)
values
( 557084, 2, 'FAIM', 3129.70, 2853.55, 2577.40, 2301.25, null, null, '2023-12-01' );

insert into tablaescalasalarialjornales
(id, antiguedaddesde, camara, cata, catb, catc, catd, cate, catf, fechadesde)
values
( 557085, 4, 'FAIM', 3160.38, 2881.53, 2602.67, 2323.81, null, null, '2023-12-01' );

insert into tablaescalasalarialjornales
(id, antiguedaddesde, camara, cata, catb, catc, catd, cate, catf, fechadesde)
values
( 557086, 6, 'FAIM', 3191.07, 2909.50, 2627.94, 2346.37, null, null, '2023-12-01' );
 
insert into tablaescalasalarialjornales
(id, antiguedaddesde, camara, cata, catb, catc, catd, cate, catf, fechadesde)
values
( 557087, 8, 'FAIM', 3221.75, 2937.48, 2653.21, 2368.93, null, null, '2023-12-01' );
 
insert into tablaescalasalarialjornales
(id, antiguedaddesde, camara, cata, catb, catc, catd, cate, catf, fechadesde)
values
( 557088, 10, 'FAIM', 3252.43, 2965.45, 2678.47, 2391.49, null, null, '2023-12-01' );
 
 
 
 
 insert into afiliado
 (cuil_titular, inte, cuil, apellido, nombre, alta_fecha, alta_usr, modi_fecha, modi_usr, baja_fecha, baja_usr )
 values
 ( '27367071112', 0, '27367071112', 'Nelo', 'Rosario', NOW(), 1, NOW(), 1, null, null); --    07/03/92
 
 
 insert into afiliado
 (cuil_titular, inte, cuil, apellido, nombre, alta_fecha, alta_usr, modi_fecha, modi_usr, baja_fecha, baja_usr )
 values
 ( '23278871924', 0, '23278871924', 'Morales', 'Alfio', NOW(), 1, NOW(), 1, null, null); --    08/02/80
 
 insert into afiliado
 (cuil_titular, inte, cuil, apellido, nombre, alta_fecha, alta_usr, modi_fecha, modi_usr, baja_fecha, baja_usr )
 values
 ( '27312728848', 0, '27312728848', 'Castaneda', 'Manfredo', NOW(), 1, NOW(), 1, null, null); --    19/12/84
 
 insert into afiliado
 (cuil_titular, inte, cuil, apellido, nombre, alta_fecha, alta_usr, modi_fecha, modi_usr, baja_fecha, baja_usr )
 values
 ( '27418918786', 0, '27418918786', 'Chapa', 'Gladys', NOW(), 1, NOW(), 1, null, null); --    07/07/99
 
 
 insert into afiliado
 (cuil_titular, inte, cuil, apellido, nombre, alta_fecha, alta_usr, modi_fecha, modi_usr, baja_fecha, baja_usr )
 values
 ( '20275889645', 0, '20275889645', 'Vasquez', 'Clementino', NOW(), 1, NOW(), 1, null, null); --    07/08/79
 
 
 insert into afiliado
 (cuil_titular, inte, cuil, apellido, nombre, alta_fecha, alta_usr, modi_fecha, modi_usr, baja_fecha, baja_usr )
 values
 ( '27106098374', 0, '27106098374', 'Salcido', 'Leuco', NOW(), 1, NOW(), 1, null, null); --    04/01/53    

 insert into afiliado
 (cuil_titular, inte, cuil, apellido, nombre, alta_fecha, alta_usr, modi_fecha, modi_usr, baja_fecha, baja_usr )
 values
 ( '23279737229', 0, '23279737229', 'Vázquez', 'Lot', NOW(), 1, NOW(), 1, null, null); --    04/01/53    

 insert into afiliado
 (cuil_titular, inte, cuil, apellido, nombre, alta_fecha, alta_usr, modi_fecha, modi_usr, baja_fecha, baja_usr )
 values
 ( '20294465996', 0, '20294465996', 'Tapia', 'Querubín', NOW(), 1, NOW(), 1, null, null); --    04/01/53    
 
 
 select * from ramo
 select * from empresa
 
 select * from empresa_domicilio
 update empresa set id = 1 where id = 16

 insert into empresa
 (cuit, razon_social, ramo_id)
 values
 ('30707400753', 'AGRO EMPRESA NORTE S.A.',  4)
 
 insert into empresa_domicilio
 ( empresa_id, tipo, provincia_id, localidad_id, calle, calle_nro,
   piso, depto, oficina, cp, planta)
 values
 ( 1, 'FISCAL', 3, 2, 'L Lugones', 'S/n',
  null, null, null,  '5248', 'Domicilio Fiscal');
 
  /*
   update empresa_domicilio
   set planta = 'Domicilio Fiscal'
   where id = 32
 */
 
 select * from provincia
 select * from localidad
 select * from empresa_domicilio
 
 insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (0, 'Ciudad Autónoma de Buenos Aires', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (1, 'Buenos Aires', null, null, null);
--------------------
insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (2, 'Catamarca', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (3, 'Cordoba', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (4, 'Corrientes', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (5, 'Entre Ríos', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (6, 'Jujuy', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (7, 'Mendoza', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (8, 'La Rioja', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (9, 'Salta', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (10, 'San Juna', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (11, 'San Luis', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (12, 'Santa Fe', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (13, 'Santiago del Estero', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (14, 'Tucumán', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (16, 'Chaco', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (17, 'Chubut', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (18, 'Formosa', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (19, 'Misiones', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (20, 'Neuquén', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (21, 'La Pampa', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (22, 'Río Negro', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (23, 'Santa Cruz', null, null, null);

insert into provincia
 (id_provincia, detalle, id_sssalud, id_provincia_afip, cod_postal )
 values
 (24, 'Tierra del Fuego', null, null, null);

 select * from provincia
 select * from localidad
 
 insert into localidad
 (id_provincia, detalle, id_provinciasss, id_localidadesss, cod_postal)
 values
 (0, 'CABA', null, null, null);
 
 insert into localidad
 (id_provincia, detalle, id_provinciasss, id_localidadesss, cod_postal)
 values
 (3, 'Villa Maria Del Rio Seco', null, null, '5248');
 
 
 insert into localidad
 (id_provincia, detalle, id_provinciasss, id_localidadesss, cod_postal)
 values
 (3, '9 de Julio', null, null, '7252');
 
 insert into localidad
 (id_provincia, detalle, id_provinciasss, id_localidadesss, cod_postal)
 values
 (3, 'Arroyito', null, null, '2434');
 
   
   
 insert into localidad
 (id_provincia, detalle, id_provinciasss, id_localidadesss, cod_postal)
 values
 (1, 'Carlos Casares', null, null, '6530');

 insert into localidad
 (id_provincia, detalle, id_provinciasss, id_localidadesss, cod_postal)
 values
 (1, 'Acassuso', null, null, '1640');

 insert into localidad
 (id_provincia, detalle, id_provinciasss, id_localidadesss, cod_postal)
 values
 (1, 'Alberdi', null, null, '6034');
 
 
 insert into localidad
 (id_provincia, detalle, id_provinciasss, id_localidadesss, cod_postal)
 values
 (1, 'Arrecifes', null, null, '2740');
 
 
 
 insert into ddjj
 (periodo, secuencia, empresa_id, estado, presentada_fecha, creado_en)
 values
 ('2024-01-01', null, 1, 'PE', null, NOW() )
 
 select * from ddjj
 
 select * from ddjj_deta
 select * from  ddjj_deta_aporte
 -- CUIL invalido: no graba DDJJ
 -- camara, fecha ingreso, remun y no remun se pueden dejar en blanco.
 -- planta, categoria no dan opcion a dejar nulo. El combo selecciona algo.
 
 --Camara: se valida que tenga datos al momento de Presentar la ddjj. Combo con opcion de vacio
 --Aportes: debe seleccionarlos al momento de Presentar ddjj
 --Presentar: si tiene errores no deja Presentar !!
 
 insert into ddjj_deta
 (ddjj_id, afiliado_cuil_titular, afiliado_inte, empresa_domicilio_id, camara, categoria, remunerativo, no_remunerativo,
  ingreso,  uoma_socio,  antima_socio)
 values
 (1, '27106098374', 0, 32, 'CAENA', 'C', 500400, null, '2020-06-01', true, false);
 
 insert into ddjj_deta_aporte
 (ddjj_deta_id, aporte, importe)
 values
 (1, 'UOMACS',  10008.00); --select 500400*0.02
 
 
 insert into ddjj_deta_aporte
 (ddjj_deta_id, aporte, importe)
 values
 (1, 'UOMAAS',  10008.00); --select 500400*0.02
 
 insert into ddjj_deta_aporte
 (ddjj_deta_id, aporte, importe)
 values
 (1, 'ART46',  444.92 );  --select 222.46*2

 select * from ddjj_deta
 where afiliado_cuil_titular = '20275889645'
 
 update ddjj_deta
 set remunerativo = 1470800.75, no_remunerativo = 574800
 
 where id = 2
 insert into ddjj_deta
 (ddjj_id, afiliado_cuil_titular, afiliado_inte, empresa_domicilio_id, camara, categoria, remunerativo, no_remunerativo,
  ingreso, uoma_socio,  antima_socio)
 values
 (1, '20275889645', 0, 32, 'CEPA', 'E', 470800.75, null, '2021-01-01', true, true);

 insert into ddjj_deta_aporte
 (ddjj_deta_id, aporte, importe)
 values
 (2, 'UOMACS',  10008.00); --select 470800.75*0.02
 
 
 insert into ddjj_deta_aporte
 (ddjj_deta_id, aporte, importe)
 values
 (2, 'UOMAAS',  10008.00);
 
 insert into ddjj_deta_aporte
 (ddjj_deta_id, aporte, importe)
 values
 (2, 'ART46',  520.00 );  --select 260.00*2

 insert into ddjj_deta_aporte
 (ddjj_deta_id, aporte, importe)
 values
 (2, 'AMTIMACS',  7800);  --select 260.00*2


 insert into ddjj_deta
 (ddjj_id, afiliado_cuil_titular, afiliado_inte, empresa_domicilio_id, camara, categoria, remunerativo, no_remunerativo,
  ingreso, uoma_socio,  antima_socio)
 values
 (1, '27418918786', 0, 32, 'FAIM', 'D', 367001.05, null, '2021-06-01', false, false);

 insert into ddjj_deta_aporte
 (ddjj_deta_id, aporte, importe)
 values
 (3, 'ART46', 4512.26); --select 2256.13*2

 insert into ddjj_deta_aporte
 (ddjj_deta_id, aporte, importe)
 values
 (3, 'UOMACU', 7340.02); --select 367001.05*0.02


 

select * from ddjj_deta_aporte
select * from ddjj_deta
select * from afiliado
 select * from v_camara
  select * from v_camara_categoria
 
 
 select * from aporte
  select * from aporte_seteo
 select * from empresa
 select * from empresa_domicilio where empresa_id = 7
 select * from ddjj_deta
 select * from ddjj_deta_aporte
						 

		 	
--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- 24/05/2024

select * from aporte

update aporte 
set descripcion = 'Aporte Solidario UOMA'
where codigo = 'UOMAAS'

update aporte 
set descripcion = 'Cuota Social AMTIMA'
where codigo = 'AMTIMACS'


--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- 30/05/2024

update aporte_seteo
set camara = 'DJ'
where aporte = 'ART46'

 
 
update afip_interes
set hasta = '2024-02-29 03:00:00'
where id = 15

insert into afip_interes
(desde, hasta, indice)
values
('2024-03-01 03:00:00', '2024-03-31 03:00:00', 0.2 )

insert into afip_interes
(desde, hasta, indice)
values
('2024-04-01 03:00:00', '2024-04-30 03:00:00', 0.22 )

insert into afip_interes
(desde, hasta, indice)
values
('2024-05-01 03:00:00', null, 0.24 )
 
update afip_interes
set desde = (desde::date || ' 00:00:00')::timestamp,
    hasta = (hasta::date || ' 23:59:59')::timestamp
	
--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- PROXIMO....

select * from aporte

select * from afip_vencimiento

select * from aporte_vencimiento

delete  from aporte_vencimiento

insert into aporte_vencimiento
(aporte_codigo, desde, hasta, dia)
values
('UOMAAS', '1900-01-01', null, 15);

insert into aporte_vencimiento
(aporte_codigo, desde, hasta, dia)
values
('AMTIMACS', '1900-01-01', null, 15);

delete from afip_vencimiento;

insert into afip_vencimiento
(vigencia, dia)
values
('1900-01-01 03:00:00', 7);

insert into afip_vencimiento
(vigencia, dia)
values
('2018-01-01 00:00:00',	9);


 
 
select * from aporte 

update aporte 
set descripcion = 'Aporte Solidario'
where codigo = 'UOMAAS'

update aporte 
set descripcion = 'Aporte Solidario'
where codigo = 'UOMAAS'



--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- PROXIMO....

insert into funcionalidad (id,descripcion) values (15,'DEUDA') 

insert into rol_funcionalidad (id, rol, funcionalidad, activo)
values (148, 'EMPLEADOR', 'DEUDA', true)


--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- PROXIMO....


update localidad
set detalle = UPPER(detalle)

update provincia
set detalle = UPPER(detalle)

--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- PROXIMO....



update funcionalidad 
set descripcion = 'INTERESES'
where descripcion = 'INTERESES_AFIP'

update rol_funcionalidad
set funcionalidad = 'INTERESES'
where  funcionalidad = 'INTERESES_AFIP'


--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- PROXIMO....

select * from funcionalidad

update funcionalidad 
set descripcion = 'Nueva_DDJJ'
where descripcion = 'DDJJ'

update rol_funcionalidad
set funcionalidad = 'Nueva_DDJJ'
where  funcionalidad = 'DDJJ'



update funcionalidad 
set descripcion = 'Mis_BOLETAS'
where descripcion = 'BOLETAS'

update rol_funcionalidad
set funcionalidad = 'Mis_BOLETAS'
where  funcionalidad = 'BOLETAS'



update funcionalidad 
set descripcion = 'Boleta_Actas'
where descripcion = 'BOLETA_BLANCA'

update rol_funcionalidad
set funcionalidad = 'Boleta_Actas'
where  funcionalidad = 'BOLETA_BLANCA'





update funcionalidad 
set descripcion = 'Datos_Perfil'
where descripcion = 'DATOS_EMPRESA'

update rol_funcionalidad
set funcionalidad = 'Datos_Perfil'
where  funcionalidad = 'DATOS_EMPRESA'



insert into funcionalidad
(id, descripcion)
values
(15, 'Mis_DDJJ')



delete from rol_funcionalidad
where  funcionalidad = 'PAGOS'

delete from funcionalidad
where  descripcion = 'PAGOS'

----------------TOD EN MAYUSCULA
update funcionalidad 
set descripcion = upper(descripcion)

update rol_funcionalidad
set funcionalidad = upper(funcionalidad)


--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- PROXIMO....

-- select * from rol_funcionalidad order by 1 desc

insert into funcionalidad (id,descripcion) values (16,'BOLETAS_CONSULTA') 

insert into rol_funcionalidad (  rol, funcionalidad, activo)
values ( 'ADMINISTRATIVO','BOLETAS_CONSULTA',true);


--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- PROXIMO....

-- select * from rol_funcionalidad order by 1 desc

insert into funcionalidad (id,descripcion) values (17,'CONSULTA_EMPRESA') 

insert into rol_funcionalidad (  rol, funcionalidad, activo)
values ( 'ADMINISTRATIVO','CONSULTA_EMPRESA',true);

--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- PROXIMO....
-- select * from funcionalidad order by 1 desc

insert into funcionalidad (id,descripcion) values (18,'ID_EMPRESA_TEST') 


insert into funcionalidad (id,descripcion) values (19,'CONSULTA_EMPRESA_ALTA_MODI') 



update funcionalidad 
set descripcion = 'EMPRESA_CONTACTO_DOMICILIO_ABM'
where descripcion = 'CONSULTA_EMPRESA_ALTA_MODI'
 

update rol_funcionalidad
set funcionalidad = 'EMPRESA_CONTACTO_DOMICILIO_ABM'
where funcionalidad = 'CONSULTA_EMPRESA_ALTA_MODI'



insert into funcionalidad (id,descripcion) values (20,'EMPRESA_DATOS_MODI') 

insert into empresa 
(cuit, razon_social, actividad_molinera )
values 
('11111111111', 'EMPRESA DEMO', true )



		select * from usuario -- 56 - "11111111111"
		
		select * from usuario_rol -- 4 - "EMPLEADOR"
		
		
		insert into usuario_rol
		(rol_id, usuario_id, baja)
		values
		(4, 56, false)

		insert into usuario_empresa
		(empresa_id, usuario_id)
		values
		(100, 56)


		select * from empresa -- 100 - EMPRESA DEMO
		
		-- ** ENTRAR AL CUIT TODO 1 y DAR DE ALTA DOMICILIO.-
		
		

insert into empresa 
(cuit, razon_social, actividad_molinera )
values 
('11111111111', 'EMPRESA DEMO', true )


select * from empresa_domicilio
where empresa_id = 51





--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- PROXIMO....



select * from aporte_seteo
where aporte = 'AMTIMACS'

update aporte_seteo
set hasta = '2024-01-31 00:00:00'
where id=12

insert into aporte_seteo
(id, aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
(13, 'AMTIMACS', '2024-02-01 00:00:00', '2024-06-30 00:00:00', 'AMTIMA', true, 'EN', 11000, null, null, null, null);


insert into aporte_seteo
(id, aporte, desde, hasta, entidad, socio, calculo_tipo, calculo_valor, calculo_base, camara, camara_categoria, camara_antiguedad)
values
(14, 'AMTIMACS', '2024-07-01 00:00:00', null, 'AMTIMA', true, 'EN', 19076, null, null, null, null);

 
 
 
--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- PROXIMO....

 
alter table afip_interes
ALTER COLUMN indice TYPE real ;

