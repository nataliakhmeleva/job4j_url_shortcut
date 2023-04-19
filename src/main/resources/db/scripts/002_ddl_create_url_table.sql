  create table url (
      id serial primary key not null,
      url varchar   not null unique,
      code varchar  not null unique,
      total int            not null
  );