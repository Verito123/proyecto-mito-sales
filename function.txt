CREATE OR REPLACE FUNCTION fn_sales () 
 RETURNS TABLE (
 quantityFn INT,
 datetimeFn TEXT
) 
AS $$
DECLARE 
    var_r record;
BEGIN
FOR var_r IN(
	select (count(*)::int) as quantity, to_char(s.date_time, 'dd/MM/yyyy') as datetime from sale s
	group by to_char(s.date_time, 'dd/MM/yyyy') order by to_char(s.date_time, 'dd/MM/yyyy') asc 
	)  
 LOOP
        quantityFn := var_r.quantity; 
 		datetimeFn := var_r.datetime;
        RETURN NEXT;
 END LOOP;
END; $$ 
LANGUAGE 'plpgsql';

select * from fn_sales();
