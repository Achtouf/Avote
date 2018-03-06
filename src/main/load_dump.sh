#!/bin/bash

if ! [[ -z ${1+x} ]]; then
  password=${1}
else
  read -s -p "mysql root password: " password
fi


mysql -u root --password="${password}" < sql/load_demo_dump.sql



