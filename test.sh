#!/usr/bin/env bash

mongod --replSet rs1 --logpath "1.log" --dbpath db1 --port 27017 --oplogSize 64 --smallfiles --fork
mongod --replSet rs1 --logpath "2.log" --dbpath db2 --port 27018 --oplogSize 64 --smallfiles --fork
mongod --replSet rs1 --logpath "3.log" --dbpath db3 --port 27019 --oplogSize 64 --smallfiles --fork
mongod --replSet rs1 --logpath "4.log" --dbpath db4 --port 27020 --oplogSize 64 --smallfiles --fork