# create table log
# (
#   id                               bigint    not null
#     primary key,
#   created_at                       timestamp not null,
#   course_over_ground               integer,
#   is_stationary                    boolean   not null,
#   point                            geometry,
#   speed_over_ground_in_km_per_hour integer,
#   wind_direction                   integer,
#   wind_speed_in_km_per_hour        integer,
#   vessel_id                        bigint    not null
# );
#
# create table vessel
# (
#   id              bigint       not null
#     primary key,
#   created_at      timestamp    not null,
#   name            varchar(255) not null,
#   production_date timestamp,
#   last_log_id     bigint
#     constraint fkqvatccs6k6nit01rt6lujkr41
#       references log
# );
#
# alter table log
#   add constraint fkq7rhr9gpfphxuuctspy0mku45
#     foreign key (vessel_id) references vessel;
#
