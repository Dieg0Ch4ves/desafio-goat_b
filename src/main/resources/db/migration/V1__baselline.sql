create table users (
    id uuid primary key,
    name varchar(120) not null,
    email varchar(180) not null unique,
    password varchar(120) not null,
    role varchar(20) not null,
    active boolean not null default true,
    created_at timestamp not null,
    updated_at timestamp not null
);

create index idx_user_email on users(email);

create table suppliers (
    id uuid primary key,
    name varchar(180) not null,
    tax_id varchar(32) not null,
    email varchar(180),
    phone varchar(40),
    created_by uuid,
    updated_by uuid,
    created_at timestamp not null,
    updated_at timestamp not null,
    constraint uk_supplier_tax_id unique (tax_id)
);

create index idx_supplier_name on suppliers(name);

create table accounts_payable (
    id uuid primary key,
    supplier_id uuid not null references suppliers(id),
    description varchar(200) not null,
    category varchar(60),
    original_amount numeric(17,2) not null,
    paid_amount numeric(17,2) not null,
    issue_date date not null,
    due_date date not null,
    status varchar(20) not null,
    notes varchar(500),
    created_by uuid,
    updated_by uuid,
    created_at timestamp not null,
    updated_at timestamp not null
);

create index idx_ap_due_date on accounts_payable(due_date);
create index idx_ap_status on accounts_payable(status);
create index idx_ap_supplier on accounts_payable(supplier_id);

create table payments (
    id uuid primary key,
    account_id uuid not null references accounts_payable(id),
    payment_date date not null,
    amount numeric(17,2) not null,
    method varchar(20) not null,
    note varchar(300),
    processed_by uuid,
    created_by uuid,
    updated_by uuid,
    created_at timestamp not null,
    updated_at timestamp not null
);

create index idx_payment_account on payments(account_id);
create index idx_payment_date on payments(payment_date);

alter table suppliers
  add constraint fk_suppliers_created_by foreign key (created_by) references users(id);

alter table suppliers
  add constraint fk_suppliers_updated_by foreign key (updated_by) references users(id);

alter table accounts_payable
  add constraint fk_ap_created_by foreign key (created_by) references users(id);

alter table accounts_payable
  add constraint fk_ap_updated_by foreign key (updated_by) references users(id);

alter table payments
  add constraint fk_payments_processed_by foreign key (processed_by) references users(id);

alter table payments
  add constraint fk_payments_created_by foreign key (created_by) references users(id);

alter table payments
  add constraint fk_payments_updated_by foreign key (updated_by) references users(id);
