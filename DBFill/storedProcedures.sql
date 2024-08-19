create or replace procedure pay_invoice_off(buyer_id integer, invoice_tag text)
    language plpgsql
as $$
begin
    update invoices i
    set is_paid = true
    where i.buyer_id = pay_invoice_off.buyer_id and i.tag = pay_invoice_off.invoice_tag;
end;
$$

create or replace procedure end_address_owning(payer_id integer, address_id integer)
    language plpgsql
as $$
begin
    if exists (select 1 from payersaddresses pa
               where pa.payer_id = end_address_owning.payer_id and pa.address_id = end_address_owning.address_id) then
        update payersaddresses pa
        set owns_to = current_date
        where end_address_owning.payer_id = pa.payer_id and pa.address_id = end_address_owning.address_id;
    else
        raise notice 'Payer does not own this address.';
    end if;
end;
$$

create or replace procedure start_address_owning(payer_id integer, address_id integer)
    language plpgsql
as $$
begin
    if not exists (select 1 from payersaddresses pa
                   where pa.payer_id = start_address_owning.payer_id and pa.address_id = start_address_owning.address_id) then
        insert into payersaddresses(payer_id, address_id, owns_from)
        values (payer_id, address_id, current_date);
    else
        raise notice 'Payer already owns this address.';
    end if;
end;
$$