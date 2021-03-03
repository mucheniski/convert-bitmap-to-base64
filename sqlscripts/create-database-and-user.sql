show databases;

create database bvtestsv3;

use bvtestsv3;

grant all privileges on bvtestsv3.* to 'developer'@'%';

flush privileges;

