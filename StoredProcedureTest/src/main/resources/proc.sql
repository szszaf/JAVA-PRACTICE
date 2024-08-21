create or replace procedure copy_sum_product()
    language plpgsql
as $$
declare
    row_sum integer;
    row_product integer;
    rec record;
begin
    for rec in select * from originals loop
            row_sum := rec.number1 + rec.number2 + rec.numnber3 + rec.number4;
            row_sum := rec.number1 * rec.number2 * rec.numnber3 * rec.number4;

            insert into secondaries (number1, number2, number3, number4, "sum", product)
            values (number1, number2, number3, number4, row_sum, row_product);
        end loop;
end;
$$