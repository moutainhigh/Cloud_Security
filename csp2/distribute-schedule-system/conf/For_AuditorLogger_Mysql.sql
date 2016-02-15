'-----------------------------------------------------'
' FOR MYSQL                                           '
'-----------------------------------------------------'
CREATE TABLE IF NOT EXISTS t_audit_log ( 
   Log_id     INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
   LoginId    VARCHAR(64)  NOT NULL DEFAULT '', 
   UserName   VARCHAR(128) DEFAULT '', 
   LogTime    datetime     NOT NULL, 
   OptClass   VARCHAR(256) DEFAULT '', 
   OptMethod  VARCHAR(128) DEFAULT '', 
   LogLevel   VARCHAR(32)  NOT NULL , 
   LogMessage VARCHAR(512) NOT NULL , 
   PRIMARY KEY (Log_id) 
 ) ENGINE=MyISAM DEFAULT CHARSET=GBK; 

COMMIT;
