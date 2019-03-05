create table if not exists resumes
(
  uuid char(36) not null
    constraint resumes_pk
      primary key,
  username text not null
);

comment on table resumes is 'user names table';

alter table resumes owner to postgres;

create unique index resumes_uuid_uindex
  on resumes (uuid);

create table if not exists contacts
(
  id serial not null
    constraint contacts_pk
      primary key,
  contact_type text not null,
  contact_value text not null,
  resume_uuid char(36) not null
    constraint contacts_resume_uuid_fk
      references resumes
      on update cascade on delete cascade
);

comment on table contacts is 'resume user''s contacts';

alter table contacts owner to postgres;

create unique index resume_uuid_contact_type_index
  on contacts (resume_uuid, contact_type);

create table if not exists sections
(
  id serial not null
    constraint opaq_sections_pk
      primary key,
  section_type text not null,
  section_value text not null,
  resume_uuid char(36) not null
    constraint sections_resume_uuid_fk
      references resumes
      on update restrict on delete cascade
);

comment on table sections is 'textual and list sections of resume';

alter table sections owner to postgres;

create unique index sections_type_uuid_index
  on sections (resume_uuid, section_type);

