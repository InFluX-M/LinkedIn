import pymysql
import pymysql.cursors
import json

def load_sp_db():
    connection = pymysql.connect(host='localhost',
                                 user='root',
                                 password='',
                                 db='linkedin_db',
                                 charset='utf8mb4',
                                 cursorclass=pymysql.cursors.DictCursor)

    try:

        with connection.cursor() as cursor:
            sql = "SELECT `user_id`, `specialities` FROM `user_specialities`"
            cursor.execute(sql)
            result = cursor.fetchall()
            result = list(result)

    finally:
        connection.close()

    return result

class person:
    def __init__(self, Id, name, profile_pic, email, dateOfBirth, universityLocation, field, workplace, specialties):
        self.Id = Id
        self.name = name
        self.profile_pic = profile_pic
        self.email = email
        self.dateOfBirth = dateOfBirth
        self.universityLocation = universityLocation
        self.field = field
        self.workplace = workplace
        self.specialties = specialties


def load_sp():
    sp = load_sp_db()

    specialities = dict()
    for s in sp:
        if s['user_id'] not in specialities:
            specialities[s['user_id']] = list()
        specialities[s['user_id']].append(s['specialities'])

    return specialities

import json
from users.models import user

def show():

    data = user.objects.all()
    specialities = load_sp()
    us = data.values()

    for u in us:
        u['specialities'] = specialities[u['id']]
        print(u)

    return us