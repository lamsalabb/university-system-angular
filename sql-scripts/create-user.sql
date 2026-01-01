DROP USER if exists 'universitydba'@'%' ;

CREATE USER 'universitydba'@'%' IDENTIFIED BY 'UniDba@2025!';

GRANT ALL PRIVILEGES ON * . * TO 'universitydba'@'%';
