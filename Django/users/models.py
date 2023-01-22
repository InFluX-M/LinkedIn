import django.db.models


class user(django.db.models.Model):
    id = django.db.models.CharField(primary_key=True, max_length=255)
    name = django.db.models.CharField(max_length=100)
    profile_url = django.db.models.CharField(max_length=100)
    date_of_birth = django.db.models.DateField()
    university_location = django.db.models.CharField(max_length=100)
    field = django.db.models.CharField(max_length=100)
    workplace = django.db.models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'user'
