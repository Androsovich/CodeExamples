create TABLE IF NOT EXISTS users
(
    id          BIGINT       NOT NULL auto_increment,
    first_name  VARCHAR(255) NULL,
    last_name   VARCHAR(255) NULL,
    middle_name VARCHAR(255) NULL,
    birthday    DATE         NULL,
    user_name   VARCHAR(255) NULL,
    password    VARCHAR(255) NULL,
    email       VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);


create TABLE IF NOT EXISTS roles
(
    id   BIGINT      NOT NULL auto_increment,
    name VARCHAR(20) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS user_roles
(
    id      BIGINT NOT NULL auto_increment,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    CONSTRAINT pk_user_roles PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS projects
(
    id               BIGINT       NOT NULL auto_increment,
    title            VARCHAR(255) NULL,
    status           VARCHAR(15)  NULL,
    admin_id         BIGINT       NULL,
    user_projects_id BIGINT       NULL,
    CONSTRAINT pk_projects PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS tasks
(
    id         BIGINT      NOT NULL auto_increment,
    title      VARCHAR(60) NULL,
    project_id BIGINT      NULL,
    user_id    BIGINT      NULL,
    startDate  DATE        NULL,
    endDate    DATE        NULL,
    status     VARCHAR(60) NULL,
    CONSTRAINT pk_tasks PRIMARY KEY (id)
);
create TABLE IF NOT EXISTS user_projects
(
    id         BIGINT NOT NULL auto_increment,
    user_id    BIGINT NULL,
    project_id BIGINT NULL,
    CONSTRAINT pk_user_projects PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS comments
(
    id      BIGINT      NOT NULL auto_increment,
    content VARCHAR(60) NULL,
    task_id BIGINT      NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);

insert into roles (id, name)
values (1, 'ROLE_ADMIN');
insert into roles (id, name)
values (2, 'ROLE_USER');

--23wer -password
insert into users (id, first_name, last_name, middle_name,
                   birthday, user_name, password, email)
values (1, 'John', 'Miller', 'Show',
        '1987-12-10', 'werty', '$2a$12$f56gsJmbgIxutPo5QyS/Yuv1/NjdAzVIC9KmK7Xr7IFqzkUhUEKdq', '123@gmail.com');
--23wer123 -password
insert into users (id, first_name, last_name, middle_name,
                   birthday, user_name, password, email)
values (2, 'Jonny', 'Santa', 'Second',
        '1984-10-07', 'werties', '$2a$12$yKwJhvtmZ5hQA.b.xQ8i2eDe.R79eEkE1hjGfgHZlbNgFaI7kVELm', 'santa@gmail.com');
--mark777
insert into users (id, first_name, last_name, middle_name,
                   birthday, user_name, password, email)
values (3, 'Mark', 'Sanchas', 'Valerich',
        '1994-07-22', 'mark', '$2a$12$1ANEBZ6EMvu1gASiOScuku9uSMj5/n5JHjOZzfpMP6dB9Oifo0k6i', 'mark@gmail.com');
--vasya777
insert into users (id, first_name, last_name, middle_name,
                   birthday, user_name, password, email)
values (4, 'Vasya', 'Kysikov', 'Vasilevich',
        '1991-05-18', 'vasya', '$2a$12$Coh1Quld/s/IJcz4A1t1UuZV0UNfgrTsEk6i8qrkCf9.Sdr6e4tqi', 'vasya@gmail.com');

insert into user_roles(id, user_id, role_id)
values (1, 1, 1);
insert into user_roles(id, user_id, role_id)
values (3, 2, 2);
insert into user_roles(id, user_id, role_id)
values (4, 3, 2);
insert into user_roles(id, user_id, role_id)
values (5, 4, 2);



insert into projects (id, title, status, admin_id)
values (1, 'village power lines', 'ACTIVE', 1);
insert into projects (id, title, status, admin_id)
values (2, 'shop project', 'ACTIVE', 1);
insert into projects (id, title, status, admin_id)
values (3, 'school lighting project', 'INACTIVE', 1);

insert into user_projects (id, user_id, project_id)
values (1, 2, 1);
insert into user_projects (id, user_id, project_id)
values (2, 3, 1);
insert into user_projects (id, user_id, project_id)
values (3, 4, 1);
insert into user_projects (id, user_id, project_id)
values (4, 2, 2);
insert into user_projects (id, user_id, project_id)
values (5, 3, 2);
insert into user_projects (id, user_id, project_id)
values (6, 4, 2);
insert into user_projects (id, user_id, project_id)
values (7, 2, 3);
insert into user_projects (id, user_id, project_id)
values (8, 3, 3);
insert into user_projects (id, user_id, project_id)
values (9, 4, 3);

insert into tasks(id, title, project_id, user_id, startDate, endDate, status)
values (1, 'task1- village power lines', 1, 2, '2021-07-17', NULL, 'INACTIVE');
insert into tasks(id, title, project_id, user_id, startDate, endDate, status)
values (2, 'task2- village power lines', 1, 3, '2021-07-15', NULL, 'ACTIVE');
insert into tasks(id, title, project_id, user_id, startDate, endDate, status)
values (3, 'task3- village power lines', 1, 4, '2021-07-21', NULL, 'INACTIVE');
insert into tasks(id, title, project_id, user_id, startDate, endDate, status)
values (4, 'task1- shop project', 2, 2, '2021-08-10', NULL, 'INACTIVE');
insert into tasks(id, title, project_id, user_id, startDate, endDate, status)
values (5, 'task2- shop project', 2, 3, '2021-08-12', NULL, 'ACTIVE');
insert into tasks(id, title, project_id, user_id, startDate, endDate, status)
values (6, 'task3- shop project', 2, 4, '2021-08-09', NULL, 'ACTIVE');
insert into tasks(id, title, project_id, user_id, startDate, endDate, status)
values (7, 'task1 -school lighting project', 3, 2, '2021-09-20', NULL, 'INACTIVE');
insert into tasks(id, title, project_id, user_id, startDate, endDate, status)
values (8, 'task2 -school lighting project', 3, 3, '2021-09-19', NULL, 'ACTIVE');
insert into tasks(id, title, project_id, user_id, startDate, endDate, status)
values (9, 'task3 -school lighting project', 3, 4, '2021-09-23', NULL, 'INACTIVE');
