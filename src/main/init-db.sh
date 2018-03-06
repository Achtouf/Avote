#!/bin/bash

if ! [[ -z ${1+x} ]]; then
  password=${1}
else
  read -s -p "mysql root password: " password
fi

mysql -u root --password="${password}" < sql/create_avote_db.sql
mysql -u root --password="${password}" < sql/create_avote_test_db.sql

mysqldump -u root --password="${password}" AVOTE | mysql -u root --password="${password}" AVOTE_TEST

mysql -u root --password="${password}" < sql/create_avote_db_users.sql

# mysql -u root --password="${password}" < sql/create_mock_addresses.sql
# mysql -u root --password="${password}" < sql/create_mock_polls.sql
# mysql -u root --password="${password}" < sql/create_mock_choices.sql
# mysql -u root --password="${password}" < sql/create_mock_users.sql


