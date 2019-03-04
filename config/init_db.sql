create table if not exists resumes
(
  uuid char(36) not null
    constraint resumes_pk
      primary key,
  username text not null
);

comment on table resumes is 'user names table';

alter table resumes owner to postgres;

create unique index if not exists resumes_uuid_uindex
  on resumes (uuid);

create table if not exists contacts
(
  id serial not null
    constraint contacts_pk
      primary key,
  type text not null,
  value text not null,
  resume_uuid char(36) not null
    constraint contacts_resume_uuid_fk
      references resumes
      on update restrict on delete cascade
);

comment on table contacts is 'users''s contacts';

alter table contacts owner to postgres;

create unique index if not exists contacts_id_uindex
  on contacts (id);

create unique index contacts_resume_uuid_type_index
  on contacts (resume_uuid, contact_type);

