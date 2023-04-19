  create table site (
      id serial primary key not null,
      name varchar   not null unique,
      login varchar  not null unique,
      password varchar      not null
  );