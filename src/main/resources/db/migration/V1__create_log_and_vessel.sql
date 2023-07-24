create table log
(
  id                               serial primary key,
  created_at                       timestamp not null,
  course_over_ground               integer,
  is_stationary                    boolean   not null,
  point                            geometry,
  speed_over_ground_in_km_per_hour integer,
  wind_direction                   integer,
  wind_speed_in_km_per_hour        integer,
  vessel_id                        bigint    not null
);

create table vessel
(
  id              serial primary key,
  created_at      timestamp    not null,
  name            varchar(255) not null,
  production_date timestamp,
  last_log_id     bigint
);

alter table log
  add constraint fk_log_vessel
    foreign key (vessel_id) references vessel;

alter table vessel
  add constraint fk_veseel_last_log
    foreign key (last_log_id) references log;

