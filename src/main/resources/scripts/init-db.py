import os

import sqlparse
import yaml
import mysql.connector
import urllib3

with open("../application.yml", "r") as application_yml:
    cfg = yaml.load(application_yml, Loader=yaml.Loader)

    url = urllib3.util.parse_url(cfg["spring"]["datasource"]["url"][len('jdbc:'):])
    username = str(cfg["spring"]["datasource"]["username"])
    password = str(cfg["spring"]["datasource"]["password"])

    with mysql.connector.connect(
        host=url.host,
        port=url.port,
        user=username,
        password=password,
        database=url.path[1:]
    ) as cnx:

        cur = cnx.cursor(buffered=True)

        for sql_filepath in [
            "../db/schema-mysql.sql",
            "../db/data-mysql.sql",
            * map(lambda fn: f'../db/bak/{fn}', next(os.walk('../db/bak'))[2])
        ]:

            with open(sql_filepath, "r", encoding="utf-8") as sql_file:
                sql_buffer = sql_file.read()
                for statement in sqlparse.parse(sql_buffer):
                    sql = str(statement)
                    cur.execute(sql)
                    cnx.commit()

        cur = cnx.cursor(buffered=True)

        with os.popen('python ../db/data-generation.py') as p:
            sql_buffer = p.read()
            for statement in sqlparse.parse(sql_buffer):
                sql = str(statement)
                cur.execute(sql)
                cnx.commit()
